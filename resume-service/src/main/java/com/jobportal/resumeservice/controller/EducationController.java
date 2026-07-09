package com.jobportal.resumeservice.controller;

import com.jobportal.resumeservice.dto.EducationRequest;
import com.jobportal.resumeservice.dto.EducationResponse;
import com.jobportal.resumeservice.service.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/resumes/{resumeId}/educations")
@RequiredArgsConstructor
public class EducationController {
    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<EducationResponse> addEducation(
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long resumeId,
            @RequestBody @Valid EducationRequest educationRequest) {
        return ResponseEntity.ok(educationService.addEducation(resumeId, candidateId, educationRequest));
    }

    @GetMapping
    public ResponseEntity<List<EducationResponse>> getEducations(@PathVariable Long resumeId) {
        return ResponseEntity.ok(educationService.getEducations(resumeId));
    }

    @PutMapping("/{educationId}")
    public ResponseEntity<EducationResponse> updateEducation(
            @PathVariable Long resumeId,
            @PathVariable Long educationId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid EducationRequest educationRequest
    ) {
        return ResponseEntity.ok(educationService.updateEducation(resumeId, educationId, candidateId, educationRequest));
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<Void> deleteEducation(
            @PathVariable Long resumeId,
            @PathVariable Long educationId,
            @RequestHeader("X-User-Id") Long candidateId
    ) {
        educationService.deleteEducation(educationId, resumeId, candidateId);
        return ResponseEntity.noContent().build();
    }
}
