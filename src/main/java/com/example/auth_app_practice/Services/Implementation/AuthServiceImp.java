package com.example.auth_app_practice.Services.Implementation;

import com.example.auth_app_practice.DTOs.LoginDto;
import com.example.auth_app_practice.DTOs.UserDto;
import com.example.auth_app_practice.Mapper.UserMapper;
import com.example.auth_app_practice.Model.User;
import com.example.auth_app_practice.Repository.UserRepo;
import com.example.auth_app_practice.Services.AuthService;
import com.example.auth_app_practice.Services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager; // FOR LOGIN THE BOSS.. // YOU HAVE HOLD NOW AS BEAN IN SECURITY CONFIG..
    private final JwtService jwtService;

    @Override
    public UserDto register(UserDto userDto) {

        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User savedUser = userRepo.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public String login(LoginDto loginDto) {
        // 2. The Login Check
        // This line attempts to log the user in.
        // If password is WRONG -> It throws an Exception automatically.
        // If password is RIGHT -> It continues to the next line.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        User user = userRepo.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtService.generateToken(user);
    }
}