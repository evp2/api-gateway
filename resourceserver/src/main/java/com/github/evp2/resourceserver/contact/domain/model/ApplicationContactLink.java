package com.github.evp2.resourceserver.contact.domain.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "application_contact_links")
public class ApplicationContactLink {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID linkId;

    @Column(nullable = false)
    private UUID applicationId;

    @Column(nullable = false)
    private UUID contactId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RelationshipType relationshipType;

    public ApplicationContactLink() {
    }

    public UUID getLinkId() {
        return linkId;
    }

    public void setLinkId(UUID linkId) {
        this.linkId = linkId;
    }

    public UUID getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(UUID applicationId) {
        this.applicationId = applicationId;
    }

    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public enum RelationshipType {
        RECRUITER, INTERVIEWER, REFERRAL
    }
}
