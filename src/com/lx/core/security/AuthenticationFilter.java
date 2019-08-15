package com.lx.core.security;

import com.lx.core.utils.Priorities;
import com.lx.core.utils.StringUtils;
import com.sun.jersey.core.util.Priority;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author lx.ds
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
            .entity("You cannot access this resource").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
            .entity("Access blocked for all users !!").build();

    @Override
    public ContainerRequest filter(ContainerRequest request) {
        AuthenticatedUser user = null;
        ITokenProvider tokenProvider;

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String authorizationHeader = request.getHeaderValue(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly 
        if (!StringUtils.isNullOrEmpty(authorizationHeader) && !"null".equalsIgnoreCase(authorizationHeader)) {
            
            try {
                tokenProvider = LxProviderFactory.getInstance().getProvider(authorizationHeader);
                if (tokenProvider != null) {
                    user = (AuthenticatedUser)tokenProvider.extractToken(authorizationHeader);
                }
            } catch (Exception ex) {
                
            }
            
        }

        // We configure your Security Context here
        String scheme = request.getBaseUri().getScheme();
        request.setSecurityContext(new LxSecurityContext(user, scheme));

        return request;
    }

}
