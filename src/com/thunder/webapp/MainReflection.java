package com.thunder.webapp;

import com.thunder.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println(r);

        // invoke toString via reflection
        Method method = r.getClass().getDeclaredMethod("toString", null);
        System.out.println(method);

        Resume r2 = new Resume();
        System.out.println(method.invoke(r2));
    }
}

/**
 uuid
 cfa07db4-37b8-438c-b8ec-02215cdbc30b
 new_uuid
 public java.lang.String com.thunder.webapp.model.Resume.toString()
 1c1dc3e9-ebcf-4523-8ec9-bf2c3fcc972c
*/




