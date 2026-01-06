package com.github.evp2.resourceserver.identity.service;

import com.github.evp2.resourceserver.identity.domain.model.User;
import com.github.evp2.resourceserver.identity.infrastructure.persistence.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAllByTenantId(UUID tenantId) {
        return repository.findAll().stream()
                .filter(u -> tenantId.equals(u.getTenantId()))
                .toList();
    }

    public Optional<User> findById(UUID userId, UUID tenantId) {
        return repository.findById(userId)
                .filter(u -> tenantId.equals(u.getTenantId()));
    }

    public User create(User user) {
        return repository.save(user);
    }

    public User update(UUID userId, User details, UUID tenantId) {
        return repository.findById(userId)
                .filter(u -> tenantId.equals(u.getTenantId()))
                .map(existing -> {
                    existing.setEmail(details.getEmail());
                    existing.setDisplayName(details.getDisplayName());
                    existing.setStatus(details.getStatus());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found or unauthorized"));
    }

    public void delete(UUID userId, UUID tenantId) {
        repository.findById(userId)
                .filter(u -> tenantId.equals(u.getTenantId()))
                .ifPresent(repository::delete);
    }
}
