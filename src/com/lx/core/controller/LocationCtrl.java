package com.lx.core.controller;

import com.lx.core.configuration.LXPermission;
import com.lx.core.infrastructure.extensions.ModuleExtensions;
import com.lx.core.repository.entity.LXLocation;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Path;

/**
 *
 * @author lx.ds
 */
@Path("/location")
@RolesAllowed(LXPermission.LOCATION)
public class LocationCtrl extends ModuleExtensions<LXLocation> {

    public LocationCtrl() {
        super(LXLocation.class);
    }

}