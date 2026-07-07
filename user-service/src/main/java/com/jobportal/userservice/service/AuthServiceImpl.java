package com.jobportal.userservice.service;

import com.jobportal.userservice.domain.User;
import com.jobportal.userservice.domain.UserRole;
import com.jobportal.userservice.domain.UserStatus;
import com.jobportal.userservice.dto.AuthResponse;
import com.jobportal.userservice.dto.LoginRequest;
import com.jobportal.userservice.dto.SignupRequest;
import com.jobportal.userservice.exception.AdminRoleNotAllowedException;
import com.jobportal.userservice.exception.EmailAlreadyExistsException;
import com.jobportal.userservice.exception.UserNotExistsException;
import com.jobportal.userservice.repository.UserRepository;
import com.jobportal.userservice.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import static com.jobportal.userservice.util.UserMapper.toDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(SignupRequest request) {

        if (Boolean.TRUE.equals(userRepository.existsByEmail(request.email())))
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

        var savedUser = userRepository.save(user);
        log.info("User signed up successfully: {}", savedUser.getId());

        var authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwt = jwtProvider.generateToken(authentication,savedUser.getId());

        return AuthResponse.builder()
                .title("Welcome to JobPortal " +  request.fullName())
                .message("Registered Successfully!")
                .jwt(jwt)
                .user(toDto(savedUser))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = userRepository.findByEmail(request.email()).orElseThrow(UserNotExistsException::new);
        var jwt = jwtProvider.generateToken(authentication, user.getId());
        user.setLastLogin(Instant.now());
        userRepository.save(user);

        return AuthResponse.builder()
                .title("Welcome back to JobPortal " +  user.getFullName())
                .message("Logged in Successfully!")
                .jwt(jwt)
                .user(toDto(user))
                .build();
    }
}
