package com.jobportal.userservice.service;

import com.jobportal.userservice.domain.User;
import com.jobportal.userservice.domain.UserRole;
import com.jobportal.userservice.domain.UserStatus;
import com.jobportal.userservice.dto.LoginRequest;
import com.jobportal.userservice.dto.SignupRequest;
import com.jobportal.userservice.exception.AdminRoleNotAllowedException;
import com.jobportal.userservice.exception.EmailAlreadyExistsException;
import com.jobportal.userservice.exception.UserNotExistsException;
import com.jobportal.userservice.repository.UserRepository;
import com.jobportal.userservice.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.Instant;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication mockAuthentication;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void signup_success_returnsAuthResponseWithJwt() {
        var request = new SignupRequest("John Doe", "john@test.com", "password123", "555-0100", UserRole.ROLE_JOB_SEEKER);
        var savedUser = User.builder()
                .id(1L)
                .fullName("John Doe")
                .email("john@test.com")
                .password("encoded-password")
                .phone("555-0100")
                .role(UserRole.ROLE_JOB_SEEKER)
                .status(UserStatus.ACTIVE)
                .lastLogin(Instant.ofEpochMilli(System.currentTimeMillis()))
                .build();
        when(userRepository.existsByEmail("john@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtProvider.generateToken(any(Authentication.class), anyLong())).thenReturn("test-jwt");

        var response = authService.signup(request);

        assertThat(response.jwt()).isEqualTo("test-jwt");
        assertThat(response.message()).isEqualTo("Registered Successfully!");
        assertThat(response.user().email()).isEqualTo("john@test.com");
    }

    @Test
    void signup_duplicateEmail_throwsEmailAlreadyExistsException() {
        var request = new SignupRequest("Jane", "dup@test.com", "pass", "111", UserRole.ROLE_JOB_SEEKER);
        when(userRepository.existsByEmail("dup@test.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("dup@test.com");
    }

    @Test
    void signup_adminRole_throwsAdminRoleNotAllowedException() {
        var request = new SignupRequest("Admin", "admin@test.com", "pass", "000", UserRole.ROLE_ADMIN);
        when(userRepository.existsByEmail("admin@test.com")).thenReturn(false);
        assertThatThrownBy(() -> authService.signup(request)).isInstanceOf(AdminRoleNotAllowedException.class);
    }

    @Test
    void login_success_returnsAuthResponseWithJwt() {
        var request = new LoginRequest("john@test.com", "password123");
        var user = User.builder()
                .id(1L)
                .fullName("John Doe")
                .email("john@test.com")
                .password("encoded-password")
                .role(UserRole.ROLE_JOB_SEEKER)
                .status(UserStatus.ACTIVE)
                .build();
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        when(userRepository.findByEmail("john@test.com")).thenReturn(Optional.of(user));
        when(jwtProvider.generateToken(any(Authentication.class), anyLong())).thenReturn("test-jwt");
        when(userRepository.save(any(User.class))).thenReturn(user);

        var response = authService.login(request);

        assertThat(response.jwt()).isEqualTo("test-jwt");
        assertThat(response.message()).isEqualTo("Logged in Successfully!");
        assertThat(response.user().email()).isEqualTo("john@test.com");
    }

    @Test
    void login_unknownEmail_throwsUserNotExistsException() {
        var request = new LoginRequest("ghost@test.com", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        when(userRepository.findByEmail("ghost@test.com")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authService.login(request)).isInstanceOf(UserNotExistsException.class);
    }
}
