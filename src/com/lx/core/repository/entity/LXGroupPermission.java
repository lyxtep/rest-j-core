package com.lx.core.repository.entity;

import com.lx.core.configuration.LXPermission;
import com.lx.core.configuration.LXSecurityClass;
import com.lx.core.service.generic.MetaColumn;
import com.lx.core.service.generic.MetaReferencedTable;
import com.lx.core.service.generic.MetaTable;

/**
 *
 * @author lx.ds
 */
@MetaTable(name = "lxgrouppermission", resourceName = LXPermission.GROUP_PERMISSION, defaultSecurityClass = LXSecurityClass.TOPSECRET, logColumns = "name")
public class LXGroupPermission {
    
    private String keyword;

    @MetaColumn(primaryKey = true, length = 10, allowNull = false)
    private String id;
    
    @MetaColumn(length = 10, allowNull = false, referencedTable = @MetaReferencedTable(name = "lxgroup", primaryKey = "id", selectColumn = "name"))
    private String groupId;
        
    @MetaColumn(length = 20, allowNull = false)
    private String type;
        
    @MetaColumn(length = 15, allowNull = false)
    private String permission;

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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }
}
