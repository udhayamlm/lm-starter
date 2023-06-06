package com.lmpay.starter.service;

import com.lmpay.starter.exception.CustomException;
import com.lmpay.starter.model.LoginRequest;
import com.lmpay.starter.model.User;
import com.lmpay.starter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findByUsername(username);
        if (user == null || !password.equals(user.getPassword())) {
            throw new CustomException("Invalid username or password");
        }

        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));
    }

    public User createUser(User user) {
        // Perform any necessary validations or business logic
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));

        // Update the fields of the existing user object with the new values
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setRoles(user.getRoles());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));

        userRepository.delete(user);
    }

}

