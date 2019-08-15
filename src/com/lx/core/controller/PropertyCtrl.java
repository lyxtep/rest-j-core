package com.lx.core.controller;

import com.lx.core.configuration.LXConfig;
import com.lx.core.infrastructure.extensions.ModuleExtensions;
import com.lx.core.repository.entity.LXProperty;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author lx.ds
 */
@Path("/property")
public class PropertyCtrl extends ModuleExtensions<LXProperty> {

    public PropertyCtrl() {
        super(LXProperty.class); 
    }

    @GET
    @Path("/value/{key}")
    public String getValue(@PathParam("key") String key) throws Exception {
        return LXConfig.getInstance().getString(key);
    }
    @GET
    @Path("/refresh")
    public void refresh() throws Exception {
        LXConfig.getInstance().refresh();
    }
}
