package com.jobportal.companyservice.dto;

import com.jobportal.companyservice.domain.CompanySize;
import com.jobportal.companyservice.domain.CompanyStatus;
import com.jobportal.companyservice.domain.CompanyType;
import com.jobportal.companyservice.domain.IndustryType;
import lombok.Builder;
import java.time.Instant;
import java.util.List;

@Builder
public record CompanyResponse(
        Long id,
        String name,
        String slug,
        String tagline,
        String description,
        String logoUrl,
        String coverImageUrl,
        String website,
        String email,
        String phone,
        Integer foundedYear,

        CompanySize companySize,
        CompanyType companyType,
        IndustryType industryType,
        CompanyStatus companyStatus,
        Boolean verified,
        Boolean active,

        Long ownerId,

        List<SocialLinkResponse> socialLinks,

        Instant createdAt,
        Instant updatedAt,
        Instant verifiedAt

) {
}
