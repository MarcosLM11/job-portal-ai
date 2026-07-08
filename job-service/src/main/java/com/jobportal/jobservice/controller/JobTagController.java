package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.dto.JobTagRequest;
import com.jobportal.jobservice.dto.JobTagResponse;
import com.jobportal.jobservice.service.JobTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/job-tags")
@RequiredArgsConstructor
public class JobTagController {
    private final JobTagService jobTagService;

    @PostMapping
    public ResponseEntity<JobTagResponse> createJobTag(@RequestBody @Valid JobTagRequest jobTagRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobTagService.createTag(jobTagRequest));
    }

    @GetMapping
    public ResponseEntity<List<JobTagResponse>> getAllJobTags() {
        return ResponseEntity.ok(jobTagService.getAllTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobTagResponse> getJobTag(@PathVariable long id) {
        return ResponseEntity.ok(jobTagService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobTagResponse> updateJobTag(@PathVariable long id, @RequestBody @Valid JobTagRequest jobTagRequest) {
        return ResponseEntity.ok(jobTagService.updateTag(id, jobTagRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobTag(@PathVariable long id) {
        jobTagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
