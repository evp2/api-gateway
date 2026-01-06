package com.github.evp2.resourceserver.identity.infrastructure.persistence;

import com.github.evp2.resourceserver.identity.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
