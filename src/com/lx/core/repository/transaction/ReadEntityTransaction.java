package com.lx.core.repository.transaction;

import com.lx.core.repository.generic.EntityObjectWrapper;
import com.lx.core.repository.generic.ITransaction;
import com.lx.core.repository.generic.RepositoryTransactions;
import com.lx.core.request.SearchRequest;
import com.lx.core.security.IUserIdentity;
import com.lx.core.utils.ClassUtils;
import com.lx.core.utils.ObjectUtils;
import org.sql2o.Connection;

/**
 *
 * @author lx.ds
 * @param <T>
 */
public class ReadEntityTransaction<T extends Object> implements ITransaction  {
    
    private final Class<T> clazz;
    private final String id;
    private final IUserIdentity user;
    
    private T entity;
    
    public ReadEntityTransaction(Class<T> clazz, String id, IUserIdentity user) {
        this.clazz = clazz;
        this.id = id;
        this.user = user;
    }

    @Override
    public void execute(Connection cn) throws Exception {
        EntityObjectWrapper entityWrapper = new EntityObjectWrapper(ClassUtils.getInstance().createIntance(clazz));
        
        entityWrapper.setId(id);
        
        this.entity = ObjectUtils.getUnique(
                RepositoryTransactions.getInstance().read(clazz, new SearchRequest(0, 0, null, null, entityWrapper.getEntity()), true, user, cn)
        );
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the entity
     */
    public T getEntity() {
        return entity;
    }

}
