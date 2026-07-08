package com.jobportal.jobservice.service;

import com.jobportal.jobservice.dto.JobRequest;
import com.jobportal.jobservice.dto.JobResponse;
import com.jobportal.jobservice.dto.JobSearchRequest;
import java.util.List;

public interface JobService {
    JobResponse createJob(Long employerId, JobRequest req);
    JobResponse getJobById(Long id);
    List<JobResponse> getJobs(JobSearchRequest request);
    List<JobResponse> getJobsByCompany(Long companyId);
    JobResponse updateJob(Long jobId, Long employerId, JobRequest req);
    JobResponse publishJob(Long jobId, Long employerId);
    JobResponse closeJob(Long jobId, Long employerId);
    void deleteJob(Long jobId, Long employerId);
    List<JobResponse> getAllJobsAdmin();
}
