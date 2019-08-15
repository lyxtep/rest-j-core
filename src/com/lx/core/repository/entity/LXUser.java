package com.lx.core.repository.entity;

import com.lx.core.configuration.LXPermission;
import com.lx.core.configuration.LXSecurityClass;
import com.lx.core.service.generic.MetaColumn;
import com.lx.core.service.generic.MetaData;
import com.lx.core.service.generic.MetaField;
import com.lx.core.service.generic.MetaReferencedTable;
import com.lx.core.service.generic.MetaTable;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author lx.ds
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
@MetaTable(name = "lxuser", resourceName = LXPermission.USER, defaultSecurityClass = LXSecurityClass.TOPSECRET, logColumns = "username")
public class LXUser {
    
    private String securityClass;
    private String companyId;
    private String companyName;
    private String departmentId;
    private String employeeId;

    //@MetaField(type = "hidden", length = 10)
    @MetaColumn(primaryKey = true, length = 10, allowNull = false)
    private String id;
    
    @MetaColumn(length = 10, referencedTable = @MetaReferencedTable(name = "lxgroup", primaryKey = "id", selectColumn = "name"))
    private String groupId;
    
    //@MetaField(length = 50, mandatory = true, priority = 10, group = "Basic")
    @MetaColumn(length = 50, unique = true, allowNull = false, search = true)
    private String username;
    
    @MetaData(secret = true)
    @MetaField(length = 50, mandatory = true, priority = 20, group = "Basic")
    @MetaColumn(length = 50, allowNull = false)
    private String password;
        
    @MetaColumn(length = 20)
    private String locale;
    
    @MetaField(length = 200, mandatory = true, priority = 40, group = "Basic")
    @MetaColumn(length = 200, allowNull = false, search = true)
    private String displayName;

    /**
     * @return the securityClass
     */
    public String getSecurityClass() {
        return securityClass;
    }

    /**
     * @param securityClass the securityClass to set
     */
    public void setSecurityClass(String securityClass) {
        this.securityClass = securityClass;
    }

    /**
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the departmentId
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
