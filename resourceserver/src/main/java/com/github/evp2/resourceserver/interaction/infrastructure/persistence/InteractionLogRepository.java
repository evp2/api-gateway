package com.github.evp2.resourceserver.interaction.infrastructure.persistence;

import com.github.evp2.resourceserver.interaction.domain.model.InteractionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface InteractionLogRepository extends JpaRepository<InteractionLog, UUID> {
}
