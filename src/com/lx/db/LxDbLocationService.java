package com.lx.db;

import com.lx.core.repository.entity.LXLocation;
import com.lx.core.security.IUserIdentity;
import com.lx.core.utils.LxFileUtils;
import com.lx.core.utils.StringUtils;
import java.util.List;

/**
 *
 * @author lx.ds
 */
public class LxDbLocationService {
    
    public static final String TAG_COUNTRY = "country";

    // Singleton pattern
    private static LxDbLocationService instance = null;


    // Singleton pattern
    private LxDbLocationService() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized LxDbLocationService getInstance() {
        if (instance == null) {
            instance = new LxDbLocationService();
        }

        return instance;
    }
    
    public synchronized String insertEntries(IUserIdentity user) {
        String errors = "";
        
        LXLocation query = new LXLocation();
        
        List<List<String>> countries = LxFileUtils.getInstance().loadCsvFile("/com/lx/core/resources/countries.csv");
        for (int i = 1; i < countries.size(); i++) {
            if (!countries.get(i).isEmpty() && !countries.get(i).get(0).isEmpty()) {
                query.setName(StringUtils.trim(countries.get(i).get(0), "\""));
                errors += LxDbService.getInstance().createEntity(LXLocation.class, query, getCountry(query.getName(), countries.get(i).get(1)), user);
            }
        }
        
        return errors;
    }
    
    private LXLocation getCountry(String name, String code) {
        LXLocation entity = new LXLocation();
        entity.setName(name);
        entity.setCode(code);
        entity.setTags(TAG_COUNTRY);
        
        return entity;
    }
}
