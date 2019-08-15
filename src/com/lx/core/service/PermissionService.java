package com.lx.core.service;

import com.lx.core.configuration.LXPermission;
import com.lx.core.utils.ClassUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lx.ds
 */
public class PermissionService {

    // Singleton pattern
    private static PermissionService instance = null;
    
    private List<String> _list = null;

    public static final String PERMISSION_TYPE_DATA = "data";
    public static final String PERMISSION_TYPE_MODULE = "module";

    // Singleton pattern
    private PermissionService() {
        // Constructor
        init();
    }

    // Singleton pattern
    public static synchronized PermissionService getInstance() {
        if (instance == null) {
            instance = new PermissionService();
        }

        return instance;
    }

    private void init()
    {
        appendPermissions(LXPermission.class);
    }
    
    public <TEntity extends Object> List<String> insertPermissions(Class<TEntity> clazz) {
        if (_list == null)
        {
            _list = new ArrayList<String>();
        }

        _list.addAll(0, ClassUtils.getInstance().getPublicStaticFinalStringValues(clazz));

        return _list;
    }
    
    public <TEntity extends Object> List<String> appendPermissions(Class<TEntity> clazz) {
        if (_list == null)
        {
            _list = new ArrayList<String>();
        }

        _list.addAll(ClassUtils.getInstance().getPublicStaticFinalStringValues(clazz));

        return _list;
    }
    
    public List<String> getPermissions() {
        return _list;
    }
}
