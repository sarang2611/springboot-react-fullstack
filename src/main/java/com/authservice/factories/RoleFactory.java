package com.authservice.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.authservice.enums.ERole;
import com.authservice.exceptions.RoleNotFoundException;
import com.authservice.modals.Role;
import com.authservice.services.RoleService;

@Component
public class RoleFactory {
    @Autowired
    RoleService roleService;

    public Role getInstance(String role) throws RoleNotFoundException {
        String normalizedRole = role.trim().toUpperCase();

        switch (normalizedRole) {
            case "USER":
            case "ROLE_USER":
                return roleService.findByName(ERole.ROLE_USER);
            case "ADMIN":
            case "ROLE_ADMIN":
                return roleService.findByName(ERole.ROLE_ADMIN);
            default:
                throw new RoleNotFoundException("Invalid role name: " + role);
        }
    }


}
