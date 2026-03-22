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

package javassist.util.proxy;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * A proxy object is converted into an instance of this class
 * when it is written to an output stream.
 *
 * @see RuntimeSupport#makeSerializedProxy(Object)
 */
class SerializedProxy implements Serializable {
    /** default serialVersionUID */
    private static final long serialVersionUID = 1L;

    private String superClass;
    private String[] interfaces;
    private byte[] filterSignature;
    private MethodHandler handler;

    SerializedProxy(Class<?> proxy, byte[] sig, MethodHandler h) {
        filterSignature = sig;
        handler = h;
        superClass = proxy.getSuperclass().getName();

        Class<?>[] infs = proxy.getInterfaces();
        String setterInf = ProxyObject.class.getName();
        String setterInf2 = Proxy.class.getName();

        int count = 0;
        for (Class<?> inf : infs) {
            String name = inf.getName();
            if (!name.equals(setterInf) && !name.equals(setterInf2)) {
                count++;
            }
        }

        interfaces = new String[count];
        int index = 0;
        for (Class<?> inf : infs) {
            String name = inf.getName();
            if (!name.equals(setterInf) && !name.equals(setterInf2)) {
                interfaces[index++] = name;
            }
        }
    }

    /**
     * Load class.
     *
     * @param className the class name
     * @return loaded class
     * @throws ClassNotFoundException for any error
     */
    protected Class<?> loadClass(final String className) throws ClassNotFoundException {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            return Class.forName(className, true, cl);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("cannot load the class: " + className, e);
        }
    }

    @Serial
    Object readResolve() throws ObjectStreamException {
        try {
            int n = interfaces.length;
            Class<?>[] infs = new Class<?>[n];
            for (int i = 0; i < n; i++) {
                infs[i] = loadClass(interfaces[i]);
            }

            ProxyFactory f = new ProxyFactory();
            f.setSuperclass(loadClass(superClass));
            f.setInterfaces(infs);

            Proxy proxy = (Proxy) f.createClass(filterSignature).getConstructor().newInstance();
            proxy.setHandler(handler);
            return proxy;
        } catch (NoSuchMethodException | InvocationTargetException | ClassNotFoundException | IllegalAccessException e) {
            throw new InvalidClassException(e.getMessage());
        } catch (InstantiationException e) {
            throw new InvalidObjectException(e.getMessage());
        }
    }
}