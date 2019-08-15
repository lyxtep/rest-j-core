package com.lx.core.exception;

import javax.ws.rs.core.Response;

/**
 *
 * @author lx.ds
 */
public class BadRequestException extends LxException {
     
    public BadRequestException(String message) {
         super(Response.Status.BAD_REQUEST, message);
    }
    
    public BadRequestException(String pattern, Object... args) {
         super(Response.Status.BAD_REQUEST, pattern, args);
    }
}