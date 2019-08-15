package com.lx.core.utils;

import com.lx.core.configuration.LXConfig;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lx.ds
 */
public class ObjectUtils {
    
    public static Field getField(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        Field field = null;
        
        while (clazz != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
                clazz = null;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        
        return field;
    }

    public static <V> V get(Object object, String fieldName) {
        return get(object, getField(object, fieldName));
    }
    public static <V> V get(Object object, Field field) {
        if (field != null) {
            try {
                field.setAccessible(true);
                return (V) field.get(object);
            }catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }
    
    public static boolean set(Object object, String fieldName, Object fieldValue) {
        return set(object, getField(object, fieldName), fieldValue);
    }
    public static boolean set(Object object, Field field, Object fieldValue) {
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            }catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    public static boolean reset(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, ClassUtils.getInstance().getDefaultValue(field.getClass()));
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }
    
    public static boolean isDefaultValue(Object object, String fieldName) {
        return isDefaultValue(object, getField(object, fieldName));
    }
    public static boolean isDefaultValue(Object object, Field field) {
        
        if (field == null) throw new IllegalStateException("Field does not exist");
        
        try {
            field.setAccessible(true);
            return field.get(object) == ClassUtils.getInstance().getDefaultValue(field.getClass());
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    public static <TSource, TTarget> TTarget mapTo(TSource source, TTarget target, boolean overwriteWithDefaultValues) {
        Class<?> sourceClazz = source.getClass();
        Field[] sourceFields = sourceClazz.getDeclaredFields();
        
        Class<?> targetClazz = target.getClass();
        Field[] targetFields = targetClazz.getDeclaredFields();
        
        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (sourceField.getName().equalsIgnoreCase(targetField.getName())) {
                    
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);
                    try {
                        Object value = sourceField.get(source);
                        
                        if (!overwriteWithDefaultValues) {
                            if (value == ClassUtils.getInstance().getDefaultValue(sourceField.getClass())) {
                                continue;
                            }
                        }
                        
                        // map value
                        targetField.set(target, sourceField.get(source));
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(ObjectUtils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        return target;
    }
    
    public static <V> V getUnique(List<V> list) throws Exception {
        
        if (list == null || list.isEmpty()) {
            return null;
        }
        
        if (list.size() > 1) {
            throw new Exception("Requested item is not unique");
        }
        
        return list.get(0);
    }
    
    public static <V> V getFieldValue(Object object, String fieldName, String valueName) {
        Class<?> clazz = object.getClass();
        Field field = null;
        
        if (clazz != null) {
            
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ex) {
                field = null;
            }
            
            if (field != null && !field.getClass().isPrimitive()) {
                try {
                    field = field.getClass().getDeclaredField(valueName);
                } catch (NoSuchFieldException ex) {
                    field = null;
                }
            }
            
            if (field == null) {
                try {
                    field = clazz.getDeclaredField(fieldName + valueName.substring(0, 1).toUpperCase() + valueName.substring(1));
                } catch (NoSuchFieldException ex) {
                    field = null;
                }
            }
        }
        
        if (field != null) {
            return get(object, field);
        }
        
        return null;
    }
    
    public static String getSerializedValue(Object obj, String fieldName) {
        Field field = getField(obj, fieldName);
        String sValue;
        Object value;
        String type;
        
        if (field == null) throw new IllegalStateException("Field does not exist");
        
        if (isDefaultValue(obj, field)) {
            return null;
        }
        
        value = get(obj, field);
        
        type = field.getType().getCanonicalName();

        switch (type) {
            case "java.lang.String":
                sValue = String.valueOf(value);
                break;
            case "int":
                sValue = Integer.toString((int)value);
                break;
            case "java.lang.Integer":
                sValue = ((Integer)value).toString();
                break;
            case "long":
                sValue = Long.toString((long)value);
                break;
            case "java.​lang.Long":
                sValue = ((Long)value).toString();
                break;
            case "java.util.Date":
                sValue = Long.toString(((Date)value).getTime()/1000);
                break;
            case "java.math.BigDecimal":
                sValue = ((BigDecimal)value).toString();
                break;
            case "boolean":
                sValue = Boolean.toString((boolean)value);
                break;
            case "java.lang.Boolean":
                sValue = ((Boolean)value).toString();
                break;
            default:
                sValue = null;
                break;

        }

        return sValue;
    }
    
    public static void setDeserializedValue(Object obj, String fieldName, String sValue) {
        Field field = getField(obj, fieldName);
        String type;
        Object value;
        
        if (field == null) throw new IllegalStateException("Field does not exist");
        
        type = field.getType().getCanonicalName();

        switch (type) {
            case "java.lang.String":
                value = sValue;
                break;
            case "int":
                value = Integer.parseInt(type);
                break;
            case "java.lang.Integer":
                value = new Integer(type);
                break;
            case "long":
                value = Long.parseLong(sValue);
                break;
            case "java.​lang.Long":
                value = Long.valueOf(sValue);
                break;
            case "java.util.Date":
                value = new Date(Long.valueOf(sValue)*1000);
                break;
            case "java.math.BigDecimal":
                value = new BigDecimal(sValue);
                break;
            case "boolean":
                value = Boolean.parseBoolean(sValue);
                break;
            case "java.lang.Boolean":
                value = Boolean.valueOf(sValue);
                break;
            default:
                value = sValue;
                break;

        }
        
        set(obj, field, value);
    }
}
