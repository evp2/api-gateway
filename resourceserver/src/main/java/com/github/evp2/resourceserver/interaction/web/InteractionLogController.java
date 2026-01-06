package com.github.evp2.resourceserver.interaction.web;

import com.github.evp2.resourceserver.interaction.domain.model.InteractionLog;
import com.github.evp2.resourceserver.interaction.service.InteractionLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/interactions")
public class InteractionLogController {

    private final InteractionLogService service;

    public InteractionLogController(InteractionLogService service) {
        this.service = service;
    }

    @GetMapping
    public List<InteractionLog> getAll(@AuthenticationPrincipal Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenantId"));
        return service.findAllByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InteractionLog> getById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenantId"));
        return service.findById(id, tenantId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public InteractionLog create(@RequestBody InteractionLog interactionLog, @AuthenticationPrincipal Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenantId"));
        interactionLog.setTenantId(tenantId);
        return service.create(interactionLog);
    }

    @PutMapping("/{id}")
    public InteractionLog update(@PathVariable UUID id, @RequestBody InteractionLog interactionLog, @AuthenticationPrincipal Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenantId"));
        return service.update(id, interactionLog, tenantId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenantId"));
        service.delete(id, tenantId);
        return ResponseEntity.noContent().build();
    }
}
