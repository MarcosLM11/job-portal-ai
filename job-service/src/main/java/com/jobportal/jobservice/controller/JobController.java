package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.dto.JobRequest;
import com.jobportal.jobservice.dto.JobResponse;
import com.jobportal.jobservice.dto.JobSearchRequest;
import com.jobportal.jobservice.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
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
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getJobs(@ModelAttribute JobSearchRequest request) {
        return ResponseEntity.ok(jobService.getJobs(request));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobResponse>> getJobsByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(jobService.getJobsByCompany(companyId));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<JobResponse>> getAllJobsAdmin() {
        return ResponseEntity.ok(jobService.getAllJobsAdmin());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long id, @RequestHeader("X-User-Id") Long employerId, @RequestBody @Valid JobRequest jobRequest) {
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
