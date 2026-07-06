package com.jobportal.userservice.service;

import com.jobportal.userservice.domain.User;
import com.jobportal.userservice.dto.UpdateUserRequest;
import com.jobportal.userservice.dto.UserResponse;
import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(long id);
    List<User> getAllUsers();
    UserResponse updateUserProfile(String email, UpdateUserRequest request);

    //admin
    UserResponse suspendUser(Long id);
    UserResponse activateUser(Long id);
    UserResponse deleteUser(Long id);
}
