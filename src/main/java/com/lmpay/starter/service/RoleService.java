package com.lmpay.starter.service;

import com.lmpay.starter.exception.CustomException;
import com.lmpay.starter.model.Role;
import com.lmpay.starter.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new CustomException("Role not found"));
    }

    // Other service methods
}

