package com.lx.core.repository.transaction;

import com.lx.core.repository.generic.EntityClassWrapper;
import com.lx.core.repository.generic.ITransaction;
import com.lx.core.repository.generic.RepositoryTransactions;
import com.lx.core.security.IUserIdentity;
import com.lx.core.utils.LxCacheUtils;
import com.lx.core.utils.ObjectUtils;
import org.sql2o.Connection;

/**
 *
 * @author lx.ds
 * @param <T>
 */
public class SaveEntityTransaction<T extends Object> implements ITransaction {

    private final Class<T> clazz;
    private final T entity;
    private final IUserIdentity user;
    
    public SaveEntityTransaction(Class<T> clazz, T entity, IUserIdentity user) {
        this.clazz = clazz;
        this.entity = entity;
        this.user = user;
    }
    
    public T getEntity() {
        return entity;
    }

    @Override
    public void execute(Connection cn) throws Exception {
        Object id;
        EntityClassWrapper classWrapper = LxCacheUtils.getInstance().getEtityClassWrapper(clazz);
        
        id = ObjectUtils.get(getEntity(), classWrapper.getPrimaryKeyColumn().getName());
        
        if (id == null) {
            id = RepositoryTransactions.getInstance().create(clazz, getEntity(), user, cn);
            ObjectUtils.set(getEntity(), classWrapper.getPrimaryKeyColumn().getName(), id);
        } else {
            RepositoryTransactions.getInstance().update(clazz, getEntity(), user, cn);
        }
    }
}
