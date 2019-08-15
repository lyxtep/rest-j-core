package com.lx.db;

import com.lx.core.repository.entity.LXUser;
import com.lx.core.security.IUserIdentity;

/**
 *
 * @author lx.ds
 */
public class LxDbUserService {

    // Singleton pattern
    private static LxDbUserService instance = null;


    // Singleton pattern
    private LxDbUserService() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized LxDbUserService getInstance() {
        if (instance == null) {
            instance = new LxDbUserService();
        }

        return instance;
    }
    
    public synchronized LXUser getSysAdminUser() {
        LXUser user = new LXUser();
        user.setUsername("sa");
        user.setPassword("8aa87050051efe26091a13dbfdf901c6");
        user.setDisplayName("Administrator");
        
        return user;
    }
    
    public synchronized String insertEntries(IUserIdentity user) {
        String errors = "";
        
        LXUser query = new LXUser();
        query.setUsername("sa");
        
        errors += LxDbService.getInstance().createEntity(LXUser.class, query, getSysAdminUser(), user);
        
        return errors;
    }
}
