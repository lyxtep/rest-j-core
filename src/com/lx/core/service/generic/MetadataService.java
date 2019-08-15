package com.lx.core.service.generic;

import com.lx.core.configuration.LXConfig;
import com.lx.core.repository.common.DbConnectionFactory;
import com.lx.core.repository.sql.Column;
import com.lx.core.repository.sql.Table;
import com.lx.core.response.MetaFieldResponse;
import com.lx.core.utils.ObjectUtils;
import com.lx.core.utils.StringUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author lt.ds
 */
public class MetadataService {

    // Singleton pattern
    private static MetadataService instance = null;

    private final HashMap _cache;

    // Singleton pattern
    private MetadataService() {
        // Constructor
        _cache = new HashMap();
    }

    // Singleton pattern
    public static synchronized MetadataService getInstance() {
        if (instance == null) {
            instance = new MetadataService();
        }

        return instance;
    }
    
    public synchronized <TEntity extends Object> Column getPrimaryKeyColumn(Class<TEntity> clazz) {
        Column pkColumn = null;
        List<Column> columns = getTableColumns(clazz);
        
        for (Column aColumn : columns) {
            if (aColumn.isPrimaryKey()) {
                pkColumn = aColumn;
                break;
            }
        }
        
        return pkColumn;
    }
    
    public synchronized <TEntity extends Object> List<Column> getDirtyColumns(Class<TEntity> clazz, TEntity entity) {
        List<Column> results = new ArrayList<Column>();
        List<Column> columns = getTableColumns(clazz);
        
        for (Column aColumn : columns) {
            if (!ObjectUtils.isDefaultValue(entity, aColumn.getName())) {
                results.add(aColumn);
            }
        }
        
        return results;
    }
    
    public Table getTable(Class clazz) {
        Table table;

        table = new Table();

        MetaTable metaTable = (MetaTable)clazz.getAnnotation(MetaTable.class);

        if (metaTable != null)
        {
            table.setName(metaTable.name());
            table.setStateTable(metaTable.state().tableName());
            table.setStateJoinColumn(metaTable.state().joinColumn());
            table.setResourceName(metaTable.resourceName());
            table.setDefaultSecurityClass(metaTable.defaultSecurityClass());
        }

        if (table.getName() == null)
        {
            table.setName(clazz.getSimpleName());
        }

        if (table.getResourceName() == null)
        {
            table.setResourceName(clazz.getSimpleName());
        }

        if (table.getDefaultSecurityClass() == null)
        {
            table.setDefaultSecurityClass(LXConfig.getInstance().getSecurityClassDefault());
        }


        return table;
    }

    public <TEntity extends Object> List<Column> getTableColumns(Class<TEntity> clazz) {
        List<Column> tableColumns = new ArrayList<Column>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field aField : fields) {
            Column aTableColumn = new Column();
            MetaColumn aMetaColumn = aField.getAnnotation(MetaColumn.class);

            if (aMetaColumn == null) {
                continue;
            }

            aTableColumn.setFieldName(aField.getName());
            aTableColumn.setName(aMetaColumn.name().isEmpty() ? aField.getName() : aMetaColumn.name());
            if (!StringUtils.equalsIgnoreCase(aTableColumn.getName(), aField.getName())) aTableColumn.setAlias(aField.getName());
            aTableColumn.setAllowNull(aMetaColumn.allowNull());
            if (!StringUtils.nullHandler(aMetaColumn.defaultValue()).equals("void")) {
                aTableColumn.setDefaultValue(aMetaColumn.defaultValue());
            }
            aTableColumn.setLength(aMetaColumn.length());
            aTableColumn.setScale(aMetaColumn.scale());
            aTableColumn.setPrimaryKey(aMetaColumn.primaryKey());
            aTableColumn.setUnique(aMetaColumn.unique());
            aTableColumn.setForeignTable(aMetaColumn.referencedTable().name());
            aTableColumn.setForeignColumns(aMetaColumn.referencedTable().primaryKey());
            aTableColumn.setForeignSelectColumns(aMetaColumn.referencedTable().selectColumn());
            aTableColumn.setForeignSelectAliases(aMetaColumn.referencedTable().selectAlias());
            aTableColumn.setForeignProperty(aMetaColumn.referencedProperty());
            aTableColumn.setSearch(aMetaColumn.search());

            MetaField aMetaField = aField.getAnnotation(MetaField.class);
            if (aMetaField != null) {
                if (aTableColumn.isAllowNull() && aMetaField.mandatory()) {
                    aTableColumn.setAllowNull(false);
                }
                if (aTableColumn.getDefaultValue() == null && !StringUtils.nullHandler(aMetaField.defaultValue()).equals("void")) {
                    aTableColumn.setDefaultValue(aMetaField.defaultValue());
                }
                if (aTableColumn.getLength() == 0 && aMetaField.length() > 0) {
                    aTableColumn.setLength(aMetaField.length());
                }
                if (aTableColumn.getScale() == 0 && aMetaField.decimalPrecision() > 0) {
                    aTableColumn.setScale(aMetaField.decimalPrecision());
                }
            }
            
            // dependence of AllowNull and Length and Scale -> muste be at the end of function
            aTableColumn.setDataType(DbConnectionFactory.getInstance().getSqlProvider().getDataType(
                    aMetaColumn.dataType().isEmpty() ? aField.getType().getCanonicalName() : aMetaColumn.dataType(), aTableColumn.getLength(), aTableColumn.getScale()));

            tableColumns.add(aTableColumn);
        }

