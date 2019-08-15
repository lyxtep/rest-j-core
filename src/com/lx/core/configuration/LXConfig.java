package com.lx.core.configuration;

import com.lx.core.utils.LxFileUtils;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 *
 * @author dejan.stjepanovic
 */
public class LXConfig {

    private static LXConfig instance = null;
    
    private SimpleDateFormat _dateTimeFormatter;
    private SimpleDateFormat _dateFormatter;
    private DecimalFormat _decimalFormatter;

    // Singleton pattern
    private LXConfig() {
        // Constructor
    }
    
    // Singleton pattern
    public static synchronized LXConfig getInstance() {
        if (instance == null) {
            instance = new LXConfig();
        }

        return instance;
    }
    
    public synchronized void install(String path, boolean overwrite) throws Exception {
        LxFileUtils.getInstance().installConfigFile(path, overwrite);
    }
    
    public synchronized void refresh() throws Exception {
        
        Properties defaults = LxFileUtils.getInstance().loadConfigFile(null, null);
        
        LXDbConfig.getInstance().setConnectionParams(defaults.getProperty("db.host"), defaults.getProperty("db.port"), 
                defaults.getProperty("db.username"), defaults.getProperty("db.password"), defaults.getProperty("db.name"));
        
        LXProperties.getInstance().refresh(defaults);
    }
    
    public SimpleDateFormat getDateTimeFormatter() {
        if (_dateTimeFormatter == null) {
            _dateTimeFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        }
        return _dateTimeFormatter;
    }
    
    public SimpleDateFormat getDateFormatter() {
        if (_dateFormatter == null) {
            _dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        }
        return _dateFormatter;
    }
    
    public DecimalFormat getDecimalFormatter() {
        if (_decimalFormatter == null) {
            _decimalFormatter = new DecimalFormat("0,00");
        }
        return _decimalFormatter;
    }
    
    public synchronized String getTemplateLocation() throws Exception {
        return LXProperties.getInstance().getStringValue("template");
    }
    
    public synchronized String getRepository() throws Exception {
        return LXProperties.getInstance().getStringValue("repository");
    }
    public synchronized File getRepositoryLocation() throws Exception {
        return LXProperties.getInstance().getFileValue("repository");
    }
    
    public synchronized String getDeletedRep() throws Exception {
        return LXProperties.getInstance().getStringValue("deleted_rep");
    }
    public synchronized File getDeletedRepLocation() throws Exception {
        return LXProperties.getInstance().getFileValue("deleted_rep");
    }
    
    public synchronized File getUploadTempLocation() throws Exception {
        return LXProperties.getInstance().getFileValue("temp");
    }
    
    public synchronized String getString(String key) throws Exception {
        return LXProperties.getInstance().getStringValue(key);
    }
    
    
    private String _elementStateActive;
    public synchronized String getElementStateActive() {
        if (_elementStateActive == null) {
            _elementStateActive = "active";
        }
        return _elementStateActive;
    }

    private String _securityClassDefault;
    public String getSecurityClassDefault()
    {
        if (_securityClassDefault == null)
        {
            _securityClassDefault = LXSecurityClass.RESTRICTED;
        }
        return _securityClassDefault;
    }
}
