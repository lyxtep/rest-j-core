package com.lx.core.controller;

import com.lx.core.configuration.LXPermission;
import com.lx.core.infrastructure.extensions.ModuleExtensions;
import com.lx.core.repository.entity.LXDictionary;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Path;

/**
 *
 * @author lx.ds
 */
@Path("/dictionary")
@RolesAllowed(LXPermission.DICTIONARY)
public class DictionaryCtrl extends ModuleExtensions<LXDictionary> {

    public DictionaryCtrl() {
        super(LXDictionary.class);
    }

}