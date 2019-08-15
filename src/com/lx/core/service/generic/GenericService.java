package com.lx.core.service.generic;

import com.lx.core.repository.common.DbConnectionFactory;
import com.lx.core.repository.entity.LXElement;
import com.lx.core.repository.entity.LXEntryProperty;
import com.lx.core.repository.generic.GenericRepository;
import com.lx.core.repository.generic.ITransaction;
import com.lx.core.request.SearchRequest;
import com.lx.core.security.IUserIdentity;
import java.util.HashMap;
import java.util.List;
import org.sql2o.Connection;

/**
 *
 * @author lx.ds
 */
public class GenericService {
    
    // Singleton pattern
    private static GenericService instance = null;
    
    private final HashMap _cache;

    // Singleton pattern
    private GenericService() {
        // Constructor
        _cache = new HashMap();
    }

    // Singleton pattern
    public static synchronized GenericService getInstance() {
        if (instance == null) {
            instance = new GenericService();
        }

        return instance;
    }
    
    /**
     * CREATE: create entity to db
     * @param <TEntity>
     * @param entity
     * @param clazz
     * @param user
     * @return entity
     */
    public synchronized <TEntity extends Object> TEntity create(Class<TEntity> clazz, TEntity entity, IUserIdentity user) throws Exception {
        getRepository(clazz).create(entity, user);
        return entity;
    }
    
    /**
     * READ: get entity by id
     * @param <TEntity>
     * @param clazz class of entity to return
     * @param id primary key value of the entity
     * @param user
     * @return entity
     */
    public synchronized <TEntity extends Object> TEntity getById(Class<TEntity> clazz, String id, IUserIdentity user) throws Exception {
        return getRepository(clazz).get(id, user);
    }
    
    /**
     * Search entities
     * @param <TEntity> entity class must extends Object
     * @param clazz entity class
     * @param entity
     * @param user logged user
     * @return list of found entities
     */
    public synchronized <TEntity extends Object> List<TEntity> get(Class<TEntity> clazz, TEntity entity, IUserIdentity user) throws Exception {
        return getRepository(clazz).get(new SearchRequest(0, 0, null, null, entity), user);
    }
    
    /**
     * Search entities
     * @param <TEntity> entity class must extends Object
     * @param clazz entity class
     * @param params SearchRequest object parameters for advanced search (put 0 or null to ignore some value)
     * @param user logged user
     * @return list of found entities
     */
    public synchronized <TEntity extends Object> List<TEntity> search(Class<TEntity> clazz, SearchRequest params, IUserIdentity user) throws Exception {
        return getRepository(clazz).get(params, user);
    }
    
    public synchronized List<LXEntryProperty> getSettings(String entryId, IUserIdentity user) throws Exception {
        
        LXEntryProperty query = new LXEntryProperty();
        query.setEntryId(entryId);
        return getRepository(LXEntryProperty.class).get(new SearchRequest(0, 0, null, null, query), user);
    }
    
    /**
     * UPDATE: update entity to db
     * @param <TEntity>
     * @param id
     * @param entity
     * @param clazz
     * @param user
     * @return entity
     * @throws java.lang.Exception
     */
    public synchronized <TEntity extends Object> TEntity update(Class<TEntity> clazz, String id, TEntity entity, IUserIdentity user) throws Exception {
        getRepository(clazz).update(id, entity, user);
        return entity;
    }
    
    /**
     * Save list of entries (create if primary key is null, and update if not)
     * @param <TEntity>
     * @param clazz
     * @param properties
     * @param user
     * @return
     * @throws Exception 
     */
    public synchronized <TEntity extends Object> List<TEntity> save(Class<TEntity> clazz, List<TEntity> properties, IUserIdentity user) throws Exception {
        return getRepository(clazz).save(properties, user);
    }
    
    /**
     * DELETE: delete entity from db
     * @param <TEntity>
     * @param id
     * @param clazz
     */
    public synchronized <TEntity extends Object> void delete(Class<TEntity> clazz,String id) throws Exception {
        getRepository(clazz).delete(id);
    }
    
    /**
     * Execute list of transactions
     * @param transactions
     * @throws Exception 
     */
    public synchronized void executeTransactions(List<ITransaction> transactions) throws Exception {
        
        try (Connection cn = DbConnectionFactory.getInstance().beginTransaction()) {

            for (ITransaction aTransaction : transactions) {
                aTransaction.execute(cn);
            }

            //COMMIT Transaction
            cn.commit();
        }
    }
    
    public synchronized String createDB() {
        return getRepository(LXElement.class).createDB();
    }
    
    public synchronized <TEntity extends Object> String createTable(Class<TEntity> clazz) {
        return getRepository(clazz).createTable(clazz);
    }
    
    private synchronized <TEntity extends Object> GenericRepository<TEntity> getRepository(Class<TEntity> clazz)
    {
        //return (GenericRepository<TEntity>)RepositoryFactory.getInstance().getRepository(clazz);
        GenericRepository<TEntity> repository = (GenericRepository<TEntity>)_cache.get(clazz.getCanonicalName() + "-repository");
        if (repository == null)
        {
            repository = new GenericRepository<TEntity>(clazz);
            _cache.put(clazz.getCanonicalName() + "-repository", repository);
        }
        return repository;
    }
}
