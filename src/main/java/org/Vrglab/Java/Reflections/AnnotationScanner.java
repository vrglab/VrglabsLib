package org.Vrglab.Java.Reflections;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class AnnotationScanner {
    // Method to get all classes in a package from a specific class loader
    public static Set<Class> getClassesInPackage(String packageName, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        InputStream stream = classLoader
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        if(stream == null) {
            return new HashSet<>();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Set<Class> classes = reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
        return classes;
    }

    private static Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

    // Method to find classes in a directory
    private static List<Class<?>> findClasses(File directory, String packageName, ClassLoader classLoader) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    classes.addAll(findClasses(file, packageName + "." + file.getName(), classLoader));
                } else if (file.getName().endsWith(".class")) {
                    classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6), false, classLoader));
                }
            }
        }
        return classes;
    }

    // Method to find classes in a jar file
    private static List<Class<?>> findClassesInJar(URL jar, String path, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarURLConnection jarURLConnection = (JarURLConnection) jar.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.startsWith(path) && name.endsWith(".class")) {
                String className = name.replace('/', '.').substring(0, name.length() - 6);
                classes.add(Class.forName(className, false, classLoader));
            }
        }
        return classes;
    }

    // Method to scan for annotated classes in a specific package
    public static Set<Class<?>> getAnnotatedClassesInPackage(String packageName, Class<? extends Annotation> annotation, ClassLoader classLoader) {
        Set<Class<?>> annotatedClasses = new HashSet<>();
        try {
            Set<Class> classes = getClassesInPackage(packageName, classLoader);
            for (Class<?> clazz : classes) {
                if (clazz.isAnnotationPresent(annotation)) {
                    annotatedClasses.add(clazz);
                }
            }
            if (annotatedClasses.isEmpty()) {
                System.out.println("No classes found with annotation: " + annotation.getName());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while scanning for annotated classes: " + e.getMessage());
        }
        return annotatedClasses;
    }

    // Method to get the annotation from a class
    public static <T extends Annotation> T getClassAnnotation(Class<?> clazz, Class<T> annotationClass) {
        if (clazz != null && clazz.isAnnotationPresent(annotationClass)) {
            return clazz.getAnnotation(annotationClass);
        }
        return null;
    }

    // Method to create an instance of a class
    public static Object createInstance(Class<?> clazz, Object... initargs) {
        if (clazz == null) {
            System.err.println("Class is null, cannot create instance.");
            return null;
        }

        try {
            for (Constructor<?> constructor : clazz.getConstructors()) {
                if (constructor.getParameterCount() == initargs.length) {
                    return constructor.newInstance(initargs);
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("Error while creating instance of class");
            e.printStackTrace();
        }

        return null;
    }
}
