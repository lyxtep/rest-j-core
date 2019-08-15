package com.lx.core.repository.mySql;

import com.lx.core.repository.sql.Column;
import com.lx.core.repository.sql.SqlProvider;
import com.lx.core.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lx.ds
 */
public class MySqlProvider extends SqlProvider {

    @Override
    public String addPagingToSql(String sql, int start, int limit) {
        return String.format("%s LIMIT %s OFFSET %s", sql, limit > 0 ? limit : 1000, start);
    }
    
    @Override
    public String createDbSql(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("CREATE DATABASE `%s` /*!40100 COLLATE 'utf8_general_ci' */", name));
        
        return sb.toString();
    }
    
    @Override
    public String createTableSql(String tableName, List<Column> columns) {
        List<Column> pkColumns = new ArrayList<Column>();
        List<Column> fkColumns = new ArrayList<Column>();
        List<Column> uniqueColumns = new ArrayList<Column>();
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("CREATE TABLE %s", tableName));
        sb.append("(");

        int cnt = columns.size();
        for (int i = 0; i < cnt; i++) {
            Column aColumn = columns.get(i);

            // add primary key columns
            if (aColumn.isPrimaryKey()) {
                pkColumns.add(aColumn);
            }
            // add foreign key columns
            if (!StringUtils.isNullOrEmpty(aColumn.getForeignTable())) {
                fkColumns.add(aColumn);
            }
            // add unique columns
            if (aColumn.isUnique()) {
                uniqueColumns.add(aColumn);
            }

            if (i != 0) {
                sb.append(",");
            }

            sb.append(String.format("%s %s", aColumn.getName(), aColumn.getDataType()));
            if (aColumn.isPrimaryKey() || !aColumn.isAllowNull()) {
                sb.append(" NOT NULL");
            }

            if (aColumn.getDefaultValue() != null) {
                sb.append(String.format(" DEFAULT '%s'", aColumn.getDefaultValue()));
            }
        }

        // Primary Keys
        if (pkColumns.size() > 0) {
            List<String> arr = new ArrayList<String>();
            for (int i = 0; i < pkColumns.size(); i++) {
                arr.add(pkColumns.get(i).getName());
            }
            sb.append(String.format(", CONSTRAINT pk_%s_%s PRIMARY KEY (%s)", 
                    tableName, StringUtils.join("_", arr), StringUtils.join(",", arr)));
        }
        // Foreign Keys
        if (fkColumns.size() > 0)
        {
            for (int i = 0; i < fkColumns.size(); i++)
            {
                Column aColumn = fkColumns.get(i);
                sb.append(String.format(", CONSTRAINT fk_%s_%s_%s FOREIGN KEY (%s) REFERENCES %s(%s)", 
                        tableName, aColumn.getForeignTable(), aColumn.getForeignColumns().replace(",","_"), 
                        aColumn.getName(), aColumn.getForeignTable(), aColumn.getForeignColumns()));
            }
        }
        // Unique
        if (uniqueColumns.size() > 0)
        {
            List<String> arr = new ArrayList<String>();
            for (int i = 0; i < uniqueColumns.size(); i++) {
                arr.add(uniqueColumns.get(i).getName());
            }
            sb.append(String.format(", CONSTRAINT uc_%s_%s UNIQUE (%s)", 
                    tableName, StringUtils.join("_", arr), StringUtils.join(",", arr)));
        }


            sb.append(")");

            return sb.toString();
    }

    @Override
    public String getDataType(String type, int length, int scale) {
        String dataType;
        
        switch (type)
        {
            case "java.lang.String":
                dataType = String.format("VARCHAR(%s)", length > 0 ? length : 50);
                break;
            case "int":
            case "java.lang.Integer":
                dataType = "INT";
                break;
            case "java.lang.Long":
                dataType = "BIGINT";
                break;
            case "java.util.Date":
                dataType = "DATETIME";
                break;
            case "java.math.BigDecimal":
                dataType = String.format("DECIMAL(%s,%s)", length > 0 ? length : 11, scale > 0 ? scale : 3);
                break;
            case "boolean":
            case "java.lang.Boolean":
                dataType = "BIT";
                break;
            default:
                dataType = type;
                break;

        }

        return dataType;
    }
    
}
