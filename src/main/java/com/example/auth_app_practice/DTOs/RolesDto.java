package com.example.auth_app_practice.DTOs;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolesDto {

    private UUID id;

    @NotBlank(message = "Role name is required")
    @Pattern(regexp = "^[A-Z]+$", message = "Role name must be uppercase letters only (e.g., USER, ADMIN)")
    private String name;
}
