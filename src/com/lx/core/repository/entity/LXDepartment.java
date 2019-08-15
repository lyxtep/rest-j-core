package com.lx.core.repository.entity;

import com.lx.core.configuration.LXPermission;
import com.lx.core.configuration.LXSecurityClass;
import com.lx.core.service.generic.MetaColumn;
import com.lx.core.service.generic.MetaTable;

/**
 *
 * @author lx.ds
 */
@MetaTable(name = "lxdepartment", resourceName = LXPermission.DEPARTMENT, defaultSecurityClass = LXSecurityClass.RESTRICTED, logColumns = "name")
public class LXDepartment {

    @MetaColumn(primaryKey = true, length = 10, allowNull = false)
    private String id;
        
    @MetaColumn(length = 200, allowNull = false)
    private String name;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
