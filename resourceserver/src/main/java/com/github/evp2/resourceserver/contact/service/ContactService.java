package com.github.evp2.resourceserver.contact.service;

import com.github.evp2.resourceserver.contact.domain.model.Contact;
import com.github.evp2.resourceserver.contact.infrastructure.persistence.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ContactService {

    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public List<Contact> findAllByTenantId(UUID tenantId) {
        return repository.findAll().stream()
                .filter(c -> tenantId.equals(c.getTenantId()))
                .toList();
    }

    public Optional<Contact> findById(UUID contactId, UUID tenantId) {
        return repository.findById(contactId)
                .filter(c -> tenantId.equals(c.getTenantId()));
    }

    public Contact create(Contact contact) {
        return repository.save(contact);
    }

    public Contact update(UUID contactId, Contact details, UUID tenantId) {
        return repository.findById(contactId)
                .filter(c -> tenantId.equals(c.getTenantId()))
                .map(existing -> {
                    existing.setFirstName(details.getFirstName());
                    existing.setLastName(details.getLastName());
                    existing.setEmail(details.getEmail());
                    existing.setPhone(details.getPhone());
                    existing.setContactType(details.getContactType());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Contact not found or unauthorized"));
    }

    public void delete(UUID contactId, UUID tenantId) {
        repository.findById(contactId)
                .filter(c -> tenantId.equals(c.getTenantId()))
                .ifPresent(repository::delete);
    }
}
