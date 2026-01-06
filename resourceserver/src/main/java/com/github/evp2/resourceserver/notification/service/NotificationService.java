package com.github.evp2.resourceserver.notification.service;

import com.github.evp2.resourceserver.notification.domain.model.Notification;
import com.github.evp2.resourceserver.notification.infrastructure.persistence.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public List<Notification> findAllByUserId(UUID userId, UUID tenantId) {
        return repository.findAll().stream()
                .filter(n -> tenantId.equals(n.getTenantId()) && userId.equals(n.getUserId()))
                .toList();
    }

    public Optional<Notification> findById(UUID notificationId, UUID tenantId) {
        return repository.findById(notificationId)
                .filter(n -> tenantId.equals(n.getTenantId()));
    }

    public Notification create(Notification notification) {
        return repository.save(notification);
    }

    public Notification markAsRead(UUID notificationId, UUID tenantId) {
        return repository.findById(notificationId)
                .filter(n -> tenantId.equals(n.getTenantId()))
                .map(existing -> {
                    existing.setReadStatus(Notification.ReadStatus.READ);
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Notification not found or unauthorized"));
    }

    public void delete(UUID notificationId, UUID tenantId) {
        repository.findById(notificationId)
                .filter(n -> tenantId.equals(n.getTenantId()))
                .ifPresent(repository::delete);
    }
}
