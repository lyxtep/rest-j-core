
package com.lx.core.repository.generic;

import org.sql2o.Connection;

/**
 *
 * @author lx.ds
 */
public interface ITransaction {
    
    void execute(Connection cn) throws Exception;
}
