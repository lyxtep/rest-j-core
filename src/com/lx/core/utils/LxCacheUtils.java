package com.lx.core.utils;

import com.lx.core.repository.generic.EntityClassWrapper;
import com.lx.core.service.generic.BeanClassWrapper;
import java.util.HashMap;

/**
 *
 * @author lx.ds
 */
public class LxCacheUtils {

    // Singleton pattern
    private static LxCacheUtils instance = null;

    private final HashMap _htEntityClassWrappers;
    private final HashMap _htBeanClassWrappers;

    // Singleton pattern
    private LxCacheUtils() {
        // Constructor
        _htEntityClassWrappers = new HashMap();
        _htBeanClassWrappers = new HashMap();
    }

    // Singleton pattern
    public static synchronized LxCacheUtils getInstance() {
        if (instance == null) {
            instance = new LxCacheUtils();
        }

        return instance;
    }
    
    public EntityClassWrapper getEtityClassWrapper(Class clazz) {
        EntityClassWrapper data = (EntityClassWrapper)_htEntityClassWrappers.get(clazz.getName());
        if (data == null) {
            data = new EntityClassWrapper(clazz);
            setEtityClassWrapper(clazz, data);
        }
        return data;
    }
    public void setEtityClassWrapper(Class clazz, EntityClassWrapper wrapper) {
        _htEntityClassWrappers.put(clazz.getName(), wrapper);
    }
    
    public BeanClassWrapper getBeanClassWrapper(Class clazz) {
        BeanClassWrapper data = (BeanClassWrapper)_htBeanClassWrappers.get(clazz.getName());
        if (data == null) {
            data = new BeanClassWrapper(clazz);
            setBeanClassWrapper(clazz, data);
        }
        return data;
    }
    public void setBeanClassWrapper(Class clazz, BeanClassWrapper wrapper) {
        _htBeanClassWrappers.put(clazz.getName(), wrapper);
    }
}
