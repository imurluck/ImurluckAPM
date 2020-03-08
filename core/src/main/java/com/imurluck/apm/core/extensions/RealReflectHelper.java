package com.imurluck.apm.core.extensions;

import java.lang.reflect.Field;

public class RealReflectHelper {

    public static <T> T getField(Object instance, String fieldName) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
