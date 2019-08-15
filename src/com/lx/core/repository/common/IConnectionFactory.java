package com.lx.core.repository.common;

import org.sql2o.Connection;


/**
 *
 * @author lx.ds
 */
public interface IConnectionFactory {
    
    Connection getCreateDbConnection();
    
    Connection getConnection();
    
    Connection beginTransaction();
}
