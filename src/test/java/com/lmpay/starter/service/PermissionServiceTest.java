package com.lmpay.starter.service;

import com.lmpay.starter.model.Permission;
import com.lmpay.starter.repository.PermissionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PermissionServiceTest {

    @InjectMocks
    private PermissionService permissionService;

    @Mock
    private PermissionRepository permissionRepository;

    @Test
    public void testGetAllPermissions() {
        Permission permission1 = new Permission();
        permission1.setId(1L);
        permission1.setName("READ");

        Permission permission2 = new Permission();
        permission2.setId(2L);
        permission2.setName("WRITE");

        List<Permission> permissions = Arrays.asList(permission1, permission2);

        Mockito.when(permissionRepository.findAll()).thenReturn(permissions);

        List<Permission> result = permissionService.getAllPermissions();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("READ", result.get(0).getName());
        Assertions.assertEquals("WRITE", result.get(1).getName());
    }

    // Other test cases
}

