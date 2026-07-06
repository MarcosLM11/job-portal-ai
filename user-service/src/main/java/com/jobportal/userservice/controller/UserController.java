package com.jobportal.userservice.controller;

import com.jobportal.userservice.dto.UpdateUserRequest;
import com.jobportal.userservice.dto.UserResponse;
import com.jobportal.userservice.service.UserService;
import com.jobportal.userservice.util.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static com.jobportal.userservice.util.UserMapper.toDto;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserProfile(@RequestHeader("X-User-Email") String email) {
        var user = userService.getUserByEmail(email);
        return ResponseEntity.ok(toDto(user));
    }

    @PostMapping("/me")
    public ResponseEntity<UserResponse> updateUserProfile(@RequestHeader("X-User-Email") String email, @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUserProfile(email, request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        var user = userService.getUserById(userId);
        return ResponseEntity.ok(toDto(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users.stream().map(UserMapper::toDto).collect(Collectors.toList()));
    }

    @PatchMapping("/{userId}/suspend")
    public ResponseEntity<UserResponse> suspendUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.suspendUser(userId));
    }

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<UserResponse> activateUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.activateUser(userId));
    }

    @PatchMapping("/{userId}/delete")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
