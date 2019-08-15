package com.lx.core.security;

/**
 *
 * @author lx.ds
 */
public class LxAuthenticationService {

    // Singleton pattern
    private static LxAuthenticationService instance = null;

    // Singleton pattern
    private LxAuthenticationService() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized LxAuthenticationService getInstance() {
        if (instance == null) {
            instance = new LxAuthenticationService();
        }

        return instance;
    }
    
    public synchronized boolean isAuthenticatedUser(AuthenticatedUser user) {
        
        return (user != null);
    }
}
