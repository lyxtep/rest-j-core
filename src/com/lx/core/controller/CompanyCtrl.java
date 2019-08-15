package com.lx.core.controller;

import com.lx.core.configuration.LXPermission;
import com.lx.core.infrastructure.extensions.ModuleExtensions;
import com.lx.core.repository.entity.LXCompany;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Path;

/**
 *
 * @author lx.ds
 */
@Path("/company")
@RolesAllowed(LXPermission.COMPANY)
public class CompanyCtrl extends ModuleExtensions<LXCompany> {

    public CompanyCtrl() {
        super(LXCompany.class);
    }

}
