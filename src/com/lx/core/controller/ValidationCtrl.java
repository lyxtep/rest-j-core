package com.lx.core.controller;

import com.lx.core.response.ValidationResponse;
import com.lx.core.service.ValidationService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

/**
 *
 * @author lx.ds
 */
@Path("/validation")
public class ValidationCtrl {

    @GET
    @Path("/unique/{entity}/{name}/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ValidationResponse> search(
            @PathParam("entity") String entity,
            @PathParam("name") String name,
            @PathParam("value") String value
    ) throws Exception {
        
        try {
            return ValidationService.getInstance().checkUnique(entity, name, value);
        } catch (Exception ex) {
            Logger.getLogger(ValidationCtrl.class.getName()).log(Level.SEVERE, null, ex);
            throw new HTTPException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }
}
