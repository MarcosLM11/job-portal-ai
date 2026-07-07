package com.jobportal.companyservice.util;

import com.jobportal.companyservice.domain.Company;
import com.jobportal.companyservice.domain.SocialLink;
import com.jobportal.companyservice.dto.CompanyResponse;
import com.jobportal.companyservice.dto.SocialLinkResponse;
import java.util.ArrayList;

public class CompanyMapper {

    public static SocialLinkResponse toDto(SocialLink socialLink) {
        return SocialLinkResponse.builder().socialPlatform(socialLink.getSocialPlatform()).url(socialLink.getUrl()).build();
    }

    public static CompanyResponse toDto(Company company) {
        var socialLinkResponse =company.getSocialLinks() == null ? new ArrayList<SocialLinkResponse>() : company.getSocialLinks().stream()
                .map(CompanyMapper::toDto)
                .toList();

        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .tagline(company.getTagline())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsite())
                .email(company.getEmail())
                .phone(company.getPhone())
                .foundedYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .industryType(company.getIndustryType())
                .companyStatus(company.getCompanyStatus())
                .ownerId(company.getOwnerId())
                .socialLinks(socialLinkResponse)
                .active(company.getActive())
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }
}
