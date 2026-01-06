package com.github.evp2.resourceserver.notification.infrastructure.persistence;

import com.github.evp2.resourceserver.notification.domain.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
