package com.jobportal.companyservice.service;

import com.jobportal.companyservice.domain.Company;
import com.jobportal.companyservice.domain.CompanyStatus;
import com.jobportal.companyservice.domain.CompanyType;
import com.jobportal.companyservice.domain.IndustryType;
import com.jobportal.companyservice.dto.CompanyRequest;
import com.jobportal.companyservice.dto.CompanyResponse;
import java.util.List;

public interface CompanyService {
    CompanyResponse createCompany(Long ownerId, CompanyRequest request);
    CompanyResponse getCompanyById(Long companyId);
    CompanyResponse getMyCompany(Long ownerId);
    List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus);
    CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest request);
    CompanyResponse verifyCompany(Long companyId);
    void deleteCompany(Long companyId,  Long ownerId);
    CompanyResponse deactivateCompany(Long companyId);

    Company getCompanyEntityById(Long companyId);
}
