package com.jobportal.jobservice.service;

import com.jobportal.jobservice.dto.JobRequest;
import com.jobportal.jobservice.dto.JobResponse;
import com.jobportal.jobservice.dto.JobSearchRequest;
import com.jobportal.jobservice.dto.JobUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {
    JobResponse createJob(Long employerId, JobRequest req);
    JobResponse getJobById(Long id, Long requesterId);
    Page<JobResponse> getJobs(JobSearchRequest request, Pageable pageable);
    Page<JobResponse> getJobsByCompany(Long companyId, Pageable pageable);
    JobResponse updateJob(Long jobId, Long employerId, JobUpdateRequest req);
    JobResponse publishJob(Long jobId, Long employerId);
    JobResponse closeJob(Long jobId, Long employerId);
    void deleteJob(Long jobId, Long employerId);
    Page<JobResponse> getAllJobsAdmin(Pageable pageable);
}
