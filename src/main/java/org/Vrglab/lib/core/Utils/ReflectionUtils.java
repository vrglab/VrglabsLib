package org.Vrglab.lib.core.Utils;

import org.Vrglab.Java.Reflections.AnnotationScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ReflectionUtils {

    public static <T extends Annotation> T getClassAnnotation(Class<?> clazz, Class<T> annotationClass) {
        return AnnotationScanner.getClassAnnotation(clazz, annotationClass);
    }

    public static Set<Class<?>> getAnnotatedClassesInPackage(String packageName, Class<? extends Annotation> annotation, ClassLoader loader) {
       return AnnotationScanner.getAnnotatedClassesInPackage(packageName, annotation, loader);
    }

    public static Object createInstance(Class<?> clazz, Object... initargs) {
        return AnnotationScanner.createInstance(clazz, initargs);
    }
}
