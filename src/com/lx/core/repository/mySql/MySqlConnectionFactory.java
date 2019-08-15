
package com.lx.core.repository.mySql;

import com.lx.core.configuration.LXDbConfig;
import com.lx.core.repository.common.IConnectionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

/**
 *
 * @author lx.ds
 */
public class MySqlConnectionFactory implements IConnectionFactory {

    private final Sql2o factory;
    private Sql2o factoryCreateDb = null;

    public MySqlConnectionFactory()
    {
        try {
            // Constructor
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(MySqlConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        factory = new Sql2o(String.format("jdbc:mysql://%s:%s/%s?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC", 
                LXDbConfig.getInstance().getDbHost(), LXDbConfig.getInstance().getDbPort(), LXDbConfig.getInstance().getDbName()), 
                LXDbConfig.getInstance().getDbUsername(), LXDbConfig.getInstance().getDbPassword());
    }

    @Override
    public Connection getCreateDbConnection() {
        if (factoryCreateDb == null) {
            factoryCreateDb = new Sql2o(String.format("jdbc:mysql://%s:%s?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC", 
                LXDbConfig.getInstance().getDbHost(), LXDbConfig.getInstance().getDbPort()), 
                LXDbConfig.getInstance().getDbUsername(), LXDbConfig.getInstance().getDbPassword());
        }
        return factoryCreateDb.open();
    }
    
    @Override
    public Connection getConnection() {
        return factory.open();
    }
    
    @Override
    public Connection beginTransaction() {
        return factory.beginTransaction();
    }
    
}
