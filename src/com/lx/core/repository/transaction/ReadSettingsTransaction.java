package com.lx.core.repository.transaction;

import com.lx.core.repository.entity.LXEntryProperty;
import com.lx.core.repository.generic.EntityClassWrapper;
import com.lx.core.request.SearchRequest;
import com.lx.core.security.IUserIdentity;
import com.lx.core.service.EntryPropertyService;
import com.lx.core.utils.LxCacheUtils;
import com.lx.core.utils.ObjectUtils;
import org.sql2o.Connection;

/**
 *
 * @author lx.ds
 * @param <T>
 */
public class ReadSettingsTransaction<T extends Object> extends ReadEntitiesTransaction<LXEntryProperty> {

    private final Class<T> clazz;
    private final String id;
    private T settings;
    

    public ReadSettingsTransaction(Class<T> clazz, String id, IUserIdentity user) {
        super(LXEntryProperty.class, new SearchRequest(0, 0, null, null, new LXEntryProperty()), user);
        
        this.clazz = clazz;
        this.id = id;
    }
    
    @Override
    public void execute(Connection cn) throws Exception {
        
        ((LXEntryProperty)getParams().getEntity()).setEntryId(id);
        
        super.execute(cn);
        
        settings = EntryPropertyService.getInstance().mapSettingsToObject(clazz, getEntities());
        
        EntityClassWrapper classWrapper = LxCacheUtils.getInstance().getEtityClassWrapper(clazz);
        if (settings != null && classWrapper.getPrimaryKeyColumn() != null) {
            ObjectUtils.set(settings, classWrapper.getPrimaryKeyColumn().getFieldName(), id);
        }
    }

    /**
     * @return the settings
     */
    public T getSettings() {
        return settings;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

}
