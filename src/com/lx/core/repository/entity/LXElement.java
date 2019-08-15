package com.lx.core.repository.entity;

import com.lx.core.service.generic.MetaColumn;
import com.lx.core.service.generic.MetaTable;
import java.util.Date;

/**
 *
 * @author lx.ds
 */
@MetaTable(name = "lxelement")
public class LXElement {
    
    @MetaColumn(primaryKey = true, length = 10, allowNull = false)
    private String id;
    
    @MetaColumn(length = 10)
    private String companyId;
    
    @MetaColumn(length = 10)
    private String departmentId;
    
    @MetaColumn(length = 10)
    private String employeeId;
        
    @MetaColumn(length = 50, allowNull = false)
    private String securityClass;
        
    @MetaColumn(length = 50, allowNull = false)
    private String className;
    
    @MetaColumn(length = 100)
    private String owner;
    
    @MetaColumn(allowNull = false)
    private Date createDate;
    
    @MetaColumn(length = 100)
    private String createBy;
    
    @MetaColumn(allowNull = false)
    private Date lastupdateDate;
    
    @MetaColumn(length = 100)
    private String lastupdateBy;
    
    @MetaColumn(length = 50, allowNull = false)
    private String state;
    
    @MetaColumn(allowNull = false)
    private Date stateDate;
    
    @MetaColumn(length = 100)
    private String stateBy;
    
    @MetaColumn
    private Date effectiveDate;
    
    @MetaColumn
    private Date untilDate;
    
    @MetaColumn(length = 50)
    private String keyword;
    
    @MetaColumn(length = 200)
    private String tags;
    
    @MetaColumn(length = 10)
    private String parentId;
    
    @MetaColumn
    private Integer priority;

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
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the createBy
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy the createBy to set
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return the lastupdateDate
     */
    public Date getLastupdateDate() {
        return lastupdateDate;
    }

    /**
     * @param lastupdateDate the lastupdateDate to set
     */
    public void setLastupdateDate(Date lastupdateDate) {
        this.lastupdateDate = lastupdateDate;
    }

    /**
     * @return the lastupdateBy
     */
    public String getLastupdateBy() {
        return lastupdateBy;
    }

    /**
     * @param lastupdateBy the lastupdateBy to set
     */
    public void setLastupdateBy(String lastupdateBy) {
        this.lastupdateBy = lastupdateBy;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the stateDate
     */
    public Date getStateDate() {
        return stateDate;
    }

    /**
     * @param stateDate the stateDate to set
     */
    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    /**
     * @return the stateBy
     */
    public String getStateBy() {
        return stateBy;
    }

    /**
     * @param stateBy the stateBy to set
     */
    public void setStateBy(String stateBy) {
        this.stateBy = stateBy;
    }

    /**
     * @return the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return the untilDate
     */
    public Date getUntilDate() {
        return untilDate;
    }

    /**
     * @param untilDate the untilDate to set
     */
    public void setUntilDate(Date untilDate) {
        this.untilDate = untilDate;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
