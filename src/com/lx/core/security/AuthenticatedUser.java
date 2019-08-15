package com.lx.core.security;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author lx.ds
 */
public class AuthenticatedUser implements IUserIdentity, Principal {

    private String username;
    private String displayName;
    private String companyId;
    private String departmentId;
    private String employeeId;
    private String groupId;
    private String locale;
    private Map<String, String> permissions;
    
    private Set claims;
    
    public AuthenticatedUser() {
        initialize("Guest", null, null);
    }
    public AuthenticatedUser(String username, String groupId, Map<String, String> permissions) {
        initialize(username, groupId, permissions);
    }
    
    private void initialize(String username, String groupId, Map<String, String> permissions) {
        this.username = username;
        this.groupId = groupId;
        this.permissions = permissions;
        
        this.claims = getAllNames();
    }
    
    @Override
    public String getName() {
        return username;
    }
    
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getDisplayName() {
        if (displayName == null) {
            displayName = username;
        }
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    
    @Override
    public String getCompanyId() {  
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
      
    @Override
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
      
    @Override
    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    @Override
    public String getGroupId() {
        return groupId;
    }
    @Override
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public Set getAllNames() {
        Set<String> allNames = new HashSet<>();
        allNames.add(username);

        for (Entry<String, String> item : getPermissions().entrySet())
        {
            allNames.add(item.getKey());
        }
        
        return allNames;
    }
    
    @Override
    public Map<String, String> getPermissions() {
        if (permissions == null) {
            permissions = new HashMap<String, String>();
        }
        return permissions;
    }

    @Override
    public Map getAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map getAuthenticationHeaders(Map options) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the claims
     */
    public Set getClaims() {
        return claims;
    }

    /**
     * @param claims the claims to set
     */
    public void setClaims(Set claims) {
        this.claims = claims;
    }

}
