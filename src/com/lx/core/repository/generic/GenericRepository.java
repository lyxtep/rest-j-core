package com.lx.core.repository.generic;

import com.lx.core.configuration.LXDbConfig;
import com.lx.core.repository.common.DbConnectionFactory;
import com.lx.core.repository.entity.LXElement;
import com.lx.core.repository.sql.ISqlProvider;
import com.lx.core.repository.transaction.ReadEntitiesTransaction;
import com.lx.core.repository.transaction.ReadEntityTransaction;
import com.lx.core.repository.transaction.SaveEntitiesTransaction;
import com.lx.core.request.SearchRequest;
import com.lx.core.security.IUserIdentity;
import com.lx.core.service.generic.MetadataService;
import com.lx.core.utils.ObjectUtils;
import com.lx.core.utils.StringUtils;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

/**
 *
 * @author lx.ds
 * @param <TEntity>
 */
public class GenericRepository<TEntity extends Object> implements IGenericRepository<TEntity> {

    private final Class<TEntity> clazz;

    public GenericRepository(Class<TEntity> clazz) {
        this.clazz = clazz;
    }
    
    @Override
    public String create(TEntity entity, IUserIdentity user) throws Exception {

        String id;

        try (Connection cn = DbConnectionFactory.getInstance().beginTransaction()) {

            id = RepositoryTransactions.getInstance().create(clazz, entity, user, cn);

            //COMMIT Transaction
            cn.commit();
        }

        return id;
    }

    @Override
    public TEntity get(String id, IUserIdentity user) throws Exception {
        ReadEntityTransaction<TEntity> transaction = new ReadEntityTransaction(clazz, id, user);
        
        try (Connection cn = DbConnectionFactory.getInstance().getConnection()) {
            transaction.execute(cn);
        }
        
        return transaction.getEntity();
    }

    @Override
    public List<TEntity> get(SearchRequest params, IUserIdentity user) throws Exception {
        ReadEntitiesTransaction<TEntity> transaction = new ReadEntitiesTransaction(clazz, params, user);
        
        try (Connection cn = DbConnectionFactory.getInstance().getConnection()) {
            transaction.execute(cn);
        }
        
        return transaction.getEntities();
    }

    @Override
    public void update(String id, TEntity entity, IUserIdentity user) throws Exception {

        if (!StringUtils.isNullOrEmpty(id)) {
            ObjectUtils.set(entity, "id", id);
        }
        
        try (Connection cn = DbConnectionFactory.getInstance().beginTransaction()) {

            RepositoryTransactions.getInstance().update(clazz, entity, user, cn);

            //COMMIT Transaction
            cn.commit();
        }
    }

    @Override
    public void delete(String id) throws Exception {
        try (Connection cn = DbConnectionFactory.getInstance().beginTransaction()) {
            // delete element item
            cn.createQuery(DbConnectionFactory.getInstance().getSqlProvider().getDeleteSql(LXElement.class.getSimpleName(), "id"))
                    .addParameter("id", id)
                    .executeUpdate();
            // delete class table item
            cn.createQuery(DbConnectionFactory.getInstance().getSqlProvider().getDeleteSql(clazz.getSimpleName(), "id"))
                    .addParameter("id", id)
                    .executeUpdate();
            cn.commit();
        }
    }
    
    
    @Override
    public List<TEntity> save(List<TEntity> entities, IUserIdentity user) throws Exception {

        SaveEntitiesTransaction transaction = new SaveEntitiesTransaction(clazz, entities, user);

        try (Connection cn = DbConnectionFactory.getInstance().beginTransaction()) {

            transaction.execute(cn);

            //COMMIT Transaction
            cn.commit();
        }

        return transaction.getEntities();
    }

    @Override
    public String createTable(Class<TEntity> clazz) {
        Connection cn = null;
        String sql = "";

        try {
            cn = DbConnectionFactory.getInstance().getConnection();
            ISqlProvider sqlProvider = DbConnectionFactory.getInstance().getSqlProvider();
            /*java.util.List<com.lx.core.repository.sql.Column> columns = MetadataService.getInstance().getTableColumns(clazz);
            for (com.lx.core.repository.sql.Column aColumn : columns) {
                result += aColumn.getName() + ": " + aColumn.getDateType() + " - " + aColumn.getLength() + "; ";
            }*/
            sql = sqlProvider.createTableSql(MetadataService.getInstance().getTable(clazz).getName(), MetadataService.getInstance().getTableColumns(clazz));
            cn.createQuery(sql).executeUpdate();

        } finally {
            if (cn != null) {
                cn.close();
            }
        }

        return "SQL Query: " + sql;
    }

    @Override
    public String createDB() {
        Connection cn = null;
        String sql = "";

        try {
            try {
                // Constructor
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            }
            
            Sql2o factory = new Sql2o(String.format("jdbc:mysql://%s:%s?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC", 
                LXDbConfig.getInstance().getDbHost(), LXDbConfig.getInstance().getDbPort()), 
                LXDbConfig.getInstance().getDbUsername(), LXDbConfig.getInstance().getDbPassword());
            
            cn = factory.open();
            
            /*cn = DbConnectionFactory.getInstance().getConnection();*/
            ISqlProvider sqlProvider = DbConnectionFactory.getInstance().getSqlProvider();
            sql = sqlProvider.createDbSql(LXDbConfig.getInstance().getDbName());
            cn.createQuery(sql).executeUpdate();

        } finally {
            if (cn != null) {
                cn.close();
            }
        }

        return "SQL Query: " + sql;
    }
}
