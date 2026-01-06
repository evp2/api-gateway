package com.github.evp2.resourceserver.interaction.service;

import com.github.evp2.resourceserver.interaction.domain.model.InteractionLog;
import com.github.evp2.resourceserver.interaction.infrastructure.persistence.InteractionLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class InteractionLogService {

    private final InteractionLogRepository repository;

    public InteractionLogService(InteractionLogRepository repository) {
        this.repository = repository;
    }

    public List<InteractionLog> findAllByTenantId(UUID tenantId) {
        return repository.findAll().stream()
                .filter(i -> tenantId.equals(i.getTenantId()))
                .toList();
    }

    public Optional<InteractionLog> findById(UUID interactionId, UUID tenantId) {
        return repository.findById(interactionId)
                .filter(i -> tenantId.equals(i.getTenantId()));
    }

    public InteractionLog create(InteractionLog interactionLog) {
        return repository.save(interactionLog);
    }

    public InteractionLog update(UUID interactionId, InteractionLog details, UUID tenantId) {
        return repository.findById(interactionId)
                .filter(i -> tenantId.equals(i.getTenantId()))
                .map(existing -> {
                    existing.setInteractionType(details.getInteractionType());
                    existing.setOccurredAt(details.getOccurredAt());
                    existing.setSummary(details.getSummary());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Interaction Log not found or unauthorized"));
    }

    public void delete(UUID interactionId, UUID tenantId) {
        repository.findById(interactionId)
                .filter(i -> tenantId.equals(i.getTenantId()))
                .ifPresent(repository::delete);
    }
}
