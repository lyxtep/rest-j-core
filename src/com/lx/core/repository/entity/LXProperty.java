package com.lx.core.repository.entity;

import com.lx.core.configuration.LXPermission;
import com.lx.core.configuration.LXSecurityClass;
import com.lx.core.service.generic.MetaColumn;
import com.lx.core.service.generic.MetaField;
import com.lx.core.service.generic.MetaTable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author lx.ds
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@MetaTable(name = "lxproperty", resourceName = LXPermission.PROPERTY, defaultSecurityClass = LXSecurityClass.TOPSECRET, logColumns = "name,keyword")
public class LXProperty {
    
    private String keyword;
    
    @MetaField(type = "hidden", length = 10)
    @MetaColumn(primaryKey = true, allowNull = false)
    private String id;
    
    @MetaField(length = 200, mandatory = true, priority = 10, group = "Basic")
    @MetaColumn
    private String name;
    
    @MetaField(length = 500, mandatory = true, priority = 30, group = "Basic")
    @MetaColumn
    private String value;

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
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
