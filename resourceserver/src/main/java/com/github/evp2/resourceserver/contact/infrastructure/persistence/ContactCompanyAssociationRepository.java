package com.github.evp2.resourceserver.contact.infrastructure.persistence;

import com.github.evp2.resourceserver.contact.domain.model.ContactCompanyAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ContactCompanyAssociationRepository extends JpaRepository<ContactCompanyAssociation, UUID> {
}
