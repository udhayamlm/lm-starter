package com.lmpay.starter.service;

import com.lmpay.starter.exception.CustomException;
import com.lmpay.starter.model.Permission;
import com.lmpay.starter.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Permission getPermissionById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new CustomException("Permission not found"));
    }

    // Other service methods
}

