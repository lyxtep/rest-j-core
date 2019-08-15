package com.lx.core.repository.common;

import com.lx.core.repository.mySql.MySqlConnectionFactory;
import com.lx.core.repository.mySql.MySqlProvider;
import com.lx.core.repository.sql.ISqlProvider;
import org.sql2o.Connection;

/**
 *
 * @author lx.ds
 */
public class DbConnectionFactory {

    // Singleton pattern
    private static DbConnectionFactory instance = null;


    // Singleton pattern
    private DbConnectionFactory() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }

        return instance;
    }

    public synchronized Connection getConnection() {
        return getDbConnectionProvider().getConnection();
    }

    public synchronized Connection beginTransaction() {
        return getDbConnectionProvider().beginTransaction();
    }
    
    private IConnectionFactory dbConnectionProvider;
    public IConnectionFactory getDbConnectionProvider() {
        if (dbConnectionProvider == null) {
            dbConnectionProvider = new MySqlConnectionFactory();
        }
        return dbConnectionProvider;
    }
    
    private ISqlProvider dbSqlProvider;
    public ISqlProvider getSqlProvider() {
        if (dbSqlProvider == null) {
            dbSqlProvider = new MySqlProvider();
        }
        return dbSqlProvider;
    }
}
