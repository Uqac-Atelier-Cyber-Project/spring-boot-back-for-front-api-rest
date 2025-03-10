package com.uqac.back_for_front.services;

import com.uqac.back_for_front.controllers.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String register(UserDto userDto) {
        // Logic for registering a user
        return "User registered successfully (à implémenter)";
    }

    public String login(UserDto userDto) {
        // Logic for user login
        return "User logged in successfully (à implémenter)";
    }

    public String getUserData(Long userId) {
        // Logic for getting user data
        return "User data (à implémenter)";
    }

    public String updateUser(UserDto userDto) {
        // Logic for updating user data
        return "User updated successfully (à implémenter)";
    }
}