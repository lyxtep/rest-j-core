package com.lx.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lx.ds
 */
public class ClassUtils {

    // Singleton pattern
    private static ClassUtils instance = null;

    private final HashMap _cache;

    private final Map _defaultValues;

    // Singleton pattern
    private ClassUtils() {
        // Constructor
        _cache = new HashMap();

        _defaultValues = new HashMap();
        _defaultValues.put(boolean.class, false);
        _defaultValues.put(short.class, 0);
        _defaultValues.put(int.class, 0);
        _defaultValues.put(long.class, 0);
        _defaultValues.put(float.class, 0);
        _defaultValues.put(double.class, 0);
    }

    // Singleton pattern
    public static synchronized ClassUtils getInstance() {
        if (instance == null) {
            instance = new ClassUtils();
        }

        return instance;
    }

    public synchronized Object getDefaultValue(Class clazz) {
        if (_defaultValues.containsKey(clazz)) {
            return _defaultValues.get(clazz);
        }
        return null;
    }
    
    public synchronized <T extends Object> T createIntance(Class<T> clazz) {
        
        try {
            return clazz.newInstance();
        } catch (InstantiationException ex) {
            return null;
        } catch (IllegalAccessException ex) {
            return null;
        }
    }
    
    public synchronized boolean hasProperty(Class clazz, String name) {
        Field field;
        try {
            field = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException ex) {
            field = null;
        }
        return (field != null);
    }
    
    public synchronized List<String> getPropertyNames(Class clazz) {
        List<String> results = new ArrayList<>();
        
        Field[] fields = clazz.getDeclaredFields();
        for (Field aField : fields) {
            results.add(aField.getName());
        }
        
        return results;
    }
    
    public synchronized List<String> getPublicStaticFinalStringValues(Class clazz) {
        List<String> list = new ArrayList<>();
        
        Field[] fields = clazz.getFields(); // get all public fields
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f.getModifiers())) {
                if (f.getType().equals(String.class)) {
                    try {
                        list.add((String)f.get(null));
                    } catch (IllegalArgumentException ex) {
                        
                    } catch (IllegalAccessException ex) {
                        
                    }
                }
            } 
        }
        
        return list;
    }
}
