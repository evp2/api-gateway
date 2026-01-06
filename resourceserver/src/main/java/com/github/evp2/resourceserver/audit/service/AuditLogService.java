package com.github.evp2.resourceserver.audit.service;

import com.github.evp2.resourceserver.audit.domain.model.AuditLog;
import com.github.evp2.resourceserver.audit.infrastructure.persistence.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuditLogService {

    private final AuditLogRepository repository;

    public AuditLogService(AuditLogRepository repository) {
        this.repository = repository;
    }

    public List<AuditLog> findAllByTenantId(UUID tenantId) {
        return repository.findAll().stream()
                .filter(log -> tenantId.equals(log.getTenantId()))
                .toList();
    }

    public AuditLog create(AuditLog auditLog) {
        return repository.save(auditLog);
    }
}
