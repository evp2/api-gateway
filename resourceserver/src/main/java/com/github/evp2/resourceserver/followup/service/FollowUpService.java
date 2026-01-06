package com.github.evp2.resourceserver.followup.service;

import com.github.evp2.resourceserver.followup.domain.model.FollowUp;
import com.github.evp2.resourceserver.followup.infrastructure.persistence.FollowUpRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FollowUpService {

    private final FollowUpRepository repository;

    public FollowUpService(FollowUpRepository repository) {
        this.repository = repository;
    }

    public List<FollowUp> findAllByTenantId(UUID tenantId) {
        return repository.findAll().stream()
                .filter(f -> tenantId.equals(f.getTenantId()))
                .toList();
    }

    public Optional<FollowUp> findById(UUID followUpId, UUID tenantId) {
        return repository.findById(followUpId)
                .filter(f -> tenantId.equals(f.getTenantId()));
    }

    public FollowUp create(FollowUp followUp) {
        return repository.save(followUp);
    }

    public FollowUp update(UUID followUpId, FollowUp details, UUID tenantId) {
        return repository.findById(followUpId)
                .filter(f -> tenantId.equals(f.getTenantId()))
                .map(existing -> {
                    existing.setDueDate(details.getDueDate());
                    existing.setStatus(details.getStatus());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Follow Up not found or unauthorized"));
    }

    public void delete(UUID followUpId, UUID tenantId) {
        repository.findById(followUpId)
                .filter(f -> tenantId.equals(f.getTenantId()))
                .ifPresent(repository::delete);
    }
}
