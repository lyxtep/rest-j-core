package com.lx.core.repository.generic;

import java.util.HashMap;

/**
 *
 * @author lx.ds
 */
public class RepositoryFactory {

    // Singleton pattern
    private static RepositoryFactory instance = null;
    
    private final HashMap _cache;

    // Singleton pattern
    private RepositoryFactory() {
        // Constructor
        _cache = new HashMap();
    }

    // Singleton pattern
    public static synchronized RepositoryFactory getInstance() {
        if (instance == null) {
            instance = new RepositoryFactory();
        }

        return instance;
    }
    
    public synchronized <TRepository extends Object> TRepository getRepository(Class<TRepository> clazz) throws Exception
    {
        TRepository repository = (TRepository)_cache.get(clazz.getCanonicalName() + "-repository");
        if (repository == null)
        {
            try {
                repository = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new Exception(ex);
            }
            _cache.put(clazz.getCanonicalName() + "-repository", repository);
        }
        return repository;
    }
}
