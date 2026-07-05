package com.jobportal.userservice.util;

import com.jobportal.userservice.domain.User;
import com.jobportal.userservice.dto.UserResponse;

public class UserMapper {

    public static UserResponse toDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .profileImage(user.getProfileImage())
                .role(user.getRole())
                .status(user.getStatus())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
