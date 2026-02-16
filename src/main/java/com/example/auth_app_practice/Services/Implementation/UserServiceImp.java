package com.example.auth_app_practice.Services.Implementation;

import com.example.auth_app_practice.DTOs.UserDto;
import com.example.auth_app_practice.Mapper.UserMapper;
import com.example.auth_app_practice.Model.User;
import com.example.auth_app_practice.Repository.UserRepo;
import com.example.auth_app_practice.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
       if(userRepo.existsByEmail(userDto.getEmail()))
       {
           throw new RuntimeException("Email already exist...");
       }

       User user = userMapper.toEntity(userDto);
       User savedUser = userRepo.save(user);
       return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto getUserById(String userId) {
        UUID id = UUID.fromString(userId);
        User user = userRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        UUID id = UUID.fromString(userId);
        User existUser = userRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found with id: " + id));

        // 2. CHECK: If they are changing Email, is the NEW email free?
        if (userDto.getEmail() != null && !userDto.getEmail().equals(existUser.getEmail())) {
            // They are trying to change email. check if taken.
            if (userRepo.existsByEmail(userDto.getEmail())) {
                throw new RuntimeException("This email is already in use by another account.");
            }
        }
        userMapper.updateUserFromDto(userDto, existUser);
        User savedUser = userRepo.save(existUser);
        return userMapper.toDto(savedUser);
    }

    @Override
    public void deleteUser(String userId) {
        UUID id = UUID.fromString(userId);
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("USER NOT FOUND");
        } else {
            userRepo.deleteById(id);
        }
    }

    @Override
    public Iterable<UserDto> getAllUser() {
        // first fetch all users from db...
       List<User> allUserFromDb = userRepo.findAll();

       List<UserDto> allUserToDto = new ArrayList<>();
       for(User user : allUserFromDb)
       {
           UserDto userDto = userMapper.toDto(user);
           allUserToDto.add(userDto);
       }
       return allUserToDto;
    }
}
