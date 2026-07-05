package com.jobportal.userservice.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String jwt,
        String title,
        String message,
        UserResponse user
) {
}
