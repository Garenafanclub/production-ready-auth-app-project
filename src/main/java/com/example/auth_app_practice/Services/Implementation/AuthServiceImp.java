package com.example.auth_app_practice.Services.Implementation;

import com.example.auth_app_practice.DTOs.UserDto;
import com.example.auth_app_practice.Mapper.UserMapper;
import com.example.auth_app_practice.Model.User;
import com.example.auth_app_practice.Repository.UserRepo;
import com.example.auth_app_practice.Services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    // private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(UserDto userDto) {

         if(userRepo.existsByEmail(userDto.getEmail()))
         {
             throw new RuntimeException("Email already exists");
         }

         User user = userMapper.toEntity(userDto);
         //user.setPassword(passwordEncoder.encode(userDto.getPassword()));

         User savedUser = userRepo.save(user);
         return userMapper.toDto(savedUser);
    }
}
