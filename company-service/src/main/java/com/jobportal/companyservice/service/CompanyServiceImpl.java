package com.jobportal.companyservice.service;

import com.jobportal.companyservice.domain.*;
import com.jobportal.companyservice.dto.CompanyRequest;
import com.jobportal.companyservice.dto.CompanyResponse;
import com.jobportal.companyservice.dto.SocialLinkResponse;
import com.jobportal.companyservice.exception.*;
import com.jobportal.companyservice.repository.CompanyRepository;
import com.jobportal.companyservice.util.CompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static com.jobportal.companyservice.util.CompanyMapper.toDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public CompanyResponse createCompany(Long ownerId, CompanyRequest request) {

        if(Boolean.TRUE.equals(companyRepository.existsByName(request.name()))) throw new CompanyNameAlreadyExistsException("A company with that name already exists, please choose another name.");
        if(Boolean.TRUE.equals(companyRepository.existsByOwnerId(ownerId))) throw new AlreadyHaveCompanyException("You already own a company, only one company per owner is allowed.");
        if(request.registrationNumber() != null && Boolean.TRUE.equals(companyRepository.existsByRegistrationNumber(request.registrationNumber()))) throw new RegistrationNumberAlreadyExistsException("A company with that registration number already exists, please choose a different registration number.");

        var slug = generateUniqueSlug(request.name());

        var company = Company.builder()
                .name(request.name())
                .slug(slug)
                .tagline(request.tagline())
                .description(request.description())
                .logoUrl(request.logoUrl())
                .coverImageUrl(request.coverImageUrl())
                .website(request.website())
                .email(request.email())
                .phone(request.phone())
                .foundedYear(request.foundedYear())
                .companySize(request.companySize())
                .companyType(request.companyType())
                .industryType(request.industryType())
                .companyStatus(CompanyStatus.PENDING_VERIFICATION)
                .registrationNumber(request.registrationNumber())
                .ownerId(ownerId)
                .socialLinks(mapSocialLinks(request.socialLinks()))
                .build();

        var savedCompany = companyRepository.save(company);
        log.info("Company created: companyId={}, ownerId={}, name={}", savedCompany.getId(), ownerId, savedCompany.getName());

        return toDto(savedCompany);
    }

    @Override
    public CompanyResponse getCompanyById(Long companyId) {
        var company = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company with that id does not exist"));
        return toDto(company);
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) {
        var company = companyRepository.findByOwnerId(ownerId).orElseThrow(() -> new CompanyNotFoundException("You do not own a company yet."));
        return toDto(company);
    }

    @Override
    public List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus) {
        return companyRepository.findByFilters(companyType, industryType, companyStatus).stream()
                .map(CompanyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest request) {
        var company = getCompanyEntityById(companyId);

        if (!company.getName().equals(request.name()) && Boolean.TRUE.equals(companyRepository.existsByName(request.name()))) throw new CompanyNameAlreadyExistsException("A company with that name already exists, please choose another name.");
        if (request.registrationNumber() != null && !request.registrationNumber().equals(company.getRegistrationNumber()) && Boolean.TRUE.equals(companyRepository.existsByRegistrationNumber(request.registrationNumber())))
            throw new RegistrationNumberAlreadyExistsException("A company with that registration number already exists, please choose a different registration number.");

        company.setName(request.name());
        company.setTagline(request.tagline());
        company.setDescription(request.description());
        company.setLogoUrl(request.logoUrl());
        company.setCoverImageUrl(request.coverImageUrl());
        company.setWebsite(request.website());
        company.setEmail(request.email());
        company.setPhone(request.phone());
        company.setFoundedYear(request.foundedYear());
        company.setCompanySize(request.companySize());
        company.setCompanyType(request.companyType());
        company.setIndustryType(request.industryType());
        company.setRegistrationNumber(request.registrationNumber());
        company.setSocialLinks(mapSocialLinks(request.socialLinks()));

        var updatedCompany = companyRepository.save(company);
        log.info("Company updated: companyId={}, ownerId={}", companyId, ownerId);
        return toDto(updatedCompany);
    }

    @Override
    @Transactional
    public CompanyResponse verifyCompany(Long companyId) {
        var company = getCompanyEntityById(companyId);
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        company.setIsVerified(true);
        company.setActive(true);
        var verifiedCompany = companyRepository.save(company);
        log.info("Company verified: companyId={}", companyId);
        return toDto(verifiedCompany);
    }

    @Override
    @Transactional
    public void deleteCompany(Long companyId, Long ownerId) {
        var company = getCompanyEntityById(companyId);
        assertOwner(company.getOwnerId(),ownerId);
        companyRepository.delete(company);
        log.info("Company deleted: companyId={}, ownerId={}", companyId, ownerId);
    }

    @Override
    @Transactional
    public CompanyResponse deactivateCompany(Long companyId) {
        var company = getCompanyEntityById(companyId);
        company.setCompanyStatus(CompanyStatus.SUSPENDED);
        company.setIsVerified(false);
        company.setActive(false);
        var deactivatedCompany = companyRepository.save(company);
        log.info("Company deactivated: companyId={}", companyId);
        return toDto(deactivatedCompany);
    }

    @Override
    public Company getCompanyEntityById(Long companyId) {
        return companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + companyId));
    }

    private List<SocialLink> mapSocialLinks(List<SocialLinkResponse> socialLinks) {
        if (socialLinks == null || socialLinks.isEmpty()) return new ArrayList<>();

        return socialLinks.stream()
                .map(l -> SocialLink.builder()
                        .socialPlatform(l.socialPlatform())
                        .url(l.url())
                        .build())
                .collect(Collectors.toList());
    }

    private String generateUniqueSlug(String name) {
        var base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("[\\s-]+", "-");

        if(!Boolean.TRUE.equals(companyRepository.existsBySlug(base))) return base;

        var counter = 1;
        while (Boolean.TRUE.equals(companyRepository.existsBySlug(base+"-"+counter))) counter++;
        return base+"-"+counter;
    }

    private void assertOwner(Long companyOwnerId, Long ownerId) {
        if (!companyOwnerId.equals(ownerId)) {
            throw new NotOwnerException("You are not the owner of this company.");
        }
    }
}
