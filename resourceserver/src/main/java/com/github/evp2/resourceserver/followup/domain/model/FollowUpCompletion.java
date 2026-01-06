package com.github.evp2.resourceserver.followup.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "follow_up_completions")
public class FollowUpCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID completionId;

    @Column(nullable = false)
    private UUID followUpId;

    @Column(nullable = false)
    private UUID completedByUserId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime completedAt;

    @Column(columnDefinition = "TEXT")
    private String outcomeNotes;

    public FollowUpCompletion() {
    }

    @PrePersist
    protected void onCreate() {
        completedAt = LocalDateTime.now();
    }

    public UUID getCompletionId() {
        return completionId;
    }

    public void setCompletionId(UUID completionId) {
        this.completionId = completionId;
    }

    public UUID getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(UUID followUpId) {
        this.followUpId = followUpId;
    }

    public UUID getCompletedByUserId() {
        return completedByUserId;
    }

    public void setCompletedByUserId(UUID completedByUserId) {
        this.completedByUserId = completedByUserId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getOutcomeNotes() {
        return outcomeNotes;
    }

    public void setOutcomeNotes(String outcomeNotes) {
        this.outcomeNotes = outcomeNotes;
    }
}