        return tableColumns;
    }

    public <TEntity extends Object> List<MetaFieldResponse> GetMetaFields(Class<TEntity> clazz) {
        List<MetaFieldResponse> metaFields = new ArrayList<MetaFieldResponse>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field aField : fields) {
            // get Meta Field annotation
            MetaField aMetaField = aField.getAnnotation(MetaField.class);

            if (aMetaField == null) {
                continue;
            }

            MetaFieldResponse aMetaFieldResponse = new MetaFieldResponse();

            aMetaFieldResponse.setName(StringUtils.isNullOrEmpty(aMetaField.name()) ? aField.getName() : aMetaField.name());
            aMetaFieldResponse.setType(mapPropertyType(StringUtils.isNullOrEmpty(aMetaField.type()) ? aField.getType().getCanonicalName() : aMetaField.type()));
            // send null value to client if empty
            if (!aMetaField.label().isEmpty()) {
                aMetaFieldResponse.setLabel(aMetaField.label());
            }
            aMetaFieldResponse.setMandatory(aMetaField.mandatory());
            aMetaFieldResponse.setReadOnly(aMetaField.readOnly());
            aMetaFieldResponse.setRemoteQuery(aMetaField.remoteQuery());
            if (!StringUtils.nullHandler(aMetaField.defaultValue()).equals("void")) {
                aMetaFieldResponse.setDefaultValue(aMetaField.defaultValue());
            }
            // send null value to client if empty
            if (!aMetaField.validation().isEmpty()) {
                aMetaFieldResponse.setValidation(aMetaField.validation());
            }
            aMetaFieldResponse.setMinValue(aMetaField.minValue());
            aMetaFieldResponse.setMaxValue(aMetaField.maxValue());
            aMetaFieldResponse.setMaxLength(aMetaField.length());
            aMetaFieldResponse.setDecimalPrecision(aMetaField.decimalPrecision());
            aMetaFieldResponse.setPriority(aMetaField.priority());
            // send null value to client if empty
            if (!aMetaField.group().isEmpty()) {
                aMetaFieldResponse.setGroup(aMetaField.group());
            }
            // send null value to client if empty
            if (!aMetaField.data().isEmpty()) {
                aMetaFieldResponse.setData(aMetaField.data());
            }
            // send null value to client if empty
            if (!aMetaField.event().isEmpty()) {
                aMetaFieldResponse.setEvent(aMetaField.event());
            }
            aMetaFieldResponse.setWidth(aMetaField.width());
            aMetaFieldResponse.setFlex(aMetaField.flex());
            aMetaFieldResponse.setRow(aMetaField.row());

            MetaColumn aMetaColumn = aField.getAnnotation(MetaColumn.class);
            if (aMetaColumn != null) {
                if (!aMetaFieldResponse.getMandatory() && !aMetaColumn.allowNull()) {
                    aMetaFieldResponse.setMandatory(true);
                }
                if (aMetaFieldResponse.getDefaultValue() == null && !StringUtils.nullHandler(aMetaColumn.defaultValue()).equals("void")) {
                    aMetaFieldResponse.setDefaultValue(aMetaColumn.defaultValue());
                }
                if (aMetaFieldResponse.getMaxLength() == 0 && aMetaColumn.length() > 0) {
                    aMetaFieldResponse.setMaxLength(aMetaColumn.length());
                }
                if (aMetaFieldResponse.getDecimalPrecision() == 0 && aMetaColumn.scale() > 0) {
                    aMetaFieldResponse.setDecimalPrecision(aMetaColumn.scale());
                }
            }

            aMetaFieldResponse.setName(StringUtils.firstCharacterToLower(aMetaFieldResponse.getName()));
            metaFields.add(aMetaFieldResponse);
        }

        if (metaFields.size() > 0) {
            Collections.sort(metaFields, new Comparator<MetaFieldResponse>() {
                @Override
                public int compare(MetaFieldResponse o1, MetaFieldResponse o2) {
                    if (o1.getPriority() == o2.getPriority()) {
                        return 0;
                    }
                    return o1.getPriority() < o2.getPriority() ? -1 : 1;
                }
            });
        }
        return metaFields;
    }

    public <TEntity extends Object> List<TEntity> parseMetaData(List<TEntity> entities) {
        for (TEntity anEntity : entities) {
            parseMetaData(anEntity);
        }
        
        return entities;
    }
    public <TEntity extends Object> TEntity parseMetaData(TEntity entity) {
        
        if (entity == null) {
            return null;
        }
        
        Field[] fields = entity.getClass().getDeclaredFields();
        MetaData aMetaData;
        
        for (Field aField : fields) {
            aMetaData = aField.getAnnotation(MetaData.class);

            if (aMetaData == null) {
                continue;
            }
            
            if (aMetaData.secret()) {
                ObjectUtils.reset(entity, aField.getName());
            }
        }
        
        return entity;
    }
    public <T extends Object> List<Field> getMetaDataFields(Class<T> clazz) {
        List<Field> results = new ArrayList<Field>();
        Field[] fields = clazz.getDeclaredFields();
        MetaData aMetaData;
        
        for (Field aField : fields) {
            aMetaData = aField.getAnnotation(MetaData.class);

            if (aMetaData == null) {
                continue;
            }
            
            results.add(aField);
        }
        
        return results;
    }
    
    private String mapPropertyType(String type) {
        String metaFieldType;

        switch (type) {
            case "java.lang.String":
                metaFieldType = "string";
                break;
            case "int":
            case "java.lang.Integer":
                metaFieldType = "integer";
                break;
            case "java.util.Date":
                metaFieldType = "date";
                break;
            case "java.math.BigDecimal":
                metaFieldType = "decimal";
                break;
            case "boolean":
            case "java.lang.Boolean":
                metaFieldType = "bool";
                break;
            default:
                metaFieldType = type;
                break;

        }

        return metaFieldType;
    }
}
