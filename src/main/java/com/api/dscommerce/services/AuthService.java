package com.api.dscommerce.services;

import com.api.dscommerce.entities.User;
import com.api.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validateSelOrAdmin(Long userId){
        User me = userService.authenticate();
        if(!me.hasRole("ROLE_ADMIN") && !me.getId().equals(userId)){
            throw new ForbiddenException("Access denied");
        }
    }
}
