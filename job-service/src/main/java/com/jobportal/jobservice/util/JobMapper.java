package com.jobportal.jobservice.util;

import com.jobportal.jobservice.domain.Job;
import com.jobportal.jobservice.domain.JobSkill;
import com.jobportal.jobservice.dto.JobResponse;
import com.jobportal.jobservice.dto.JobSkillResponse;
import com.jobportal.jobservice.dto.JobTagResponse;
import com.jobportal.jobservice.dto.company.CompanyResponse;

import java.util.HashSet;
import java.util.stream.Collectors;

public class JobMapper {

    public static JobResponse toDto(Job job, CompanyResponse company) {
        var location = job.getLocation();
        var salaryRange = job.getSalaryRange();
        var skills = job.getSkills() != null ? job.getSkills().stream().map(JobSkillMapper::toDto).collect(Collectors.toSet()) : new HashSet<JobSkillResponse>();
        var tags = job.getTags() != null ? job.getTags().stream().map(JobTagMapper::toDto).collect(Collectors.toSet()) : new HashSet<JobTagResponse>();
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .responsibilities(job.getResponsibilities())
                .benefits(job.getBenefits())
                .company(company)
                .category(JobCategoryMapper.toDto(job.getJobCategory(),false))
                .skills(skills)
                .tags(tags)
                .address(location != null ? location.getAddress() : null)
                .city(location != null ? location.getCity() : null)
                .state(location != null ? location.getState() : null)
                .country(location != null ? location.getCountry() : null)
                .zipCode(location != null ? location.getZipCode() : null)
                .minSalary(salaryRange != null ? salaryRange.getMinSalary() : null)
                .maxSalary(salaryRange != null ? salaryRange.getMaxSalary() : null)
                .jobType(job.getJobType())
                .workMode(job.getWorkMode())
                .experienceLevel(job.getExperienceLevel())
                .jobStatus(job.getJobStatus())
                .openings(job.getOpenings())
                .applicationDeadLine(job.getApplicationDeadline())
                .expiresAt(job.getExpiresAt())
                .active(job.getActive())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .publishedAt(job.getPublishedAt())
                .closedAt(job.getClosedAt())
                .build();
    }
}
