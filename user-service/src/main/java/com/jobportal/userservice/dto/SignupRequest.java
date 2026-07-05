package com.jobportal.userservice.dto;

import com.jobportal.userservice.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank(message="full name is mandatory")
        String fullName,
        @Email(message="email should be valid")
        @NotBlank(message="email is mandatory")
        String email,
        @NotBlank(message="password is mandatory")
        String password,
        String phone,
        @NotBlank(message="role is mandatory")
        UserRole role
) {
}
