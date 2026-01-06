package com.github.evp2.resourceserver.followup.infrastructure.persistence;

import com.github.evp2.resourceserver.followup.domain.model.FollowUpCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface FollowUpCompletionRepository extends JpaRepository<FollowUpCompletion, UUID> {
}
