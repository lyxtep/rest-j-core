package com.lx.core.service;

import com.lx.core.repository.common.DbConnectionFactory;
import com.lx.core.repository.sql.SelectQuery;
import com.lx.core.response.ValidationResponse;
import com.lx.core.utils.ClassUtils;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Query;

/**
 *
 * @author lx.ds
 */
public class ValidationService {

    // Singleton pattern
    private static ValidationService instance = null;

    // Singleton pattern
    private ValidationService() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized ValidationService getInstance() {
        if (instance == null) {
            instance = new ValidationService();
        }

        return instance;
    }

    public synchronized List<ValidationResponse> checkUnique(String entity, String name, String value) throws Exception {
        List<ValidationResponse> results;
        Query query;
        SelectQuery sql = new SelectQuery();
        // SELECT
        sql.addSelectColumn(name);
        // FROM
        sql.addFromTable(entity);
        // WHERE
        sql.addAndCriterion(String.format("%s.%s LIKE :value", entity, name));

        try (Connection cn = DbConnectionFactory.getInstance().getConnection()) {

            query = cn.createQuery(sql.toString());
            query.addParameter("value", value);
            
            results = query.throwOnMappingFailure(false).executeAndFetch(ValidationResponse.class);
        }
        
        return results;
    }
}
