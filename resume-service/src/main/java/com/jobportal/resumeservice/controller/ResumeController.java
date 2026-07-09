package com.jobportal.resumeservice.controller;

import com.jobportal.resumeservice.dto.PersonalInfoRequest;
import com.jobportal.resumeservice.dto.ResumeRequest;
import com.jobportal.resumeservice.dto.ResumeResponse;
import com.jobportal.resumeservice.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ResumeResponse> createResume(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid ResumeRequest resumeRequest) {
        return ResponseEntity.ok(resumeService.createResume(candidateId, resumeRequest));
    }

    @GetMapping("/{resumeId}")
    public ResponseEntity<ResumeResponse> getResumeById(
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable long resumeId) {
        return ResponseEntity.ok(resumeService.getResumeById(resumeId, candidateId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<ResumeResponse>> getMyResumes(@RequestHeader("X-User-Id") Long candidateId) {
        return ResponseEntity.ok(resumeService.getMyResumes(candidateId));
    }

    @PutMapping("/{resumeId}/personal-info")
    public ResponseEntity<ResumeResponse> updatePersonalInfo(
            @PathVariable long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid PersonalInfoRequest personalInfoRequest) {
        return ResponseEntity.ok(resumeService.updatePersonalInfo(resumeId, candidateId, personalInfoRequest));
    }

    @PatchMapping("/{resumeId}/summary")
    public ResponseEntity<ResumeResponse> updateSummary(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestParam String summary) {
        return ResponseEntity.ok(resumeService.updateSummary(resumeId, candidateId, summary));
    }

    @PatchMapping("/{resumeId}/set-default")
    public ResponseEntity<ResumeResponse> setDefaultResume(@RequestHeader("X-User-Id") Long candidateId, @PathVariable Long resumeId) {
        return ResponseEntity.ok(resumeService.setDefaultResume(resumeId, candidateId));
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResume(@RequestHeader("X-User-Id") Long candidateId, @PathVariable Long resumeId) {
        resumeService.deleteResume(resumeId, candidateId);
        return ResponseEntity.noContent().build();
    }
}
