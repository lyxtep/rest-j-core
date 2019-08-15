package com.lx.core.repository.sql;

/**
 *
 * @author lx.ds
 */
public class Column {
    
    private String fieldName;
    
    private String name;
    
    private String alias;
    
    private String dataType;
    
    private int length;
    
    private int scale;

    private boolean allowNull;
    
    private Object defaultValue;
    
    private boolean primaryKey;
    
    private String foreignTable;
    
    private String foreignColumns;
    
    private String foreignSelectColumns;
    
    private String foreignSelectAliases;
    
    private String foreignProperty;
    
    private boolean unique;
    
    private boolean search;

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the scale
     */
    public int getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * @return the allowNull
     */
    public boolean isAllowNull() {
        return allowNull;
    }

    /**
     * @param allowNull the allowNull to set
     */
    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

    /**
     * @return the defaultValue
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the primaryKey
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * @return the foreignTable
     */
    public String getForeignTable() {
        return foreignTable;
    }

    /**
     * @param foreignTable the foreignTable to set
     */
    public void setForeignTable(String foreignTable) {
        this.foreignTable = foreignTable;
    }

    /**
     * @return the foreignColumns
     */
    public String getForeignColumns() {
        return foreignColumns;
    }

    /**
     * @param foreignColumns the foreignColumns to set
     */
    public void setForeignColumns(String foreignColumns) {
        this.foreignColumns = foreignColumns;
    }

    /**
     * @return the foreignSelectColumns
     */
    public String getForeignSelectColumns() {
        return foreignSelectColumns;
    }

    /**
     * @param foreignSelectColumns the foreignSelectColumns to set
     */
    public void setForeignSelectColumns(String foreignSelectColumns) {
        this.foreignSelectColumns = foreignSelectColumns;
    }

    /**
     * @return the foreignSelectAliases
     */
    public String getForeignSelectAliases() {
        return foreignSelectAliases;
    }

    /**
     * @param foreignSelectAliases the foreignSelectAliases to set
     */
    public void setForeignSelectAliases(String foreignSelectAliases) {
        this.foreignSelectAliases = foreignSelectAliases;
    }

    /**
     * @return the foreignProperty
     */
    public String getForeignProperty() {
        return foreignProperty;
    }

    /**
     * @param foreignProperty the foreignProperty to set
     */
    public void setForeignProperty(String foreignProperty) {
        this.foreignProperty = foreignProperty;
    }

    /**
     * @return the unique
     */
    public boolean isUnique() {
        return unique;
    }

    /**
     * @param unique the unique to set
     */
    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    /**
     * @return the search
     */
    public boolean isSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(boolean search) {
        this.search = search;
    }
}
