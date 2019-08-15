package com.lx.core.configuration;

import com.lx.core.utils.LxFileUtils;
import java.util.Properties;

/**
 *
 * @author lx.ds
 */
public class LXDbConfig {

    private static LXDbConfig instance = null;
    
    private String _dbHost;
    private String _dbPort;
    private String _dbUsername;
    private String _dbPassword;
    private String _dbName;
    
    // Singleton pattern
    private LXDbConfig() {
        // Constructor
        initialize();
    }
    
    // Singleton pattern
    public static synchronized LXDbConfig getInstance() {
        if (instance == null) {
            instance = new LXDbConfig();
        }

        return instance;
    }
    
    private void initialize() {
        Properties defaults = LxFileUtils.getInstance().loadConfigFile(null, null);
        
        this._dbHost = defaults.getProperty("db.host");
        this._dbPort = defaults.getProperty("db.port");
        this._dbUsername = defaults.getProperty("db.username");
        this._dbPassword = defaults.getProperty("db.password");
        this._dbName = defaults.getProperty("db.name");
    }
    
    public synchronized void setConnectionParams(String host, String port, String username, String password, String name) {
        this._dbHost = host;
        this._dbPort = port;
        this._dbUsername = username;
        this._dbPassword = password;
        this._dbName = name;
    }
    
    public synchronized String getDbHost() {
        return _dbHost;
    }
    
    public synchronized String getDbPort() {
        return _dbPort;
    }
    
    public synchronized String getDbUsername() {
        return _dbUsername;
    }
    
    public synchronized String getDbPassword() {
        return _dbPassword;
    }
    
    public synchronized String getDbName() {
        return _dbName;
    }
}
