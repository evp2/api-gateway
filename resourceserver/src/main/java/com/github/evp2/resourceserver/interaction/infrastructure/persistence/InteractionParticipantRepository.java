package com.github.evp2.resourceserver.interaction.infrastructure.persistence;

import com.github.evp2.resourceserver.interaction.domain.model.InteractionParticipant;
import com.github.evp2.resourceserver.interaction.domain.model.InteractionParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionParticipantRepository extends JpaRepository<InteractionParticipant, InteractionParticipantId> {
}
