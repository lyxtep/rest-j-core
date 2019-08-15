package com.lx.core.repository.entity;

import com.lx.core.configuration.LXPermission;
import com.lx.core.configuration.LXSecurityClass;
import com.lx.core.service.generic.MetaColumn;
import com.lx.core.service.generic.MetaTable;

/**
 *
 * @author lx.ds
 */
@MetaTable(name = "lxlocation", resourceName = LXPermission.LOCATION, defaultSecurityClass = LXSecurityClass.OFFICIAL, logColumns = "name")
public class LXLocation {
    
    private String tags;

    @MetaColumn(primaryKey = true, length = 10, allowNull = false)
    private String id;
        
    @MetaColumn(length = 200, allowNull = false)
    private String name;
        
    @MetaColumn(length = 10, allowNull = false)
    private String code;

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

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
}
