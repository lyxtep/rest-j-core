package com.lx.db;

import com.lx.core.security.IUserIdentity;
import com.lx.core.service.generic.GenericService;
import java.util.List;

/**
 *
 * @author lx.ds
 */
public class LxDbService {
    
    // Singleton pattern
    private static LxDbService instance = null;


    // Singleton pattern
    private LxDbService() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized LxDbService getInstance() {
        if (instance == null) {
            instance = new LxDbService();
        }

        return instance;
    }
    
    public synchronized String createDatabase(IUserIdentity user) {
        String errors = "";
        
        try {
            GenericService.getInstance().createDB();
        } catch (Exception ex) {
            errors += ex.getMessage() + "; ";
        }
        
        return errors;
    }
    
    public synchronized <T extends Object> String createTable(Class<T> clazz, IUserIdentity user) {
        String errors = "";
        
        try {
            GenericService.getInstance().createTable(clazz);
        } catch (Exception ex) {
            errors += ex.getMessage() + "; ";
        }
        
        return errors;
    }
    
    
    
    public synchronized <TEntity extends Object> String createEntity(Class<TEntity> clazz, TEntity query, TEntity entity, IUserIdentity user) {
        List<TEntity> results = null;
        String errors = "";
        
        try {
            results = GenericService.getInstance().get(clazz, query, user);
        } catch (Exception ex) {
            errors += ex.getMessage() + "; ";
        }
        
        if (results == null || results.isEmpty()) {
            try {
                GenericService.getInstance().create(clazz, entity, user);
            } catch (Exception ex) {
                errors += ex.getMessage() + "; ";
            }
        }
        
        return errors;
    }
}
