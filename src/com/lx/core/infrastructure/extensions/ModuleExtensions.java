package com.lx.core.infrastructure.extensions;

import com.lx.core.exception.NotAuthorizedException;
import com.lx.core.request.SearchRequest;
import com.lx.core.response.MetaFieldResponse;
import com.lx.core.security.AuthenticatedUser;
import com.lx.core.security.LxAuthenticationService;
import com.lx.core.service.generic.GenericService;
import com.lx.core.service.generic.MetadataService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.xml.ws.http.HTTPException;

/**
 *
 * @author lx.ds
 * @param <TEntity>
 */
public class ModuleExtensions<TEntity extends Object> {
    
    private @Context SecurityContext sc;
    
    private final Class<TEntity> clazz;
    
    public ModuleExtensions(Class<TEntity> clazz){
        this.clazz = clazz;
    }
    
    protected Class<TEntity> getEntityClass() {
        return clazz;
    }
    
    protected AuthenticatedUser getLoggedUser() {
        return (AuthenticatedUser)sc.getUserPrincipal();
    }
    
    protected void requiresAuthentication() {
        if (!LxAuthenticationService.getInstance().isAuthenticatedUser(getLoggedUser())) {
            throw new NotAuthorizedException("The request requires authenticated user!");
        }
    }
    
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TEntity post(TEntity entity) throws Exception {
        
        this.requiresAuthentication();
        
        return MetadataService.getInstance().parseMetaData(GenericService.getInstance().create(clazz, entity, getLoggedUser()));
        
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TEntity get(@PathParam("id") String id) throws Exception {
        return MetadataService.getInstance().parseMetaData(GenericService.getInstance().getById(clazz, id, getLoggedUser()));
    }
    
    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<TEntity> get(TEntity entity) throws Exception {
        return MetadataService.getInstance().parseMetaData(GenericService.getInstance().get(clazz, entity, getLoggedUser()));
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TEntity> get(@QueryParam("start") int start, @QueryParam("limit") int limit, @QueryParam("q") String query) throws Exception {
        return MetadataService.getInstance().parseMetaData(GenericService.getInstance().search(clazz, new SearchRequest(start, limit, query, null, null), getLoggedUser()));
    }
    
    @POST
    @Path("/tags/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<TEntity> get(List<String> tags) throws Exception {
        return MetadataService.getInstance().parseMetaData(GenericService.getInstance().search(clazz, new SearchRequest(0, 0, null, tags, null), getLoggedUser()));
    }
    
    @POST
    @Path("/search/advanced")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<TEntity> get(SearchRequest data) throws Exception {
        return MetadataService.getInstance().parseMetaData(GenericService.getInstance().search(clazz, data, getLoggedUser()));
    }
    
    /*@PUT
    @Path("/{id}/{state}")
    @Produces(MediaType.APPLICATION_JSON)
    public String put(@PathParam("id") String id, @PathParam("state") String state) {
        try {
            return GenericService.getInstance().updateState(id, state, clazz, getLoggedUser());
        } catch (Exception ex) {
            Logger.getLogger(ModuleExtensions.class.getName()).log(Level.SEVERE, null, ex);
            throw new HTTPException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }*/
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TEntity put(@PathParam("id") String id, TEntity entity) {
        try {
            return GenericService.getInstance().update(clazz, id, entity, getLoggedUser());
        } catch (Exception ex) {
            Logger.getLogger(ModuleExtensions.class.getName()).log(Level.SEVERE, null, ex);
            throw new HTTPException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") String id) throws Exception {
        GenericService.getInstance().delete(clazz, id);
    }
    
    @GET
    @Path("/meta")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MetaFieldResponse> getMetaFields() {
        return MetadataService.getInstance().GetMetaFields(clazz);
    }
}
