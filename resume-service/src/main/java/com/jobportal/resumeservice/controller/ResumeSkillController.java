package com.jobportal.resumeservice.controller;

import com.jobportal.resumeservice.dto.ResumeSkillRequest;
import com.jobportal.resumeservice.dto.ResumeSkillResponse;
import com.jobportal.resumeservice.service.ResumeSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resumes/{resumeId}/skills")
@RequiredArgsConstructor
public class ResumeSkillController {
    private final ResumeSkillService resumeSkillService;

    @PostMapping
    public ResponseEntity<ResumeSkillResponse> addSkill(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid ResumeSkillRequest resumeSkillRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resumeSkillService.addSkill(resumeId, candidateId, resumeSkillRequest));
    }

    @GetMapping
    public ResponseEntity<List<ResumeSkillResponse>> getSkills(@PathVariable Long resumeId) {
        return ResponseEntity.ok(resumeSkillService.getSkills(resumeId));
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<ResumeSkillResponse> updateSkill(
            @PathVariable Long resumeId,
            @PathVariable Long skillId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid ResumeSkillRequest resumeSkillRequest) {
        return ResponseEntity.ok(resumeSkillService.updateSkill(resumeId, skillId, candidateId, resumeSkillRequest));
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(
            @PathVariable Long resumeId,
            @PathVariable Long skillId,
            @RequestHeader("X-User-Id") Long candidateId) {
        resumeSkillService.deleteSkill(resumeId, skillId, candidateId);
        return ResponseEntity.noContent().build();
    }
}
