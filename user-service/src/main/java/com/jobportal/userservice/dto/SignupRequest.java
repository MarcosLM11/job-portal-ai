package com.jobportal.userservice.dto;

import com.jobportal.userservice.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message="full name is mandatory")
        String fullName,
        @Email(message="email should be valid")
        @NotBlank(message="email is mandatory")
        String email,
        @NotBlank(message="password is mandatory")
        @Size(min = 8, max = 128, message = "password must be between 8 and 128 characters")
        String password,
        String phone,
        @NotNull(message="role is mandatory")
        UserRole role
) {
}
