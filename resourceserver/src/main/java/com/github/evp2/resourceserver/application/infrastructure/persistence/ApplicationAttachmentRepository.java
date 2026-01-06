package com.github.evp2.resourceserver.application.infrastructure.persistence;

import com.github.evp2.resourceserver.application.domain.model.ApplicationAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ApplicationAttachmentRepository extends JpaRepository<ApplicationAttachment, UUID> {
}
