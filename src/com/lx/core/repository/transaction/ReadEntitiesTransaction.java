package com.lx.core.repository.transaction;

import com.lx.core.repository.generic.ITransaction;
import com.lx.core.repository.generic.RepositoryTransactions;
import com.lx.core.request.SearchRequest;
import com.lx.core.security.IUserIdentity;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;

/**
 *
 * @author lx.ds
 * @param <T>
 */
public class ReadEntitiesTransaction<T extends Object> implements ITransaction {

    private final Class<T> clazz;
    private final SearchRequest params;
    private final IUserIdentity user;
    
    private List<T> entities;
    
    public ReadEntitiesTransaction(Class<T> clazz, SearchRequest params, IUserIdentity user) {
        this.clazz = clazz;
        this.params = params;
        this.user = user;
    }

    @Override
    public void execute(Connection cn) throws Exception {        
        this.entities = RepositoryTransactions.getInstance().read(clazz, params, true, user, cn);
        
        if (this.entities == null) this.entities = new ArrayList<>();
    }

    /**
     * @return the entities
     */
    public List<T> getEntities() {
        return entities;
    }

    /**
     * @return the params
     */
    public SearchRequest getParams() {
        return params;
    }

    /**
     * @return the user
     */
    public IUserIdentity getUser() {
        return user;
    }
}
