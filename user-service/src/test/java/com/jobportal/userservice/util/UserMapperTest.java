package com.jobportal.userservice.util;

import com.jobportal.userservice.domain.User;
import com.jobportal.userservice.domain.UserRole;
import com.jobportal.userservice.domain.UserStatus;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void toDto_mapsAllFieldsCorrectly() {
        var now = Instant.ofEpochMilli(System.currentTimeMillis());
        var user = User.builder()
                .id(42L)
                .fullName("Jane Smith")
                .email("jane@test.com")
                .password("secret")
                .phone("555-0001")
                .profileImage("http://img.example.com/jane.jpg")
                .role(UserRole.ROLE_EMPLOYER)
                .status(UserStatus.ACTIVE)
                .lastLogin(now)
                .createdAt(now)
                .build();

        var dto = UserMapper.toDto(user);

        assertThat(dto.id()).isEqualTo(42L);
        assertThat(dto.fullName()).isEqualTo("Jane Smith");
        assertThat(dto.email()).isEqualTo("jane@test.com");
        assertThat(dto.phone()).isEqualTo("555-0001");
        assertThat(dto.profileImage()).isEqualTo("http://img.example.com/jane.jpg");
        assertThat(dto.role()).isEqualTo(UserRole.ROLE_EMPLOYER);
        assertThat(dto.status()).isEqualTo(UserStatus.ACTIVE);
        assertThat(dto.lastLogin()).isEqualTo(now);
        assertThat(dto.createdAt()).isEqualTo(now);
    }

    @Test
    void toDto_nullOptionalFields_stayNull() {
        var user = User.builder()
                .id(1L)
                .fullName("Min User")
                .email("min@test.com")
                .password("pass")
                .role(UserRole.ROLE_JOB_SEEKER)
                .status(UserStatus.ACTIVE)
                .build();

        var dto = UserMapper.toDto(user);

        assertThat(dto.phone()).isNull();
        assertThat(dto.profileImage()).isNull();
    }
}
