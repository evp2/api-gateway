package com.github.evp2.resourceserver.application.service;

import com.github.evp2.resourceserver.application.domain.model.JobApplication;
import com.github.evp2.resourceserver.application.infrastructure.persistence.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {

    @Mock
    private JobApplicationRepository repository;

    @InjectMocks
    private JobApplicationService service;

    private UUID tenantId;
    private JobApplication application;

    @BeforeEach
    void setUp() {
        tenantId = UUID.randomUUID();
        application = new JobApplication();
        application.setApplicationId(UUID.randomUUID());
        application.setTenantId(tenantId);
        application.setJobTitle("Software Engineer");
        application.setCompanyName("Tech Corp");
    }

    @Test
    void findAllByTenantId() {
        when(repository.findByTenantId(tenantId)).thenReturn(List.of(application));

        List<JobApplication> result = service.findAllByTenantId(tenantId);

        assertEquals(1, result.size());
        assertEquals("Software Engineer", result.get(0).getJobTitle());
        verify(repository).findByTenantId(tenantId);
    }

    @Test
    void findById_Success() {
        when(repository.findById(application.getApplicationId())).thenReturn(Optional.of(application));

        Optional<JobApplication> result = service.findById(application.getApplicationId(), tenantId);

        assertTrue(result.isPresent());
        assertEquals(application.getApplicationId(), result.get().getApplicationId());
    }

    @Test
    void findById_WrongTenant() {
        when(repository.findById(application.getApplicationId())).thenReturn(Optional.of(application));

        Optional<JobApplication> result = service.findById(application.getApplicationId(), UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void create() {
        when(repository.save(any(JobApplication.class))).thenReturn(application);

        JobApplication result = service.create(new JobApplication());

        assertNotNull(result);
        assertEquals("Software Engineer", result.getJobTitle());
    }

    @Test
    void update_Success() {
        when(repository.findById(application.getApplicationId())).thenReturn(Optional.of(application));
        when(repository.save(any(JobApplication.class))).thenReturn(application);

        JobApplication details = new JobApplication();
        details.setJobTitle("Senior Software Engineer");

        JobApplication result = service.update(application.getApplicationId(), details, tenantId);

        assertEquals("Senior Software Engineer", result.getJobTitle());
    }

    @Test
    void delete() {
        when(repository.findById(application.getApplicationId())).thenReturn(Optional.of(application));

        service.delete(application.getApplicationId(), tenantId);

        verify(repository).delete(application);
    }
}
