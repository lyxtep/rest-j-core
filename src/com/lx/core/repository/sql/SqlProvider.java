package com.lx.core.repository.sql;

import java.util.List;

/**
 *
 * @author lx.ds
 */
public class SqlProvider implements ISqlProvider {    
    
    @Override
    public String getInsertSql(String tableName, List<Column> columns) {
        
        StringBuilder sbColumns = new StringBuilder();
        StringBuilder sbValues = new StringBuilder();
        
        for (Column aColumn : columns) {
            if (sbColumns.length() > 0) {
                sbColumns.append(",");
                sbValues.append(",");
            }
            sbColumns.append(aColumn.getName());
            sbValues.append(":").append(aColumn.getName());
        }
        
        return String.format("INSERT INTO %s(%s) VALUE(%s)", tableName, sbColumns.toString(), sbValues.toString());
    }
    
    @Override
    public String getUpdateSql(String tableName, List<Column> columns) throws Exception {
        
        StringBuilder sbSet = new StringBuilder();
        StringBuilder sbWhere = new StringBuilder();
        
        for (Column aColumn : columns) {
            if (sbSet.length() > 0) {
                sbSet.append(",");
            }
            sbSet.append(aColumn.getName()).append("=:").append(aColumn.getName());
            
            if (aColumn.isPrimaryKey()) {
                if (sbWhere.length() > 0) {
                    sbWhere.append(",");
                }
                sbWhere.append(aColumn.getName()).append("=:").append(aColumn.getName());
            }
        }
        
        if (sbWhere.length() == 0) {
            throw new Exception("There is no primary key column or value!");
        }
        
        return String.format("UPDATE %s SET %s WHERE %s", tableName, sbSet.toString(), sbWhere.toString());
    }
    
    @Override
    public String getSelectByIdSql(String tableName, String idColumn) {
        return String.format("SELECT * FROM %s WHERE %s=:%s", tableName, idColumn, idColumn);
    }
    
    @Override
    public String getSelectAllSql(String tableName) {
        return String.format("SELECT * FROM %s", tableName);
    }
    
    @Override
    public String getSelectByColumnsSql(String tableName, List<Column> columns) {
        
        StringBuilder sbWhere = new StringBuilder();
        
        for (Column aColumn : columns) {
            
            if (sbWhere.length() > 0) {
                sbWhere.append(",");
            }
            sbWhere.append(aColumn.getName()).append("=:").append(aColumn.getName());
        }
        
        return String.format("SELECT * FROM %s WHERE  %s", tableName, sbWhere.toString());
    }
    
    @Override
    public String getDeleteSql(String tableName, String idColumn) {
        return String.format("DELETE FROM %s WHERE %s=:%s", tableName, idColumn, idColumn);
    }
    
    @Override
    public String createDbSql(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String createTableSql(String tableName, List<Column> columns) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDataType(String type, int length, int scale) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String addPagingToSql(String sql, int page, int size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
