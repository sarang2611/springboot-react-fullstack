package com.authservice.services;

import org.springframework.stereotype.Service;

import com.authservice.enums.ERole;
import com.authservice.modals.Role;

@Service
public interface RoleService {
    Role findByName(ERole eRole);
}