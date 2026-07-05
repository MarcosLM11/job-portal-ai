package com.jobportal.userservice.service;

import com.jobportal.userservice.domain.User;
import com.jobportal.userservice.domain.UserRole;
import com.jobportal.userservice.domain.UserStatus;
import com.jobportal.userservice.dto.AuthResponse;
import com.jobportal.userservice.dto.LoginRequest;
import com.jobportal.userservice.dto.SignupRequest;
import com.jobportal.userservice.exception.AdminRoleNotAllowedException;
import com.jobportal.userservice.exception.EmailAlreadyExistsException;
import com.jobportal.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import static com.jobportal.userservice.util.UserMapper.toDto;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signup(SignupRequest request) {

        if (repository.existsByEmail(request.email()))
            throw new EmailAlreadyExistsException(request.email());

        if (UserRole.ROLE_ADMIN.equals(request.role()))
            throw new AdminRoleNotAllowedException();

        var user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .phone(request.phone())
                .status(UserStatus.ACTIVE)
                .lastLogin(Instant.now())
                .build();

        var savedUser = repository.save(user);

        return AuthResponse.builder()
                .title("Welcome to JobPortal " +  request.fullName())
                .message("Registered Successfully!")
                .jwt("JWT")
                .user(toDto(savedUser))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
