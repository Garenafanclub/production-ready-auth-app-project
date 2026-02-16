package com.example.auth_app_practice.Services;

import com.example.auth_app_practice.DTOs.UserDto;
import com.example.auth_app_practice.Model.User;
import org.springframework.stereotype.Service;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto getUserById(String userId);
    UserDto getUserByEmail(String email);
    UserDto updateUser(UserDto userDto, String userId);
    void deleteUser(String userId);
    Iterable<UserDto> getAllUser();
}
