package com.github.evp2.resourceserver.identity.infrastructure.persistence;

import com.github.evp2.resourceserver.identity.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
}
