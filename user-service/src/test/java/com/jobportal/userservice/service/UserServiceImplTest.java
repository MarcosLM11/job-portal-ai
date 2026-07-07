package com.jobportal.userservice.service;

import com.jobportal.userservice.domain.User;
import com.jobportal.userservice.domain.UserRole;
import com.jobportal.userservice.domain.UserStatus;
import com.jobportal.userservice.exception.UserNotExistsException;
import com.jobportal.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserByEmail_found_returnsUser() {
        var user = activeUser();
        when(userRepository.findByEmail("alice@test.com")).thenReturn(Optional.of(user));

        var result = userService.getUserByEmail("alice@test.com");

        assertThat(result.getEmail()).isEqualTo("alice@test.com");
    }

    @Test
    void getUserByEmail_notFound_throwsUserNotExistsException() {
        when(userRepository.findByEmail("ghost@test.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByEmail("ghost@test.com")).isInstanceOf(UserNotExistsException.class);
    }

    @Test
    void getUserById_found_returnsUser() {
        var user = activeUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        var result = userService.getUserById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getUserById_notFound_throwsUserNotExistsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(99L)).isInstanceOf(UserNotExistsException.class);
    }

    @Test
    void suspendUser_setsStatusSuspendedAndSuspendedAt() {
        var user = activeUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        var response = userService.suspendUser(1L);

        assertThat(response.status()).isEqualTo(UserStatus.SUSPENDED);
        assertThat(user.getSuspendedAt()).isNotNull();
    }

    @Test
    void suspendUser_notFound_throwsUserNotExistsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.suspendUser(99L)).isInstanceOf(UserNotExistsException.class);
    }

    @Test
    void activateUser_setsStatusActiveAndClearsSuspendedAt() {
        var user = User.builder()
                .id(1L)
                .fullName("Bob")
                .email("bob@test.com")
                .password("pass")
                .role(UserRole.ROLE_JOB_SEEKER)
                .status(UserStatus.SUSPENDED)
                .suspendedAt(Instant.ofEpochMilli(System.currentTimeMillis()))
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        var response = userService.activateUser(1L);

        assertThat(response.status()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getSuspendedAt()).isNull();
    }

    @Test
    void deleteUser_softDeletes_setsStatusDeletedAndDeletedAt() {
        var user = activeUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        var response = userService.deleteUser(1L);

        assertThat(response.status()).isEqualTo(UserStatus.DELETED);
        assertThat(user.getDeletedAt()).isNotNull();
        verify(userRepository, never()).delete(any(User.class));
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteUser_notFound_throwsUserNotExistsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteUser(99L)).isInstanceOf(UserNotExistsException.class);
    }

    private User activeUser() {
        return User.builder()
                .id(1L)
                .fullName("Alice")
                .email("alice@test.com")
                .password("pass")
                .role(UserRole.ROLE_JOB_SEEKER)
                .status(UserStatus.ACTIVE)
                .build();
    }
}
