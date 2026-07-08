package com.jobportal.jobservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="jobs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String requirements;

    private String responsibilities;
    private String benefits;

    @Column(nullable = false)
    private Long companyId;

    @Column(nullable = false)
    private Long employerId;

    @Embedded
    private JobLocation location;

    @Embedded
    private SalaryRange salaryRange;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkMode workMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus jobStatus;

    private Integer openings = 1;
    private LocalDate applicationDeadline;
    private LocalDate expiresAt;
    private Boolean active = true;

    @CreationTimestamp
    @Column(nullable=false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable=false)
    private LocalDateTime updatedAt;

    private LocalDateTime publishedAt;
    private LocalDateTime closedAt;
}
