package com.lx.core.controller;

import com.lx.core.configuration.LXConfig;
import com.lx.core.repository.entity.LXCompany;
import com.lx.core.repository.entity.LXDepartment;
import com.lx.core.repository.entity.LXDictionary;
import com.lx.core.repository.entity.LXElement;
import com.lx.core.repository.entity.LXEmployee;
import com.lx.core.repository.entity.LXEntryProperty;
import com.lx.core.repository.entity.LXFile;
import com.lx.core.repository.entity.LXGroup;
import com.lx.core.repository.entity.LXGroupPermission;
import com.lx.core.repository.entity.LXLocation;
import com.lx.core.repository.entity.LXModule;
import com.lx.core.repository.entity.LXProperty;
import com.lx.core.repository.entity.LXUser;
import com.lx.core.security.AuthenticatedUser;
import com.lx.db.LxDbLocationService;
import com.lx.db.LxDbService;
import com.lx.db.LxDbUserService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author lx.ds
 */
@Path("/admin")
public class AdminCtrl {
    
    private @Context SecurityContext sc;
    
    protected AuthenticatedUser getLoggedUser() {
        return (AuthenticatedUser)sc.getUserPrincipal();
    }
    
    @GET
    @Path("/install")
    public String install() throws Exception {
        
        LXConfig.getInstance().install(null, false);
        //element();
        //property();
        //user();
        //file();
        
        return "INSTALL SUCCESSFUL";
    }
    
    @POST
    @Path("/db/install")
    public String installDb() {
        String output = "";
        
        // Order is important!
        
        // DB
        output += LxDbService.getInstance().createDatabase(getLoggedUser());
        
        // LXElement
        output += LxDbService.getInstance().createTable(LXElement.class, getLoggedUser());
        
        // LXModule
        output += LxDbService.getInstance().createTable(LXModule.class, getLoggedUser());
        
        // LXProperty
        output += LxDbService.getInstance().createTable(LXProperty.class, getLoggedUser());
        
        // LXEntryProperty
        output += LxDbService.getInstance().createTable(LXEntryProperty.class, getLoggedUser());
        
        // LXDictionary
        output += LxDbService.getInstance().createTable(LXDictionary.class, getLoggedUser());
        
        // LXLocation
        output += LxDbService.getInstance().createTable(LXLocation.class, getLoggedUser());
        
        // LXGroup
        output += LxDbService.getInstance().createTable(LXGroup.class, getLoggedUser());
        
        // LXGroupPermission
        output += LxDbService.getInstance().createTable(LXGroupPermission.class, getLoggedUser());
        
        
        // LXCompany
        output += LxDbService.getInstance().createTable(LXCompany.class, getLoggedUser());
        
        // LXDepartment
        output += LxDbService.getInstance().createTable(LXDepartment.class, getLoggedUser());
        
        // LXEmployee
        output += LxDbService.getInstance().createTable(LXEmployee.class, getLoggedUser());
        
        
        // LXUser
        output += LxDbService.getInstance().createTable(LXUser.class, getLoggedUser());
        
        // LXFile
        output += LxDbService.getInstance().createTable(LXFile.class, getLoggedUser());
        
        
        // ENTRIES
        output += LxDbUserService.getInstance().insertEntries(getLoggedUser());
        output += LxDbLocationService.getInstance().insertEntries(getLoggedUser());
        
        return output;

    }
}
