package com.lx.core.controller;

import com.lx.core.request.SigninRequest;
import com.lx.core.security.AuthenticatedUser;
import com.lx.core.security.ITokenProvider;
import com.lx.core.security.LxJwtAuthProvider;
import com.lx.core.security.LxProviderFactory;
import com.lx.core.service.AccountService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author lx.ds
 */
@Path("/account")
public class AccountCtrl {

    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    public String signin(SigninRequest credentials) throws Exception {
        String output = "";
        
        //try {
            output = LxProviderFactory.getInstance().getProvider(LxJwtAuthProvider.AUTHENTICATION_SCHEME).generateToken(
                    AccountService.getInstance().signin(credentials.getUsername(), credentials.getPassword()), credentials.getLocale());
         
        /*} catch (Exception ex) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build());
        }*/
        
        return output;

    }
}
