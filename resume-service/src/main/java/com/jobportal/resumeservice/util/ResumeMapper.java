package com.jobportal.resumeservice.util;

import com.jobportal.resumeservice.domain.PersonalInfo;
import com.jobportal.resumeservice.domain.Resume;
import com.jobportal.resumeservice.dto.PersonalInfoResponse;
import com.jobportal.resumeservice.dto.ResumeResponse;

public class ResumeMapper {

    public static PersonalInfoResponse toDto(PersonalInfo personalInfo) {
        return PersonalInfoResponse.builder()
                .firstName(personalInfo.getFirstName())
                .lastName(personalInfo.getLastName())
                .email(personalInfo.getEmail())
                .phone(personalInfo.getPhone())
                .city(personalInfo.getCity())
                .country(personalInfo.getCountry())
                .linkedinUrl(personalInfo.getLinkedinUrl())
                .githubUrl(personalInfo.getGithubUrl())
                .portfolioUrl(personalInfo.getPortfolioUrl())
                .websiteUrl(personalInfo.getWebsiteUrl())
                .build();
    }

    public static ResumeResponse toDto(Resume resume) {
        return ResumeResponse.builder()
                .id(resume.getId())
                .title(resume.getTitle())
                .candidateId(resume.getCandidateId())
                .template(resume.getTemplate())
                .visibility(resume.getVisibility())
                .personalInfo(toDto(resume.getPersonalInfo()))
                .createdAt(resume.getCreatedAt())
                .updatedAt(resume.getUpdatedAt())
                .completionScore(resume.getCompletionScore())
                .summary(resume.getSummary())
                .isDefault(resume.getIsDefault())
                .build();
    }
}
