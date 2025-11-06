package org.Vrglab.VrglabsLib.Utils;

import java.lang.reflect.*;

public class ReflectionUtil {

    public static <T> T getField(Object target, String fieldName, Class<T> type) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return type.cast(field.get(target));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to access field " + fieldName + " in " + target.getClass(), e);
        }
    }

    public static void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set field " + fieldName + " in " + target.getClass(), e);
        }
    }

    /**
     * Calls a private/protected method with no arguments
     */
    public static <T> T callMethod(Object target, String methodName, Class<T> returnType) {
        try {
            Method method = target.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            return returnType.cast(method.invoke(target));
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke method " + methodName + " on " + target.getClass(), e);
        }
    }

    /**
     * Calls a private/protected method with arguments
     */
    public static <T> T callMethod(Object target, String methodName, Class<T> returnType, Class<?>[] paramTypes, Object... args) {
        try {
            Method method = target.getClass().getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return returnType.cast(method.invoke(target, args));
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke method " + methodName + " on " + target.getClass(), e);
        }
    }

    /**
     * Instantiates a class dynamically using the no-arg constructor
     */
    public static <T> T createInstance(Class<T> clazz) {
        try {
            var constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate class " + clazz, e);
        }
    }

    /**
     * Instantiate class with constructor arguments
     */
    public static <T> T createInstance(Class<T> clazz, Class<?>[] paramTypes, Object... args) {
        try {
            var constructor = clazz.getDeclaredConstructor(paramTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate class " + clazz, e);
        }
    }


    /**
     * Gets a private nested class type by name
     */
    public static Class<?> getNestedClass(Class<?> outerClass, String nestedName)  {
        for (Class<?> inner : outerClass.getDeclaredClasses()) {
            if (inner.getSimpleName().equals(nestedName)) {
                return inner;
            }
        }
        throw new RuntimeException("Nested class " + nestedName + " not found in " + outerClass);
    }

    /**
     * Instantiates a nested class with explicit constructor types
     */
    public static Object createNestedInstance(Class<?> nestedClass, Object outerInstance, Class<?>[] paramTypes, Object... args) {
        try {
            Object[] finalArgs;

            if (nestedClass.getEnclosingClass() != null && !Modifier.isStatic(nestedClass.getModifiers())) {
                if (outerInstance == null)
                    throw new IllegalArgumentException("Outer instance cannot be null for non-static nested class");

                finalArgs = new Object[args.length + 1];
                finalArgs[0] = outerInstance;
                System.arraycopy(args, 0, finalArgs, 1, args.length);
            } else {
                finalArgs = args;
            }

            var constructor = nestedClass.getDeclaredConstructor(paramTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(finalArgs);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate nested class " + nestedClass, e);
        }
    }
}
