package com.github.evp2.resourceserver.identity.infrastructure.persistence;

import com.github.evp2.resourceserver.identity.domain.model.UserRole;
import com.github.evp2.resourceserver.identity.domain.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
