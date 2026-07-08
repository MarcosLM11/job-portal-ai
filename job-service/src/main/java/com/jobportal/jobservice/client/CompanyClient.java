package com.jobportal.jobservice.client;

import com.jobportal.jobservice.dto.company.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service", url = "${company-service.base-url}")
public interface CompanyClient {

    @GetMapping("/api/companies/{companyId}")
    CompanyResponse getCompanyById(@PathVariable("companyId") Long companyId);
}
