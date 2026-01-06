package com.github.evp2.resourceserver.interaction.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class InteractionParticipantId implements Serializable {
    private UUID interactionId;
    private UUID participantId;

    public InteractionParticipantId() {}

    public InteractionParticipantId(UUID interactionId, UUID participantId) {
        this.interactionId = interactionId;
        this.participantId = participantId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionParticipantId that = (InteractionParticipantId) o;
        return Objects.equals(interactionId, that.interactionId) && Objects.equals(participantId, that.participantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interactionId, participantId);
    }
}
