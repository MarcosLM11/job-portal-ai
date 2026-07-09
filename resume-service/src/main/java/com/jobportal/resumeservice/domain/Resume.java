package com.jobportal.resumeservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long candidateId;
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ResumeTemplate template = ResumeTemplate.PROFESSIONAL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ResumeVisibility visibility = ResumeVisibility.PUBLIC;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @Embedded
    private PersonalInfo personalInfo;

    private String summary;
    private Boolean isActive;

    @Builder.Default
    private Integer completionScore = 0;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
