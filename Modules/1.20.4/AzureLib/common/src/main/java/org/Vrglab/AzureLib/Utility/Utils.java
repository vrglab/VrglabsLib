package org.Vrglab.AzureLib.Utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Utils {

    /**
     * Gets the value of a private final static field from a class.
     *
     * @param clazz the class containing the field
     * @param fieldName the name of the field
     * @return the value of the field
     */
    public static <T> T getPrivateFinalStaticField(Class<?> clazz, String fieldName){
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T)field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the value of a private final field from an instance of a class.
     *
     * @param instance the instance containing the field
     * @param fieldName the name of the field
     * @return the value of the field
     */
    public static <T> T getPrivateFinalStaticField(Object instance, Class<?> clazz, String fieldName){
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T)field.get(instance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calls a private static method from a class.
     *
     * @param clazz the class containing the method
     * @param methodName the name of the method
     * @param paramTypes the parameter types of the method
     * @param args the arguments to pass to the method
     * @return the result of the method call
     */
    public static <T> T callPrivateStaticMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes, Object... args) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return (T)method.invoke(null, args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calls a private non-static method from an instance of a class.
     *
     * @param instance the instance containing the method
     * @param methodName the name of the method
     * @param paramTypes the parameter types of the method
     * @param args the arguments to pass to the method
     * @return the result of the method call
     */
    public static <T> T callPrivateMethod(Object instance, String methodName, Class<?>[] paramTypes, Object... args) {
        try {
            Class<?> clazz = instance.getClass();
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return (T)method.invoke(instance, args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


}
