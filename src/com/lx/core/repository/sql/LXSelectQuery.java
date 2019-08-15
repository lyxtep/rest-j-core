package com.lx.core.repository.sql;

import com.lx.core.repository.generic.EntityClassWrapper;

/**
 *
 * @author lx.ds
 */
public class LXSelectQuery extends SelectQuery {
    
    private EntityClassWrapper entityClassWrapper;
    
    public static final String ENTITY_ALIAS = "entity";
    public static final String ELEMENT_TABLE = "lxelement";
    public static final String ELEMENT_ALIAS = "el";

    public static final String STATE_TABLE_ALIAS = "tblstate";
    public static final String PARENT_TABLE_ALIAS = "tblparent";
    public static final String COMPANY_TABLE_ALIAS = "tblcompany";
    public static final String DEPARTMENT_TABLE_ALIAS = "tbldepartment";
    public static final String EMPLOYEE_TABLE_ALIAS = "tblemployee";
    
    public LXSelectQuery(EntityClassWrapper entityClassWrapper)
    {
        super();
        
        initialize(entityClassWrapper);
    }
    
    private void initialize(EntityClassWrapper entityClassWrapper) {
        
        this.entityClassWrapper = entityClassWrapper;
        
        // SELECT
        addSelectTableColumns(ENTITY_ALIAS);
        addSelectColumn(ELEMENT_ALIAS + ".companyId");
        addSelectColumn(ELEMENT_ALIAS + ".departmentId");
        addSelectColumn(ELEMENT_ALIAS + ".employeeId");
        addSelectColumn(ELEMENT_ALIAS + ".securityClass");
        addSelectColumn(ELEMENT_ALIAS + ".owner");
        addSelectColumn(ELEMENT_ALIAS + ".state");
        addSelectColumn(ELEMENT_ALIAS + ".keyword");
        addSelectColumn(ELEMENT_ALIAS + ".tags");
        addSelectColumn(ELEMENT_ALIAS + ".parentId");
        addSelectColumn(ELEMENT_ALIAS + ".priority");
        
        if (getEntityClassWrapper().isJoinState()) {
            addSelectColumn(STATE_TABLE_ALIAS + ".name AS stateName");
        }
        if (getEntityClassWrapper().isReadParent()) {
            addSelectColumn(PARENT_TABLE_ALIAS + ".name AS parentName");
        }
        if (getEntityClassWrapper().isReadCompany()) {
            addSelectColumn(COMPANY_TABLE_ALIAS + ".name AS companyName");
        }
        if (getEntityClassWrapper().isReadDepartment()) {
            addSelectColumn(DEPARTMENT_TABLE_ALIAS + ".name AS departmentName");
        }
        if (getEntityClassWrapper().isReadEmployee()) {
            addSelectColumn(EMPLOYEE_TABLE_ALIAS + ".firstName AS employeeFirstName");
            addSelectColumn(EMPLOYEE_TABLE_ALIAS + ".lastName AS employeeLastName");
        }
        
        // FROM
        addFromTable(getEntityClassWrapper().getEntityTable().getName() + " " + ENTITY_ALIAS);
        addFromInnerJoin(ELEMENT_TABLE + " " + ELEMENT_ALIAS, String.format("%1$s.id = %2$s.id AND %1$s.state <> 'inactive'", ELEMENT_ALIAS, ENTITY_ALIAS));
        if (getEntityClassWrapper().isJoinState()) {
            addFromLeftJoin(getEntityClassWrapper().getEntityTable().getStateTable() + " " + STATE_TABLE_ALIAS, 
                    String.format("%s.state = %s.%s", ELEMENT_ALIAS, STATE_TABLE_ALIAS, getEntityClassWrapper().getEntityTable().getStateJoinColumn()));
        }
        if (getEntityClassWrapper().isReadParent()) {
            addFromLeftJoin(getEntityClassWrapper().getEntityTable().getName() + " " + PARENT_TABLE_ALIAS, String.format("%s.parentId = %s.id", ELEMENT_ALIAS, PARENT_TABLE_ALIAS));
        }
        if (getEntityClassWrapper().isReadCompany()) {
            addFromLeftJoin("lxcompany " + COMPANY_TABLE_ALIAS, String.format("%s.companyId = %s.id", ELEMENT_ALIAS, COMPANY_TABLE_ALIAS));
        }
        if (getEntityClassWrapper().isReadDepartment()) {
            addFromLeftJoin("lxdepartment " + DEPARTMENT_TABLE_ALIAS, String.format("%s.departmentId = %s.id", ELEMENT_ALIAS, DEPARTMENT_TABLE_ALIAS));
        }
        if (getEntityClassWrapper().isReadEmployee()) {
            addFromLeftJoin("lxemployee " + EMPLOYEE_TABLE_ALIAS, String.format("%s.employeeId = %s.id", ELEMENT_ALIAS, EMPLOYEE_TABLE_ALIAS));
        }
    }

    /**
     * @return the entityClassWrapper
     */
    public EntityClassWrapper getEntityClassWrapper() {
        return entityClassWrapper;
    }
}
