/*
 * Javassist, a Java-bytecode translator toolkit.
 * Copyright (C) 1999- Shigeru Chiba. All Rights Reserved.
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License.  Alternatively, the contents of this file may be used under
 * the terms of the GNU Lesser General Public License Version 2.1 or later,
 * or the Apache License Version 2.0.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 */
package org.vrglab.reflectionsHelper.util.proxy;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vrglab.reflectionsHelper.bytecode.ClassFile;

class SecurityActions {

    public static final SecurityActions stack = new SecurityActions();

    private static final StackWalker STACK_WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    private SecurityActions() {
    }

    /**
     * Replacement for the old SecurityManager#getClassContext() usage.
     *
     * @return the caller class of the method that invoked the method that called this
     * @since 3.23
     */
    public Class<?> getCallerClass() {
        return STACK_WALKER.walk(stream ->
                stream.skip(2)
                        .findFirst()
                        .map(StackWalker.StackFrame::getDeclaringClass)
                        .orElse(null));
    }

    static Method[] getDeclaredMethods(final Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

    static Constructor<?>[] getDeclaredConstructors(final Class<?> clazz) {
        return clazz.getDeclaredConstructors();
    }

    static MethodHandle getMethodHandle(final Class<?> clazz,
                                        final String name,
                                        final Class<?>[] params) throws NoSuchMethodException {
        try {
            Method method = clazz.getDeclaredMethod(name, params);
            method.setAccessible(true);

            try {
                return MethodHandles.privateLookupIn(clazz, MethodHandles.lookup())
                        .unreflect(method);
            } catch (IllegalAccessException ignored) {
                // Fallback for environments where privateLookupIn is restricted
                return MethodHandles.lookup().unreflect(method);
            }
        } catch (NoSuchMethodException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static Method getDeclaredMethod(final Class<?> clazz,
                                    final String name,
                                    final Class<?>[] types) throws NoSuchMethodException {
        return clazz.getDeclaredMethod(name, types);
    }

    static Constructor<?> getDeclaredConstructor(final Class<?> clazz,
                                                 final Class<?>[] types) throws NoSuchMethodException {
        return clazz.getDeclaredConstructor(types);
    }

    static void setAccessible(final AccessibleObject ao, final boolean accessible) {
        ao.setAccessible(accessible);
    }

    static void set(final Field fld, final Object target, final Object value)
            throws IllegalAccessException {
        fld.set(target, value);
    }

    static TheUnsafe getSunMiscUnsafeAnonymously() throws ClassNotFoundException {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);

            Object unsafeInstance = theUnsafeField.get(null);
            TheUnsafe usf = new TheUnsafe(unsafeClass, unsafeInstance);

            theUnsafeField.setAccessible(false);
            disableWarning(usf);
            return usf;
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (NoSuchFieldException e) {
            throw new ClassNotFoundException("No such instance.", e);
        } catch (IllegalAccessException | SecurityException e) {
            throw new ClassNotFoundException("Security denied access.", e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * _The_ Notorious sun.misc.Unsafe in all its glory, but anonymous
     * so as not to attract unwanted attention.
     *
     * @since 3.23
     */
    static class TheUnsafe {
        final Class<?> unsafe;
        final Object theUnsafe;
        final Map<String, List<Method>> methods = new HashMap<>();

        TheUnsafe(Class<?> c, Object o) {
            this.unsafe = c;
            this.theUnsafe = o;

            for (Method m : unsafe.getDeclaredMethods()) {
                methods.computeIfAbsent(m.getName(), k -> new ArrayList<>()).add(m);
            }

            for (Map.Entry<String, List<Method>> entry : methods.entrySet()) {
                List<Method> value = entry.getValue();
                if (value.size() == 1) {
                    entry.setValue(Collections.singletonList(value.get(0)));
                }
            }
        }

        private Method getM(String name, Object[] args) {
            List<Method> candidates = methods.get(name);
            if (candidates == null || candidates.isEmpty()) {
                throw new IllegalArgumentException("No such method: " + name);
            }

            if (candidates.size() == 1) {
                return candidates.get(0);
            }

            int argCount = args == null ? 0 : args.length;

            for (Method method : candidates) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != argCount) {
                    continue;
                }

                boolean match = true;
                for (int i = 0; i < parameterTypes.length; i++) {
                    Object arg = args[i];
                    if (arg == null) {
                        continue;
                    }

                    Class<?> paramType = wrap(parameterTypes[i]);
                    Class<?> argType = wrap(arg.getClass());

                    if (!paramType.isAssignableFrom(argType)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    return method;
                }
            }

            return candidates.get(0);
        }

        private static Class<?> wrap(Class<?> type) {
            if (!type.isPrimitive()) {
                return type;
            }
            if (type == boolean.class) return Boolean.class;
            if (type == byte.class) return Byte.class;
            if (type == char.class) return Character.class;
            if (type == short.class) return Short.class;
            if (type == int.class) return Integer.class;
            if (type == long.class) return Long.class;
            if (type == float.class) return Float.class;
            if (type == double.class) return Double.class;
            if (type == void.class) return Void.class;
            return type;
        }

        public Object call(String name, Object... args) {
            try {
                Method method = getM(name, args);
                method.setAccessible(true);
                return method.invoke(theUnsafe, args);
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
    }

    /**
     * Java 9+ emits illegal-access warnings for these internals.
     *
     * @param tu theUnsafe that'll fix it
     */
    static void disableWarning(TheUnsafe tu) {
        try {
            if (ClassFile.MAJOR_VERSION < ClassFile.JAVA_9) {
                return;
            }

            Class<?> cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            tu.call("putObjectVolatile", cls, tu.call("staticFieldOffset", logger), null);
        } catch (Exception ignored) {
            // swallow
        }
    }
}
