package com.lmpay.starter.service;

import com.lmpay.starter.model.User;
import com.lmpay.starter.repository.UserRepository;
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
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        List<User> users = Arrays.asList(user1, user2);

        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("user1", result.get(0).getUsername());
        Assertions.assertEquals("user2", result.get(1).getUsername());
    }

    // Other test cases
}

