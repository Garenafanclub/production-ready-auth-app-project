package com.example.auth_app_practice.Services;

import com.example.auth_app_practice.DTOs.UserDto;
import org.apache.catalina.UserDatabase;

public interface AuthService {
    UserDto register(UserDto userDto);
}
