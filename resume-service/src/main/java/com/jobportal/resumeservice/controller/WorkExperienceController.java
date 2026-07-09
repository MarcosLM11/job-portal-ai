package com.jobportal.resumeservice.controller;

import com.jobportal.resumeservice.dto.AddWorkExperience;
import com.jobportal.resumeservice.dto.WorkExperienceResponse;
import com.jobportal.resumeservice.service.WorkExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resume/{resumeId}/work-experiences")
@RequiredArgsConstructor
public class WorkExperienceController {
    private final WorkExperienceService workExperienceService;

    @PostMapping
    public ResponseEntity<WorkExperienceResponse> addWorkExperience(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestParam Long resumeId,
            @RequestBody @Valid AddWorkExperience request) {
        return ResponseEntity.ok(workExperienceService.addWorkExperience(resumeId, candidateId, request));
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<WorkExperienceResponse> updateWorkExperience(
            @PathVariable Long resumeId,
            @PathVariable Long experienceId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddWorkExperience request) {
        return ResponseEntity.ok(workExperienceService.updateWorkExperience(resumeId, experienceId, candidateId, request));
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<Void> deleteWorkExperience(
            @PathVariable Long resumeId,
            @PathVariable Long experienceId,
            @RequestHeader("X-User-Id") Long candidateId) {
        workExperienceService.deleteWorkExperience(resumeId, experienceId, candidateId);
        return ResponseEntity.noContent().build();
    }

}
