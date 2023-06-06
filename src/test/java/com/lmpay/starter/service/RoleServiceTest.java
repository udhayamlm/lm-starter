package com.lmpay.starter.service;

import com.lmpay.starter.model.Role;
import com.lmpay.starter.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void testGetAllRoles() {
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("ROLE_ADMIN");
    }
}

