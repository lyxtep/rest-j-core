package com.lx.core.controller;

import com.lx.core.configuration.LXPermission;
import com.lx.core.infrastructure.extensions.ModuleExtensions;
import com.lx.core.repository.entity.LXModule;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Path;

/**
 *
 * @author lx.ds
 */
@Path("/module")
@RolesAllowed(LXPermission.MODULE)
public class ModuleCtrl extends ModuleExtensions<LXModule> {

    public ModuleCtrl() {
        super(LXModule.class);
    }

}
