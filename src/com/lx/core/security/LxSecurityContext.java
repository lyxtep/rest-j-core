package com.lx.core.security;

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author lx.ds
 */
public class LxSecurityContext implements SecurityContext {

    private final AuthenticatedUser user;
    private final String scheme;
    
    public LxSecurityContext(AuthenticatedUser user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }
    
    @Override
    public Principal getUserPrincipal() { return this.user; }

    @Override
    public boolean isUserInRole(String role) {
        if (user != null && user.getClaims() != null) {
            return user.getClaims().contains(role);
        }
        return false;
    }

    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme) || "http".equals(this.scheme);
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";//SecurityContext.BASIC_AUTH;
    }

}
