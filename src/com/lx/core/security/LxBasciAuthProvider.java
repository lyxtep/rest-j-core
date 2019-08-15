package com.lx.core.security;

import com.lx.core.service.AccountService;
import com.sun.jersey.core.util.Base64;
import java.util.StringTokenizer;

/**
 *
 * @author dejan.stjepanovic
 */
public class LxBasciAuthProvider implements ITokenProvider {
    
    public static final String AUTHENTICATION_SCHEME = "Basic";
    
    private String prefix;
    
    @Override
    public String getPrefix() {
        if (prefix == null) {
            prefix = AUTHENTICATION_SCHEME + " ";
        }
        return prefix;
    }
    
    @Override
    public IUserIdentity extractToken(String token) throws Exception {
        AuthenticatedUser theUserIdentity;
        String usernameAndPassword;
        final StringTokenizer tokenizer;
        final String username;
        final String password;
        
        token = token.replaceFirst(getPrefix(), "");
        
        //Decode username and password
        usernameAndPassword = new String(Base64.decode(token.getBytes()));
        
        tokenizer = new StringTokenizer(usernameAndPassword, ":");
        username = tokenizer.nextToken();
        password = tokenizer.nextToken();
        
        theUserIdentity = AccountService.getInstance().signin(username, password);
        
        return theUserIdentity;
    }

    @Override
    public String generateToken(IUserIdentity userIdentity, String locale) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
