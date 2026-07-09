package com.jobportal.resumeservice.dto;

public record PersonalInfoRequest(
        String firstName,
        String lastName,
        String headline,
        String email,
        String phone,
        String city,
        String country,
        String linkedinUrl,
        String githubUrl,
        String portfolioUrl,
        String websiteUrl
) {
}
