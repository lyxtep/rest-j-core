package com.lx.core.repository.generic;

import com.lx.core.repository.sql.Column;
import com.lx.core.repository.sql.Table;
import com.lx.core.service.generic.MetadataService;
import com.lx.core.utils.ClassUtils;
import com.lx.core.utils.StringUtils;
import java.util.List;

/**
 *
 * @author lx.ds
 */
public class EntityClassWrapper {

    private Class entityClass;
    private Table entityTable;
    private List<Column> entityColumns;
    private Column primaryKeyColumn;
    
    private boolean joinState;
    private boolean readParent;
    private boolean readCompany;
    private boolean readDepartment;
    private boolean readEmployee;
    private boolean orderByPriority;
    
    private boolean keywordExist;
    private boolean tagsExist;
    
    public EntityClassWrapper(Class clazz) {
        initialize(clazz);
    }
    
    private void initialize(Class clazz) {
        this.entityClass = clazz;
        this.entityTable = MetadataService.getInstance().getTable(clazz);
        this.entityColumns = MetadataService.getInstance().getTableColumns(clazz);
        this.primaryKeyColumn = MetadataService.getInstance().getPrimaryKeyColumn(clazz);
        
        this.joinState = (ClassUtils.getInstance().hasProperty(getEntityClass(), "stateName") 
                && !StringUtils.isNullOrEmpty(getEntityTable().getStateTable()) 
                && !StringUtils.isNullOrEmpty(getEntityTable().getStateJoinColumn()));
        readParent = ClassUtils.getInstance().hasProperty(clazz, "parentName");
        readCompany = ClassUtils.getInstance().hasProperty(clazz, "companyName");
        readDepartment = ClassUtils.getInstance().hasProperty(clazz, "departmentName");
        readEmployee = ClassUtils.getInstance().hasProperty(clazz, "employeeFirstName") || ClassUtils.getInstance().hasProperty(clazz, "employeeLastName");
        orderByPriority = ClassUtils.getInstance().hasProperty(clazz, "priority");
        
        keywordExist = ClassUtils.getInstance().hasProperty(clazz, "keyword");
        tagsExist = ClassUtils.getInstance().hasProperty(clazz, "tags");
    }

    /**
     * @return the entityClass
     */
    public Class getEntityClass() {
        return entityClass;
    }

    /**
     * @return the entityTable
     */
    public Table getEntityTable() {
        return entityTable;
    }

    /**
     * @return the entityColumns
     */
    public List<Column> getEntityColumns() {
        return entityColumns;
    }

    /**
     * @return the primaryKeyColumn
     */
    public Column getPrimaryKeyColumn() {
        return primaryKeyColumn;
    }

    /**
     * @return the joinState
     */
    public boolean isJoinState() {
        return joinState;
    }

    /**
     * @return the readParent
     */
    public boolean isReadParent() {
        return readParent;
    }

    /**
     * @return the readCompany
     */
    public boolean isReadCompany() {
        return readCompany;
    }

    /**
     * @return the readDepartment
     */
    public boolean isReadDepartment() {
        return readDepartment;
    }

    /**
     * @return the readEmployee
     */
    public boolean isReadEmployee() {
        return readEmployee;
    }

    /**
     * @return the orderByPriority
     */
    public boolean isOrderByPriority() {
        return orderByPriority;
    }

    /**
     * @return the keywordExist
     */
    public boolean isKeywordExist() {
        return keywordExist;
    }

    /**
     * @return the tagsExist
     */
    public boolean isTagsExist() {
        return tagsExist;
    }
}
