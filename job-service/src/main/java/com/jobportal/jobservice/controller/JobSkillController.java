package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.dto.JobSkillRequest;
import com.jobportal.jobservice.dto.JobSkillResponse;
import com.jobportal.jobservice.service.JobSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/job-skills")
@RequiredArgsConstructor
public class JobSkillController {
    private final JobSkillService jobSkillService;

    @PostMapping
    public ResponseEntity<JobSkillResponse> createJobSkill(@RequestBody @Valid JobSkillRequest jobSkillRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobSkillService.createSkill(jobSkillRequest));
    }

    @GetMapping
    public ResponseEntity<Page<JobSkillResponse>> getAllSkills(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(jobSkillService.getAllSkills(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSkillResponse> getSkillById(@PathVariable Long id) {
        return ResponseEntity.ok(jobSkillService.getSkillById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobSkillResponse> updateJobSkill(@PathVariable Long id, @RequestBody @Valid JobSkillRequest jobSkillRequest) {
        return ResponseEntity.ok(jobSkillService.updateSkill(id, jobSkillRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobSkill(@PathVariable Long id) {
        jobSkillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }

}
