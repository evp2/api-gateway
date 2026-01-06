package com.github.evp2.resourceserver.interaction.infrastructure.persistence;

import com.github.evp2.resourceserver.interaction.domain.model.InteractionNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface InteractionNoteRepository extends JpaRepository<InteractionNote, UUID> {
}
