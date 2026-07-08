package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.*;
import com.jobportal.jobservice.dto.JobRequest;
import com.jobportal.jobservice.dto.JobResponse;
import com.jobportal.jobservice.dto.JobSearchRequest;
import com.jobportal.jobservice.dto.company.CompanyResponse;
import com.jobportal.jobservice.exception.JobExpiredException;
import com.jobportal.jobservice.exception.JobNotFoundException;
import com.jobportal.jobservice.exception.WrongEmployerException;
import com.jobportal.jobservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import static com.jobportal.jobservice.util.JobMapper.toDto;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobCategoryService jobCategoryService;
    private final JobTagService jobTagService;
    private final JobSkillService  jobSkillService;

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
                .location(buildLocation(req))
                .salaryRange(buildSalaryRange(req))
                .jobType(req.jobType())
                .workMode(req.workMode())
                .experienceLevel(req.experienceLevel())
                .openings(req.openings() != null ? req.openings() : 1)
                .applicationDeadline(req.applicationDeadLine())
                .expiresAt(req.expiresAt())
                .jobStatus(JobStatus.DRAFT)
                .build();
        var savedJob = jobRepository.save(job);
        return convertToResponse(savedJob);
    }

    @Override
    @Transactional(readOnly = true)
    public JobResponse getJobById(Long id) {
        var job = jobRepository.findById(id).orElseThrow(JobNotFoundException::new);
        return convertToResponse(job);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> getJobs(JobSearchRequest request) {
        var jobs = jobRepository.findAll(JobSpecification.build(request));
        return jobs.stream().map(this::convertToResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> getJobsByCompany(Long companyId) {
        var jobs = jobRepository.findByCompanyId(companyId);
        return jobs.stream().map(this::convertToResponse).toList();
    }

    @Override
    public JobResponse updateJob(Long jobId, Long employerId, JobRequest req) {
        var job = jobRepository.findById(jobId).orElseThrow(JobNotFoundException::new);
        assertEmployer(job, employerId);

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
        job.setLocation(buildLocation(req));
        job.setSalaryRange(buildSalaryRange(req));
        job.setJobType(req.jobType());
        job.setWorkMode(req.workMode());
        job.setExperienceLevel(req.experienceLevel());
        job.setOpenings(req.openings() != null ? req.openings() : job.getOpenings());
        job.setApplicationDeadline(req.applicationDeadLine());
        job.setExpiresAt(req.expiresAt());
        return convertToResponse(jobRepository.save(job));
    }

    @Override
    public JobResponse publishJob(Long jobId, Long employerId) {
        var job = jobRepository.findById(jobId).orElseThrow(JobNotFoundException::new);
        assertEmployer(job, employerId);

        if (job.getJobStatus() == JobStatus.CLOSED || job.getJobStatus() == JobStatus.EXPIRED) throw new JobExpiredException();

        job.setJobStatus(JobStatus.OPEN);
        job.setPublishedAt(LocalDateTime.now(ZoneId.systemDefault()));
        job.setActive(true);
        return convertToResponse(jobRepository.save(job));
    }

    @Override
    public JobResponse closeJob(Long jobId, Long employerId) {
        var job = jobRepository.findById(jobId).orElseThrow(JobNotFoundException::new);
        assertEmployer(job, employerId);

        job.setJobStatus(JobStatus.CLOSED);
        job.setClosedAt(LocalDateTime.now(ZoneId.systemDefault()));
        job.setActive(false);
        return convertToResponse(jobRepository.save(job));
    }

    @Override
    public void deleteJob(Long jobId, Long employerId) {
        var job = jobRepository.findById(jobId).orElseThrow(JobNotFoundException::new);
        assertEmployer(job, employerId);
        jobRepository.delete(job);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> getAllJobsAdmin() {
        return jobRepository.findAll().stream().map(this::convertToResponse).toList();
    }

    private JobResponse convertToResponse(Job savedJob) {
        var companyResponse = CompanyResponse.builder()
                .id(savedJob.getCompanyId())
                .build();
        return toDto(savedJob, companyResponse);
    }

    private SalaryRange buildSalaryRange(JobRequest req) {
        return SalaryRange.builder()
                .minSalary(req.minSalary())
                .maxSalary(req.maxSalary())
                .build();
    }

    private JobLocation buildLocation(JobRequest req) {
        return JobLocation.builder()
                .address(req.address())
                .city(req.city())
                .state(req.state())
                .country(req.country())
                .zipCode(req.zipCode())
                .build();
    }

    private void assertEmployer(Job job, Long employerId) {
        if (!job.getEmployerId().equals(employerId)) throw new WrongEmployerException();
    }
}
