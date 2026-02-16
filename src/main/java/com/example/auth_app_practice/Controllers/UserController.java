package com.example.auth_app_practice.Controllers;

import com.example.auth_app_practice.DTOs.UserDto;
import com.example.auth_app_practice.Services.Implementation.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImp userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createdUSER = userService.createUser(userDto);
        return new ResponseEntity<>(createdUSER, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId)
    {
        UserDto getUSER = userService.getUserById(userId);
        return ResponseEntity.ok(getUSER);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email)
    {
        UserDto getUSERByEmail = userService.getUserByEmail(email);
        return ResponseEntity.ok(getUSERByEmail);
    }

    @GetMapping
    public ResponseEntity<Iterable<UserDto>> getAllUsers()
    {
        Iterable<UserDto> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId)
    {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @Valid @RequestBody UserDto userDto,
            @PathVariable String userId
    ){
        UserDto updateUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/session")
    public String getSession(HttpServletRequest request) {

        HttpSession session = request.getSession();

        return "Session ID: " + session.getId();
    }
}
