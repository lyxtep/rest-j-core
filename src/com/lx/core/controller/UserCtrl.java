package com.lx.core.controller;

import com.lx.core.infrastructure.extensions.ModuleExtensions;
import com.lx.core.repository.entity.LXUser;
import com.lx.core.security.AuthenticatedUser;
import com.lx.core.service.UserService;
import com.lx.core.utils.Roles;
import com.lx.core.response.UserResponse;
import com.lx.core.service.AccountService;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author lx.ds
 */
@Path("/user")
@RolesAllowed(Roles.SYSTEM_ADMIN)
public class UserCtrl extends ModuleExtensions<LXUser> {
    
    public UserCtrl() {
        super(LXUser.class);
    }

    @GET
    @Path("/me")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse getUser() {
        
        this.requiresAuthentication();
        
        UserResponse response = null;
        AuthenticatedUser theLoggedUser = getLoggedUser();
        
        try {
            response = UserService.getInstance().get(theLoggedUser);
        }  catch (Exception ex) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build());
        }
        
        return response;
    }
}
