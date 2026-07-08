package com.jobportal.companyservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="companies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;
    private String slug;
    private String tagline;
    private String description;
    private String logoUrl;
    private String coverImageUrl;
    private String website;
    private String email;
    private String phone;
    private Integer foundedYear;

    @Enumerated(EnumType.STRING)
    private CompanySize companySize;
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;
    @Enumerated(EnumType.STRING)
    private IndustryType industryType;
    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;

    @Builder.Default
    private Boolean isVerified = false;

    @Column(unique=true)
    private String registrationNumber;
    @Column(nullable = false, unique=true)
    private Long ownerId;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<SocialLink> socialLinks = new ArrayList<>();

    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    @Column(nullable=false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(nullable=false)
    private Instant updatedAt;
}
