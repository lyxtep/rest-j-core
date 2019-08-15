package com.lx.core.utils;

import com.lx.core.configuration.LXConfig;

/**
 *
 * @author lx.ds
 */
public class PathUtils {

    // Singleton pattern
    private static PathUtils instance = null;

    // Singleton pattern
    private PathUtils() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized PathUtils getInstance() {
        if (instance == null) {
            instance = new PathUtils();
        }

        return instance;
    }
    
    public synchronized String getTemplatePath(String template) throws Exception {
        String root = LXConfig.getInstance().getTemplateLocation();
        
        if (root.contains("\\")) {
            
            if (root.endsWith("\\")) {
                root = root.substring(0, root.length() - 1);
            }
            
            if (template.startsWith("\\")) {
                template = template.substring(0, 1);
            }
            
            return String.format("%s\\%s", root, template);
            
        } else if (root.contains("/")) {
            
            if (root.endsWith("/")) {
                root = root.substring(0, root.length() - 1);
            }
            
            if (template.startsWith("/")) {
                template = template.substring(0, 1);
            }
            
            return String.format("%s/%s", root, template);
        }
        
        return String.format("%s/%s", LXConfig.getInstance().getTemplateLocation(), template);
    }
}
