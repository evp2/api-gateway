package com.github.evp2.resourceserver.application.web;

import com.github.evp2.resourceserver.application.domain.model.JobApplication;
import com.github.evp2.resourceserver.application.service.JobApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public List<JobApplication> getAll(@AuthenticationPrincipal Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenantId")); // Assuming tenantId is in JWT
        return service.findAllByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenantId"));
        return service.findById(id, tenantId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public JobApplication create(@RequestBody JobApplication jobApplication, @AuthenticationPrincipal Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenantId"));
        jobApplication.setTenantId(tenantId);
        return service.create(jobApplication);
    }

    @PutMapping("/{id}")
    public JobApplication update(@PathVariable UUID id, @RequestBody JobApplication jobApplication, @AuthenticationPrincipal Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenantId"));
        return service.update(id, jobApplication, tenantId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenantId"));
        service.delete(id, tenantId);
        return ResponseEntity.noContent().build();
    }
}
