package com.lx.core.exception;

import javax.ws.rs.core.Response;

/**
 *
 * @author lx.ds
 */
public class NotAuthorizedException extends LxException {
     
    public NotAuthorizedException(String message) {
         super(Response.Status.UNAUTHORIZED, message);
    }
    
    public NotAuthorizedException(String pattern, Object... args) {
         super(Response.Status.UNAUTHORIZED, pattern, args);
    }
}