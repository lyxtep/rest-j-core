package com.lx.core.repository.generic;

import com.lx.core.repository.sql.Column;
import com.lx.core.service.generic.MetadataService;
import com.lx.core.utils.LxCacheUtils;
import com.lx.core.utils.ObjectUtils;
import java.util.List;

/**
 *
 * @author lx.ds
 */
public class EntityObjectWrapper {
    
    private EntityClassWrapper classWrapper;
    private Object entity;
    private String id;
    private List<Column> dirtyColumns;
    
    private String companyId;
    private String departmentId;
    private String employeeId;
    private String securityClass;
    private String state;
    private String keyword;
    private String tags;
    private String parentId;
    private int priority;

    public EntityObjectWrapper(Object entity) {
        initialize(entity);
    }
    
    private void initialize(Object entity) {
        this.classWrapper = LxCacheUtils.getInstance().getEtityClassWrapper(entity.getClass());
        this.entity = entity;


        if (this.classWrapper.getPrimaryKeyColumn() != null && this.entity != null)
        {
            this.id = ObjectUtils.get(this.entity, this.classWrapper.getPrimaryKeyColumn().getFieldName());
        }
        
        this.companyId = ObjectUtils.getFieldValue(entity, "company", "id");
        this.departmentId = ObjectUtils.getFieldValue(entity, "department", "id");
        this.employeeId = ObjectUtils.getFieldValue(entity, "employee", "id");
        this.securityClass = ObjectUtils.get(entity, "securityClass");
        this.state = ObjectUtils.get(entity, "state");
        this.keyword = ObjectUtils.get(entity, "keyword");
        this.tags = ObjectUtils.get(entity, "tags");
        this.parentId = ObjectUtils.getFieldValue(entity, "parent", "id");
        if (ObjectUtils.get(entity, "priority") != null) {
            this.priority = ObjectUtils.get(entity, "priority");
        }
    }
    
    public List<Column> getDirtyColumns() {
        if (dirtyColumns == null) {
            dirtyColumns = MetadataService.getInstance().getDirtyColumns(getClassWrapper().getEntityClass(), getEntity());
        }
        return dirtyColumns;
    }

    /**
     * @return the classWrapper
     */
    public EntityClassWrapper getClassWrapper() {
        return classWrapper;
    }

    /**
     * @return the entity
     */
    public Object getEntity() {
        return entity;
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
        if (getClassWrapper().getPrimaryKeyColumn() != null) {
            ObjectUtils.set(getEntity(), getClassWrapper().getPrimaryKeyColumn().getFieldName(), id);
        }
    }

    /**
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @return the departmentId
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @return the securityClass
     */
    public String getSecurityClass() {
        return securityClass;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @return the tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }
}
