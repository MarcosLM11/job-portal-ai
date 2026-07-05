package com.jobportal.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message="email should be valid")
        @NotBlank(message="email is mandatory")
        String email,
        @NotBlank(message="password is mandatory")
        String password
) {
}
