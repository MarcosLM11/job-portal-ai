package com.jobportal.companyservice.controller;

import com.jobportal.companyservice.domain.CompanyStatus;
import com.jobportal.companyservice.domain.CompanyType;
import com.jobportal.companyservice.domain.IndustryType;
import com.jobportal.companyservice.dto.CompanyRequest;
import com.jobportal.companyservice.dto.CompanyResponse;
import com.jobportal.companyservice.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestHeader("X-User-Id") Long ownerId, @RequestBody @Valid CompanyRequest companyRequest) {
       return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(ownerId, companyRequest));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long companyId) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(companyId));
    }

    @GetMapping("/my")
    public ResponseEntity<CompanyResponse> getMyCompany(@RequestHeader("X-User-Id") Long ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getMyCompany(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(@RequestParam(required = false) CompanyType companyType, @RequestParam(required = false) IndustryType industryType, @RequestParam(required = false) CompanyStatus companyStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompanies(companyType, industryType, companyStatus));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long id, @RequestHeader("X-User-Id") Long ownerId, @RequestBody @Valid CompanyRequest companyRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.updateCompany(id, ownerId, companyRequest));
    }

    @PatchMapping("/{id}/verify")
    public ResponseEntity<CompanyResponse> verifyCompany(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.verifyCompany(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CompanyResponse> deactivateCompany(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.deactivateCompany(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable Long id, @RequestHeader("X-User-Id") Long ownerId
    ) {
        companyService.deleteCompany(id, ownerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
