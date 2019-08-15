package com.lx.core.security;

import java.util.Set;

/**
 *
 * @author lx.ds
 */
public interface IUserIdentity {
    
    /**
     * Get user name
     * @return 
     */
    String getUsername();
    /**
     * Get user display name
     * @return 
     */
    String getDisplayName();
    
    /**
     * Get user company
     * @return 
     */
    String getCompanyId();
    
    /**
     * Get user department
     * @return 
     */
    String getDepartmentId();
    
    /**
     * Get user employee
     * @return 
     */
    String getEmployeeId();
    /**
     * Get user role
     * @return 
     */
    String getGroupId();
    /**
     * Get user locale
     * @return 
     */
    String getLocale();
    /**
     * Get all the identity names including the login name and all the role names.
     * @return 
     */
    Set getAllNames();
    
    /**
     * Get user permissions
     * @return 
     */
    java.util.Map<String, String> getPermissions();
    
    /**
     * Get user attributes
     * @return 
     */
    java.util.Map getAttributes();
    /**
     * Get the http headers which should be sent to the target server.
     * @param options
     * @return 
     */
    java.util.Map getAuthenticationHeaders(java.util.Map options);
    
    /**
     * Get a list of user credentials
     * @return 
     */
    //Object getCredentials();
    
    
}
