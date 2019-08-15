package com.lx.core.repository.transaction;

import com.lx.core.repository.generic.EntityClassWrapper;
import com.lx.core.repository.generic.ITransaction;
import com.lx.core.repository.generic.RepositoryTransactions;
import com.lx.core.security.IUserIdentity;
import com.lx.core.utils.LxCacheUtils;
import com.lx.core.utils.ObjectUtils;
import java.util.List;
import org.sql2o.Connection;

/**
 *
 * @author lx.ds
 * @param <T>
 */
public class SaveEntitiesTransaction<T extends Object> implements ITransaction {
    
    private final Class<T> clazz;
    private List<T> entities;
    private final IUserIdentity user;
    private final String deleteFlagFieldName;
    
    public SaveEntitiesTransaction(Class<T> clazz, List<T> entities, IUserIdentity user) {
        this.clazz = clazz;
        this.entities = entities;
        this.deleteFlagFieldName = null;
        this.user = user;
    }
    
    public SaveEntitiesTransaction(Class<T> clazz, List<T> entities, String deleteFlagFieldName, IUserIdentity user) {
        this.clazz = clazz;
        this.entities = entities;
        this.deleteFlagFieldName = deleteFlagFieldName;
        this.user = user;
    }

    @Override
    public void execute(Connection cn) throws Exception {
        Object id;
        boolean doDelete = false;
        
        for (T entity : getEntities()) {
            EntityClassWrapper classWrapper = LxCacheUtils.getInstance().getEtityClassWrapper(clazz);
            id = ObjectUtils.get(entity, classWrapper.getPrimaryKeyColumn().getName());

            if (id == null) {
                id = RepositoryTransactions.getInstance().create(clazz, entity, user, cn);
                ObjectUtils.set(entity, classWrapper.getPrimaryKeyColumn().getName(), id);
            } else {
                if (deleteFlagFieldName != null) {
                    doDelete = ObjectUtils.get(entity, deleteFlagFieldName);
                }
                
                if (doDelete) {
                    RepositoryTransactions.getInstance().delete(clazz, id, user, cn);
                } else {
                    RepositoryTransactions.getInstance().update(clazz, entity, user, cn);
                }
            }
        }
    }

    /**
     * @return the entities
     */
    public List<T> getEntities() {
        return entities;
    }

    /**
     * @param entities the entities to set
     */
    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    /**
     * @return the user
     */
    public IUserIdentity getUser() {
        return user;
    }

}
