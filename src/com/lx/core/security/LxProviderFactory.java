package com.lx.core.security;

import java.util.HashMap;

/**
 *
 * @author lx.ds
 */
public class LxProviderFactory {
    
    private final HashMap<String, ITokenProvider> _cache;
    
    // Singleton pattern
    private static LxProviderFactory instance = null;

    // Singleton pattern
    public LxProviderFactory() throws Exception {
        _cache = new HashMap();
        _cache.put(LxJwtAuthProvider.AUTHENTICATION_SCHEME.toLowerCase(), new LxJwtAuthProvider());
        _cache.put(LxBasciAuthProvider.AUTHENTICATION_SCHEME.toLowerCase(), new LxBasciAuthProvider());
    }

    // Singleton pattern
    public static synchronized LxProviderFactory getInstance() throws Exception {
        if (instance == null) {
            instance = new LxProviderFactory();
        }

        return instance;
    }
    
    public synchronized ITokenProvider getProvider(String token) throws Exception {
        
        int idx = token.indexOf(" ");
        
        if (idx > 0) {
            token = token.substring(0, idx);
        }
        
        if (token.isEmpty()) {
            return null;
        }
        
        return _cache.get(token.toLowerCase());
    }
}
