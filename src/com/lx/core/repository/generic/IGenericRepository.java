package com.lx.core.repository.generic;

import com.lx.core.request.SearchRequest;
import com.lx.core.security.IUserIdentity;
import java.util.List;

/**
 *
 * @author lx.ds
 * @param <TEntity>
 */
public interface IGenericRepository<TEntity extends Object> {
    /* C */
    String create(TEntity entity, IUserIdentity user) throws Exception;
    /* R */
    TEntity get(String id, IUserIdentity user) throws Exception;
    List<TEntity> get(SearchRequest params, IUserIdentity user) throws Exception;
    /* U */
    void update(String id, TEntity entity, IUserIdentity user) throws Exception;
    /* D */
    void delete(String id) throws Exception;
    
    
    List<TEntity> save(List<TEntity> entities, IUserIdentity user) throws Exception;
    
    public String createDB();
    public String createTable(Class<TEntity> clazz);
}
