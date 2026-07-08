package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.dto.JobRequest;
import com.jobportal.jobservice.dto.JobResponse;
import com.jobportal.jobservice.dto.JobSearchRequest;
import com.jobportal.jobservice.dto.JobUpdateRequest;
import com.jobportal.jobservice.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid JobRequest jobRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(employerId, jobRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id, @RequestHeader(value = "X-User-Id", required = false) Long requesterId) {
        return ResponseEntity.ok(jobService.getJobById(id, requesterId));
    }

    @GetMapping
    public ResponseEntity<Page<JobResponse>> getJobs(@ModelAttribute JobSearchRequest request, @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(jobService.getJobs(request, pageable));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Page<JobResponse>> getJobsByCompany(@PathVariable Long companyId, @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(jobService.getJobsByCompany(companyId, pageable));
    }

    @GetMapping("/admin")
    public ResponseEntity<Page<JobResponse>> getAllJobsAdmin(@PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(jobService.getAllJobsAdmin(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long id, @RequestHeader("X-User-Id") Long employerId, @RequestBody @Valid JobUpdateRequest jobRequest) {
        return ResponseEntity.ok(jobService.updateJob(id, employerId, jobRequest));
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<JobResponse> publishJob(@PathVariable Long id, @RequestHeader("X-User-Id") Long employerId) {
        return ResponseEntity.ok(jobService.publishJob(id, employerId));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<JobResponse> closeJob(@PathVariable Long id,  @RequestHeader("X-User-Id") Long employerId) {
        return ResponseEntity.ok(jobService.closeJob(id, employerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id,  @RequestHeader("X-User-Id") Long employerId) {
        jobService.deleteJob(id, employerId);
        return ResponseEntity.noContent().build();
    }
}
