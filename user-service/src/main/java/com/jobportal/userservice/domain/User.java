package com.jobportal.userservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;

@Entity
@Table(name="users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String fullName;
    @Column(nullable=false, unique=true)
    private String email;
    @Column(nullable=false)
    private String password;
    private String phone;
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private UserStatus status;

    @CreationTimestamp
    @Column(nullable=false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(nullable=false)
    private Instant updatedAt;
    private Instant lastLogin;
    private Instant suspendedAt;
    private Instant deletedAt;
}
