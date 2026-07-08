package com.jobportal.jobservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="job_tags")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(unique = true)
    private String slug;
    @Builder.Default
    private Boolean active = true;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
