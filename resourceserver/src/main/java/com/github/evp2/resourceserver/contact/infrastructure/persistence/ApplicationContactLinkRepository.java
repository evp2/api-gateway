package com.github.evp2.resourceserver.contact.infrastructure.persistence;

import com.github.evp2.resourceserver.contact.domain.model.ApplicationContactLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ApplicationContactLinkRepository extends JpaRepository<ApplicationContactLink, UUID> {
}
