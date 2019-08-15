package com.lx.core.repository.sql;

import java.util.List;

/**
 *
 * @author lx.ds
 */
public interface ISqlProvider {
    
    String createDbSql(String name);
    
    String createTableSql(String tableName, List<Column> columns);
    
    
    String getInsertSql(String tableName, List<Column> columns);
    
    String getUpdateSql(String tableName, List<Column> columns) throws Exception;
    
    String getSelectByIdSql(String tableName, String idColumn);
    
    String getSelectByColumnsSql(String tableName, List<Column> columns);
    
    String getSelectAllSql(String tableName);
    
    String getDeleteSql(String tableName, String idColumn);
    

    String getDataType(String type, int length, int scale);
    
    String addPagingToSql(String sql, int start, int limit);
}
