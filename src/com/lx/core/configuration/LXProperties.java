package com.lx.core.configuration;

import com.lx.core.repository.entity.LXProperty;
import com.lx.core.service.generic.GenericService;
import com.lx.core.utils.LxFileUtils;
import com.lx.core.utils.StringUtils;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author dejan.stjepanovic
 */
public class LXProperties {
    
    private static LXProperties instance = null;
    
    private Properties properties;
    
    // Singleton pattern
    private LXProperties() throws Exception {
        // Constructor
        load(null);
    }
    
    // Singleton pattern
    public static synchronized LXProperties getInstance() throws Exception {
        if (instance == null) {
            instance = new LXProperties();
        }

        return instance;
    }
    
    public void refresh(Properties defaults) throws Exception {
        load(defaults);
    }
    
    private void load(Properties defaults) throws Exception {
        
        if (properties != null && !properties.isEmpty()) properties.clear();
        
        if (defaults == null) defaults = LxFileUtils.getInstance().loadConfigFile(null, null);
        
        properties = (defaults != null ? new Properties(defaults) : new Properties());
        
        // add and override properties from DB
        loadDb();
    }
    
    protected void loadDb() throws Exception {
        
        List<LXProperty> lProperties = GenericService.getInstance().get(LXProperty.class, null, null);
        
        for (LXProperty aProperty : lProperties) {
            properties.put(aProperty.getKeyword(), aProperty.getValue());
        }
    }
    
    
    private SimpleDateFormat _dateFormatter;
    protected SimpleDateFormat getDateFormatter() {
        if (_dateFormatter == null) {
            _dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }
        return _dateFormatter;
    }
    
    
    public synchronized List<String> getListValue(String key, String separator) {
        List<String> values = new ArrayList<>();
        String strValue;
        
        if (StringUtils.isNullOrEmpty(separator)) {
            separator = ",";
        }
        
        strValue = getStringValue(key);
        if (!StringUtils.isNullOrEmpty(strValue)) {
            values.addAll(Arrays.asList(strValue.split(separator)));
        }
        
        return values;
    }
    public synchronized Date getDateValue(String key) {
        Date value = null;
        String strValue = getStringValue(key);
        if (!StringUtils.isNullOrEmpty(strValue)) {
            try {
                value = getDateFormatter().parse(strValue);
            } catch (Exception ex) {
                
            }
        }
        return value;
    }
    public synchronized BigDecimal getDecimalValue(String key) {
        BigDecimal value = null;
        String strValue = getStringValue(key);
        if (!StringUtils.isNullOrEmpty(strValue)) {
            try {
                value = new BigDecimal(strValue);
            } catch (Exception ex) {
                
            }
        }
        return value;
    }
    public synchronized int getIntValue(String key) {
        int value = 0;
        String strValue = getStringValue(key);
        if (!StringUtils.isNullOrEmpty(strValue)) {
            try {
                value = Integer.parseInt(strValue);
            } catch (Exception ex) {
                
            }
        }
        return value;
    }
    public synchronized Integer getIntegerValue(String key) {
        Integer value = null;
        String strValue = getStringValue(key);
        if (!StringUtils.isNullOrEmpty(strValue)) {
            try {
                value = Integer.parseInt(strValue);
            } catch (Exception ex) {
                
            }
        }
        return value;
    }
    public synchronized String getStringValue(String key) {
        String value = null;
        Object objValue = getValue(key);
        if (objValue != null) {
            value = objValue.toString();
        }
        return value;
    }
    public synchronized File getFileValue(String key) {
        File value = null;
        Object objValue = getValue(key);
        if (objValue != null) {
            value = new File(objValue.toString());
        }
        
        if (value == null || !value.exists()) {
            value = new File(System.getProperty("java.io.tmpdir"));
        }
        
        return value;
    }
    
    public synchronized Object getValue(String key) {
        if (properties.containsKey(key)) {
            return properties.get(key);
        }
        return null;
    }
}
