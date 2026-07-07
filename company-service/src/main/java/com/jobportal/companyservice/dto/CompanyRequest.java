package com.jobportal.companyservice.dto;

import com.jobportal.companyservice.domain.CompanySize;
import com.jobportal.companyservice.domain.CompanyStatus;
import com.jobportal.companyservice.domain.CompanyType;
import com.jobportal.companyservice.domain.IndustryType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import java.util.List;

@Builder
public record CompanyRequest(
        @NotBlank(message="Company name is required")
        String name,
        String tagline,
        String description,
        String logoUrl,
        String coverImageUrl,
        @Pattern(regexp="^(https?://).*",message="Website must be a valid URL")
        String website,
        @Email(message="Company email must be valid")
        String email,
        String phone,
        @Min(value=1800,message="Founded year seems too old")
        @Max(value=2100, message="Founded year is invalid")
        Integer foundedYear,

        @NotNull(message="Company size is required")
        CompanySize companySize,
        @NotNull(message="Company type is required")
        CompanyType companyType,
        @NotNull(message="Industry type is required")
        IndustryType industryType,
        @NotNull(message="Company status is required")
        CompanyStatus companyStatus,

        String registrationNumber,
        List<SocialLinkResponse> socialLinks
) {
}
