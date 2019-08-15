package com.lx.core.service;

import com.lx.core.repository.entity.LXUser;
import com.lx.core.utils.ObjectUtils;
import com.lx.core.response.UserResponse;
import com.lx.core.security.AuthenticatedUser;
import com.lx.core.service.generic.GenericService;
import java.util.List;

/**
 *
 * @author lx.ds
 */
public class UserService {

    // Singleton pattern
    private static UserService instance = null;

    // Singleton pattern
    private UserService() {
        // Constructor
    }

    // Singleton pattern
    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    public synchronized UserResponse get(final AuthenticatedUser loggedUser) throws Exception {
        UserResponse response = ObjectUtils.mapTo(loggedUser, new UserResponse(), false);
        LXUser query = new LXUser();
        List<LXUser> lUsers;
        
        query.setUsername(loggedUser.getUsername());
        lUsers = GenericService.getInstance().get(LXUser.class, query, null);
        
        if (lUsers.size() == 1) {
            response.setCompanyName(lUsers.get(0).getCompanyName());
        }

        return response;
    }
}
