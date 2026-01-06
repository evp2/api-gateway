package com.github.evp2.resourceserver.application.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "application_status_history")
public class ApplicationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID statusHistoryId;

    @Column(nullable = false)
    private UUID applicationId;

    private String previousStatus;

    @Column(nullable = false)
    private String newStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime changedAt;

    @Column(nullable = false)
    private UUID changedByUserId;

    public ApplicationStatusHistory() {
    }

    @PrePersist
    protected void onCreate() {
        changedAt = LocalDateTime.now();
    }

    public UUID getStatusHistoryId() {
        return statusHistoryId;
    }

    public void setStatusHistoryId(UUID statusHistoryId) {
        this.statusHistoryId = statusHistoryId;
    }

    public UUID getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(UUID applicationId) {
        this.applicationId = applicationId;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public UUID getChangedByUserId() {
        return changedByUserId;
    }

    public void setChangedByUserId(UUID changedByUserId) {
        this.changedByUserId = changedByUserId;
    }
}
