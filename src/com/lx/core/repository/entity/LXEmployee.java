package com.lx.core.repository.entity;

import com.lx.core.configuration.LXPermission;
import com.lx.core.configuration.LXSecurityClass;
import com.lx.core.service.generic.MetaColumn;
import com.lx.core.service.generic.MetaTable;

/**
 *
 * @author lx.ds
 */
@MetaTable(name = "lxemployee", resourceName = LXPermission.EMPLOYEE, defaultSecurityClass = LXSecurityClass.RESTRICTED, logColumns = "firstName,lastName")
public class LXEmployee {
    
    private String departmentId;

    @MetaColumn(primaryKey = true, length = 10, allowNull = false)
    private String id;
        
    @MetaColumn(length = 200, allowNull = false)
    private String firstName;
        
    @MetaColumn(length = 200)
    private String middleName;
        
    @MetaColumn(length = 200, allowNull = false)
    private String lastName;
        
    @MetaColumn(length = 200)
    private String email;

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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
