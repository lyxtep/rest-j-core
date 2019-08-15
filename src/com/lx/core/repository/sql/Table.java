package com.lx.core.repository.sql;

/**
 *
 * @author lx.ds
 */
public class Table {

    private String name;
    private String stateTable;
    private String stateJoinColumn;
    private String resourceName;
    private String defaultSecurityClass;

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
     * @return the stateTable
     */
    public String getStateTable() {
        return stateTable;
    }

    /**
     * @param stateTable the stateTable to set
     */
    public void setStateTable(String stateTable) {
        this.stateTable = stateTable;
    }

    /**
     * @return the stateJoinColumn
     */
    public String getStateJoinColumn() {
        return stateJoinColumn;
    }

    /**
     * @param stateJoinColumn the stateJoinColumn to set
     */
    public void setStateJoinColumn(String stateJoinColumn) {
        this.stateJoinColumn = stateJoinColumn;
    }

    /**
     * @return the resourceName
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * @param resourceName the resourceName to set
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * @return the defaultSecurityClass
     */
    public String getDefaultSecurityClass() {
        return defaultSecurityClass;
    }

    /**
     * @param defaultSecurityClass the defaultSecurityClass to set
     */
    public void setDefaultSecurityClass(String defaultSecurityClass) {
        this.defaultSecurityClass = defaultSecurityClass;
    }
}
