package com.jobportal.userservice.dto;

public record UpdateUserRequest(
        String fullName,
        String phone,
        String profileImage
) {
}
