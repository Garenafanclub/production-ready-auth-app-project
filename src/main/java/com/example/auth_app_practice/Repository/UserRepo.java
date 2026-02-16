package com.example.auth_app_practice.Repository;

import com.example.auth_app_practice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

    // 1. For Login: Find the user to check password
    // SQL: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);

    // 2. For Sign Up: Check if email is already taken
    // SQL: SELECT COUNT(*) > 0 FROM users WHERE email = ?
    boolean existsByEmail(String email);

    // 3. For Security: Find user ONLY if they are enabled (verified)
    // SQL: SELECT * FROM users WHERE email = ? AND enabled = true
    Optional<User> findByEmailAndEnabledTrue(String email);
}
