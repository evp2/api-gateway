package com.github.evp2.resourceserver.interaction.domain.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "interaction_participants")
@IdClass(InteractionParticipantId.class)
public class InteractionParticipant {

    @Id
    private UUID interactionId;

    @Id
    private UUID participantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParticipantType participantType;

    public InteractionParticipant() {
    }

    public UUID getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(UUID interactionId) {
        this.interactionId = interactionId;
    }

    public UUID getParticipantId() {
        return participantId;
    }

    public void setParticipantId(UUID participantId) {
        this.participantId = participantId;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public enum ParticipantType {
        USER, CONTACT
    }
}

