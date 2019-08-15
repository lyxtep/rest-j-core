package com.lx.core.utils;

import com.lx.core.configuration.LXProperties;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lx.ds
 */
public class LxFileUtils {
    
    protected final String FOLDER_NAME = ".lx";
    protected final String FILE_NAME = "config.properties";
    protected final String PROPERTIES_DB_DEFAULT = "/com/lx/core/resources/db.properties";
    
    private String resourcePackage;

    // Singleton pattern
    private static LxFileUtils instance = null;

    // Singleton pattern
    private LxFileUtils() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized LxFileUtils getInstance() {
        if (instance == null) {
            instance = new LxFileUtils();
        }

        return instance;
    }
    
    public synchronized void setResourcePackage(String value) {
        resourcePackage = value;
    }
    
    public synchronized void installConfigFile(String path, boolean overwrite) throws Exception {
        InputStream in = null;
        OutputStream out = null;
        File aFile;
        
        if (StringUtils.isNullOrEmpty(path)) {
            aFile = new File(System.getProperty("user.home") + File.separator + FOLDER_NAME);
            if (!aFile.exists()) {
                aFile.mkdir();
            }
            aFile = new File(aFile.getAbsolutePath(), FILE_NAME);
        } else {
            aFile = new File(path);
        }
        
        if (!overwrite && aFile.exists()) {
            throw new Exception(String.format("The file (%s) already exists.", aFile.getAbsolutePath()));
        }
        
        try {
            
            in = LXProperties.class.getResourceAsStream(PROPERTIES_DB_DEFAULT);
            if(in == null) {
                throw new Exception(String.format("Cannot get resource \"%s\" from Jar file.", PROPERTIES_DB_DEFAULT));
            }
            
            int readBytes;
            byte[] buffer = new byte[4096];
            out = new FileOutputStream(aFile);
            while ((readBytes = in.read(buffer)) > 0) {
                out.write(buffer, 0, readBytes);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LXProperties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LXProperties.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                in.close();
            }
            
            if (out != null) {
                out.close();
            }
        }
    }
    
    
    
    public synchronized Properties loadConfigFile(String filePath, Properties properties) {
        File theFile;
        InputStream in = null;
        
        if (properties == null) {
            properties = new Properties();
        }
        
        try {
            
            if (!StringUtils.isNullOrEmpty(filePath)) {
                theFile = new File(filePath);
            } else {
                theFile = new File(System.getProperty("user.home"), FOLDER_NAME + File.separator + FILE_NAME);
            }
        
            if (theFile.exists()) {
                in = new FileInputStream(theFile);
            } else {
                try {
                    if (resourcePackage != null) in = getClass().getResourceAsStream(resourcePackage + InetAddress.getLocalHost().getHostName() + ".properties");
                } catch (Exception ex) {
                    in = null;
                }
                
            }
            
            if (in == null) {
                in = getClass().getResourceAsStream(PROPERTIES_DB_DEFAULT);
            }
            
            if (in != null) {
                properties.load(in);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LXProperties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LXProperties.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(LXProperties.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return properties;
    }
    
    public synchronized List<List<String>> loadCsvFile(String filePath) {
        List<List<String>> results = new ArrayList<List<String>>();
        File theFile;
        Reader in = null;

        try {
            
            if (filePath.startsWith("/")) {
                in = new InputStreamReader(getClass().getResourceAsStream(filePath));
            } else {
                theFile = new File(filePath);
                if (theFile.exists()) {
                    in = new FileReader(theFile);
                }
            }
            
            if (in != null) {
                
                try (BufferedReader br = new BufferedReader(in)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                        results.add(Arrays.asList(values));
                    }
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LXProperties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LXProperties.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(LXProperties.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return results;
    }
}
