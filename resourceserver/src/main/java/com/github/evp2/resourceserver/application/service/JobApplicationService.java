package com.github.evp2.resourceserver.application.service;

import com.github.evp2.resourceserver.application.domain.model.JobApplication;
import com.github.evp2.resourceserver.application.infrastructure.persistence.JobApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class JobApplicationService {

    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public List<JobApplication> findAllByTenantId(UUID tenantId) {
        return repository.findByTenantId(tenantId);
    }

    public Optional<JobApplication> findById(UUID applicationId, UUID tenantId) {
        return repository.findById(applicationId)
                .filter(app -> tenantId.equals(app.getTenantId()));
    }

    public JobApplication create(JobApplication jobApplication) {
        return repository.save(jobApplication);
    }

    public JobApplication update(UUID applicationId, JobApplication details, UUID tenantId) {
        return repository.findById(applicationId)
                .filter(app -> tenantId.equals(app.getTenantId()))
                .map(existing -> {
                    existing.setJobTitle(details.getJobTitle());
                    existing.setCompanyName(details.getCompanyName());
                    existing.setJobPostingUrl(details.getJobPostingUrl());
                    existing.setCurrentStatus(details.getCurrentStatus());
                    existing.setAppliedDate(details.getAppliedDate());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Job Application not found or unauthorized"));
    }

    public void delete(UUID applicationId, UUID tenantId) {
        repository.findById(applicationId)
                .filter(app -> tenantId.equals(app.getTenantId()))
                .ifPresent(repository::delete);
    }
}
