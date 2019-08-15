package com.lx.core.repository.transaction;

import com.lx.core.repository.entity.LXEntryProperty;
import com.lx.core.repository.generic.EntityClassWrapper;
import com.lx.core.repository.generic.RepositoryTransactions;
import com.lx.core.request.SearchRequest;
import com.lx.core.security.IUserIdentity;
import com.lx.core.service.EntryPropertyService;
import com.lx.core.utils.LxCacheUtils;
import com.lx.core.utils.ObjectUtils;
import java.util.List;
import org.sql2o.Connection;

/**
 *
 * @author lx.ds
 * @param <T>
 */
public class SaveSettingsTransaction<T extends Object> extends SaveEntitiesTransaction<LXEntryProperty> {
    
    private final Class<T> clazz;
    private final String id;
    private T settings;
    

    public SaveSettingsTransaction(Class<T> clazz, String id, T settings, IUserIdentity user) {
        super(LXEntryProperty.class, null, user);
        
        this.clazz = clazz;
        this.id = id;
        this.settings = settings;
    }
    
    @Override
    public void execute(Connection cn) throws Exception {
        LXEntryProperty query = new LXEntryProperty();
        List<LXEntryProperty> entitiesExisted;
        
        if (settings != null) {
            setEntities(EntryPropertyService.getInstance().mapSettingsFromObject(settings));
        }
        
        if (getEntities().isEmpty()) return;
        
        query.setEntryId(getId());
        entitiesExisted = RepositoryTransactions.getInstance().read(LXEntryProperty.class, new SearchRequest(0, 0, null, null, query), false, getUser(), cn);
        
        for (LXEntryProperty aProperty : getEntities()) {
            aProperty.setEntryId(getId());
            
            for (LXEntryProperty anExistedProperty : entitiesExisted) {
                if (aProperty.getKeyword().equals(anExistedProperty.getKeyword())) {
                    aProperty.setId(anExistedProperty.getId());
                    break;
                }
            }
        }
        
        super.execute(cn);
        
        settings = EntryPropertyService.getInstance().mapSettingsToObject(clazz, getEntities());
        
        EntityClassWrapper classWrapper = LxCacheUtils.getInstance().getEtityClassWrapper(clazz);
        if (classWrapper.getPrimaryKeyColumn() != null) {
            ObjectUtils.set(settings, classWrapper.getPrimaryKeyColumn().getFieldName(), id);
        }
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the settings
     */
    public T getSettings() {
        return settings;
    }
}
