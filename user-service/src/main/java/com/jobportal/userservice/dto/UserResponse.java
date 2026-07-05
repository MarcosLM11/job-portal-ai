package com.jobportal.userservice.dto;

import com.jobportal.userservice.domain.UserRole;
import com.jobportal.userservice.domain.UserStatus;
import lombok.Builder;
import java.time.Instant;

@Builder
public record UserResponse(
        Long id,
        String fullName,
        String email,
        String password,
        String phone,
        String profileImage,
        UserRole role,
        UserStatus status,
        Boolean verified,
        Instant lastLogin,
        Instant createdAt
) {
}
