package com.jobportal.companyservice.repository;

import com.jobportal.companyservice.domain.Company;
import com.jobportal.companyservice.domain.CompanyStatus;
import com.jobportal.companyservice.domain.CompanyType;
import com.jobportal.companyservice.domain.IndustryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
    Optional<Company> findByOwnerId(Long ownerId);
    Boolean existsByOwnerId(Long ownerId);
    Boolean existsByName(String name);
    Boolean existsBySlug(String slug);
    Boolean existsByRegistrationNumber(String registrationNumber);

    @Query(
            "select c from Company c where" +
            "(:companyType IS NULL OR c.companyType=:companyType) AND " +
            "(:industryType IS NULL OR c.industryType=:industryType) AND " +
            "(:companyStatus IS NULL OR c.companyStatus=:companyStatus)"
    )
    List<Company> findByFilters(
            @Param("companyType")CompanyType companyType,
            @Param("industryType") IndustryType industryType,
            @Param("companyStatus") CompanyStatus companyStatus
    );
}
