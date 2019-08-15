package com.lx.core.infrastructure.extensions;

import com.lx.core.repository.generic.EntityClassWrapper;
import com.lx.core.service.EntryPropertyService;
import com.lx.core.utils.LxCacheUtils;
import com.lx.core.utils.ObjectUtils;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author lx.ds
 * @param <TEntity>
 * @param <TSettings>
 */
public class ModuleWithSettingsExtensions<TEntity extends Object, TSettings extends Object> extends ModuleExtensions<TEntity> {
    
    private final Class<TSettings> clazzSettings;
    
    public ModuleWithSettingsExtensions(Class<TEntity> clazz, Class<TSettings> clazzSettings){
        super(clazz);
        this.clazzSettings = clazzSettings;
    }
    
    @GET
    @Path("/settings/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TSettings getSettings(@PathParam("id") String id) throws Exception {
        TSettings settings = EntryPropertyService.getInstance().get(clazzSettings, id, getLoggedUser());
        
        EntityClassWrapper classWrapper = LxCacheUtils.getInstance().getEtityClassWrapper(getEntityClass());
        if (settings != null && classWrapper.getPrimaryKeyColumn() != null) {
            ObjectUtils.set(settings, classWrapper.getPrimaryKeyColumn().getFieldName(), id);
        }
        
        return settings;
    }
    
    @PUT
    @Path("/settings/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TSettings putSettings(@PathParam("id") String id, TSettings entity) throws Exception {
        if (entity == null) return entity;
        
        TSettings settings = EntryPropertyService.getInstance().update(clazzSettings, id, entity, getLoggedUser());
        
        EntityClassWrapper classWrapper = LxCacheUtils.getInstance().getEtityClassWrapper(getEntityClass());
        if (settings != null && classWrapper.getPrimaryKeyColumn() != null) {
            ObjectUtils.set(settings, classWrapper.getPrimaryKeyColumn().getFieldName(), id);
        }
        
        return settings;
    }

}
