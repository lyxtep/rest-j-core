package com.lx.core.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author lx.ds
 */
public class LxException extends WebApplicationException {
     
    public LxException(Response.Status status, String message) {
         super(Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build());
    }
    
    public LxException(Response.Status status, String pattern, Object... args) {
         super(Response.status(status).entity(String.format(pattern, args)).type(MediaType.TEXT_PLAIN).build());
    }
}