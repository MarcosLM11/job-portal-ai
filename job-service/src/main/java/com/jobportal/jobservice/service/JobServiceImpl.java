package com.jobportal.jobservice.service;

import com.jobportal.jobservice.client.CompanyClient;
import com.jobportal.jobservice.domain.*;
import com.jobportal.jobservice.dto.JobRequest;
import com.jobportal.jobservice.dto.JobResponse;
import com.jobportal.jobservice.dto.JobSearchRequest;
import com.jobportal.jobservice.dto.JobUpdateRequest;
import com.jobportal.jobservice.dto.company.CompanyResponse;
import com.jobportal.jobservice.exception.JobCompanyMismatchException;
import com.jobportal.jobservice.exception.JobExpiredException;
import com.jobportal.jobservice.exception.JobNotFoundException;
import com.jobportal.jobservice.exception.WrongEmployerException;
import com.jobportal.jobservice.repository.*;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import static com.jobportal.jobservice.util.JobMapper.toDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobCategoryService jobCategoryService;
    private final JobTagService jobTagService;
    private final JobSkillService  jobSkillService;
    private final CompanyClient companyClient;

    @Override
    public JobResponse createJob(Long employerId, JobRequest req) {
        var category = jobCategoryService.getCategoryEntityById(req.categoryId());
        var skills = req.skillIds() != null ? jobSkillService.getSkillsByIds(req.skillIds()) : new HashSet<JobSkill>();
        var tags = req.tagIds() != null ? jobTagService.getTagsByIds(req.tagIds()) : new HashSet<JobTag>();

        var job = Job.builder()
                .title(req.title())
                .description(req.description())
                .requirements(req.requirements())
                .responsibilities(req.responsibilities())
                .benefits(req.benefits())
                .companyId(req.companyId())
                .employerId(employerId)
                .jobCategory(category)
                .skills(skills)
                .tags(tags)
                .location(buildLocation(req.address(), req.city(), req.state(), req.country(), req.zipCode()))
                .salaryRange(buildSalaryRange(req.minSalary(), req.maxSalary()))
                .jobType(req.jobType())
                .workMode(req.workMode())
                .experienceLevel(req.experienceLevel())
                .openings(req.openings() != null ? req.openings() : 1)
                .applicationDeadline(req.applicationDeadLine())
                .expiresAt(req.expiresAt())
                .jobStatus(JobStatus.DRAFT)
                .build();
        var savedJob = jobRepository.save(job);
        log.info("Job created: jobId={}, employerId={}, companyId={}", savedJob.getId(), employerId, req.companyId());
        return convertToResponse(savedJob);
    }

    @Override
    @Transactional(readOnly = true)
    public JobResponse getJobById(Long id, Long requesterId) {
        var job = jobRepository.findById(id).orElseThrow(JobNotFoundException::new);
        if (job.getJobStatus() == JobStatus.DRAFT && (requesterId == null || !job.getEmployerId().equals(requesterId))) {
            throw new JobNotFoundException(id);
        }
        return convertToResponse(job);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobResponse> getJobs(JobSearchRequest request, Pageable pageable) {
        return jobRepository.findAll(JobSpecification.build(request), pageable).map(this::convertToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobResponse> getJobsByCompany(Long companyId, Pageable pageable) {
        return jobRepository.findByCompanyId(companyId, pageable).map(this::convertToResponse);
    }

    @Override
    public JobResponse updateJob(Long jobId, Long employerId, JobUpdateRequest req) {
        var job = jobRepository.findById(jobId).orElseThrow(JobNotFoundException::new);
        assertEmployer(job, employerId);
        if (req.companyId() != null && !req.companyId().equals(job.getCompanyId())) {
            throw new JobCompanyMismatchException();
        }

        var category = jobCategoryService.getCategoryEntityById(req.categoryId());
        var skills = req.skillIds() != null ? jobSkillService.getSkillsByIds(req.skillIds()) : new HashSet<JobSkill>();
        var tags = req.tagIds() != null ? jobTagService.getTagsByIds(req.tagIds()) : new HashSet<JobTag>();

        job.setTitle(req.title());
        job.setDescription(req.description());
        job.setRequirements(req.requirements());
        job.setResponsibilities(req.responsibilities());
        job.setBenefits(req.benefits());
        job.setJobCategory(category);
        job.setSkills(skills);
        job.setTags(tags);
        job.setLocation(buildLocation(req.address(), req.city(), req.state(), req.country(), req.zipCode()));
        job.setSalaryRange(buildSalaryRange(req.minSalary(), req.maxSalary()));
        job.setJobType(req.jobType());
        job.setWorkMode(req.workMode());
        job.setExperienceLevel(req.experienceLevel());
        job.setOpenings(req.openings() != null ? req.openings() : job.getOpenings());
        job.setApplicationDeadline(req.applicationDeadLine());
        job.setExpiresAt(req.expiresAt());
        var updatedJob = jobRepository.save(job);
        log.info("Job updated: jobId={}, employerId={}", jobId, employerId);
        return convertToResponse(updatedJob);
    }

    @Override
    public JobResponse publishJob(Long jobId, Long employerId) {
        var job = jobRepository.findById(jobId).orElseThrow(JobNotFoundException::new);
        assertEmployer(job, employerId);

        if (job.getJobStatus() == JobStatus.CLOSED || job.getJobStatus() == JobStatus.EXPIRED) throw new JobExpiredException();

        job.setJobStatus(JobStatus.OPEN);
        job.setPublishedAt(LocalDateTime.now(ZoneId.systemDefault()));
        job.setActive(true);
        var publishedJob = jobRepository.save(job);
        log.info("Job published: jobId={}, employerId={}", jobId, employerId);
        return convertToResponse(publishedJob);
    }

    @Override
    public JobResponse closeJob(Long jobId, Long employerId) {
        var job = jobRepository.findById(jobId).orElseThrow(JobNotFoundException::new);
        assertEmployer(job, employerId);

        job.setJobStatus(JobStatus.CLOSED);
        job.setClosedAt(LocalDateTime.now(ZoneId.systemDefault()));
        job.setActive(false);
        var closedJob = jobRepository.save(job);
        log.info("Job closed: jobId={}, employerId={}", jobId, employerId);
        return convertToResponse(closedJob);
    }

    @Override
    public void deleteJob(Long jobId, Long employerId) {
        var job = jobRepository.findById(jobId).orElseThrow(JobNotFoundException::new);
        assertEmployer(job, employerId);
        jobRepository.delete(job);
        log.info("Job deleted: jobId={}, employerId={}", jobId, employerId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobResponse> getAllJobsAdmin(Pageable pageable) {
        return jobRepository.findAll(pageable).map(this::convertToResponse);
    }

    private JobResponse convertToResponse(Job savedJob) {
        var companyResponse = fetchCompany(savedJob.getCompanyId());
        return toDto(savedJob, companyResponse);
    }

    private CompanyResponse fetchCompany(Long companyId) {
        try {
            return companyClient.getCompanyById(companyId);
        } catch (FeignException ex) {
            log.warn("Failed to fetch company {} from company-service, falling back to id-only response: {}", companyId, ex.getMessage());
            return CompanyResponse.builder().id(companyId).build();
        }
    }

    private SalaryRange buildSalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        return SalaryRange.builder()
                .minSalary(minSalary)
                .maxSalary(maxSalary)
                .build();
    }

    private JobLocation buildLocation(String address, String city, String state, String country, String zipCode) {
        return JobLocation.builder()
                .address(address)
                .city(city)
                .state(state)
                .country(country)
                .zipCode(zipCode)
                .build();
    }

    private void assertEmployer(Job job, Long employerId) {
        if (!job.getEmployerId().equals(employerId)) throw new WrongEmployerException();
    }
}
