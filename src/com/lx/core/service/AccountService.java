package com.lx.core.service;

import com.lx.core.configuration.LXSecurityClass;
import com.lx.core.repository.entity.LXEmployee;
import com.lx.core.repository.entity.LXGroupPermission;
import com.lx.core.repository.entity.LXUser;
import com.lx.core.security.AuthenticatedUser;
import com.lx.core.service.generic.GenericService;
import com.lx.core.utils.StringUtils;
import com.lx.db.LxDbUserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lx.ds
 */
public class AccountService {
    
    // Singleton pattern
    private static AccountService instance = null;

    // Singleton pattern
    private AccountService() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }

        return instance;
    }

    public synchronized AuthenticatedUser signin(final String username, String password) throws Exception {
        LXUser queryUser = new LXUser();
        queryUser.setUsername(username);
        List<LXUser> users;
        List<LXGroupPermission> permissions;
        Map<String, String> userPermissions;
        LXGroupPermission queryPermission;
        LXUser theUser;

        try {
            users = GenericService.getInstance().get(LXUser.class, queryUser, null);
        } catch (Exception ex) {
            users = new ArrayList<LXUser>();
        }
        
        if (users.isEmpty()) {
            
            if ("sa".equals(username)) {
                
                theUser = LxDbUserService.getInstance().getSysAdminUser();
                
            } else {
                throw new Exception("User does not exist.");
            }
            
        } else {
            theUser = users.get(0);
        }        

        if (!theUser.getPassword().equals(password)) {
            throw new Exception("Invalid password.");
        }

        userPermissions = new HashMap<String, String>();
        
        if (!StringUtils.isNullOrEmpty(theUser.getGroupId())) {
            queryPermission = new LXGroupPermission();
            queryPermission.setGroupId(theUser.getGroupId());
            queryPermission.setType(PermissionService.PERMISSION_TYPE_DATA);
            permissions = GenericService.getInstance().get(LXGroupPermission.class, queryPermission, null);

            for (LXGroupPermission aGroupPermission : permissions)
            {
                userPermissions.put(aGroupPermission.getKeyword(), aGroupPermission.getPermission());
            }
        } else if (username.equals("sa")) {
            
            for (String aPermission : PermissionService.getInstance().getPermissions())
            {
                userPermissions.put(aPermission, LXSecurityClass.PERMISSION_FULL);
            }
        }

        AuthenticatedUser theLoggedUser = new AuthenticatedUser(theUser.getUsername(), theUser.getGroupId(), userPermissions);
        
        theLoggedUser.setDisplayName(theUser.getDisplayName());
        theLoggedUser.setCompanyId(theUser.getCompanyId());
        
        // department
        if (StringUtils.isNullOrEmpty(theUser.getDepartmentId()) && !StringUtils.isNullOrEmpty(theUser.getEmployeeId()))
        {
            LXEmployee employee = GenericService.getInstance().getById(LXEmployee.class, theUser.getEmployeeId(), null);
            if (employee != null)
            {
                theLoggedUser.setDepartmentId(employee.getDepartmentId());
            }
        }
        else
        {
            theLoggedUser.setDepartmentId(theUser.getDepartmentId());
        }

        return theLoggedUser;
    }
}
