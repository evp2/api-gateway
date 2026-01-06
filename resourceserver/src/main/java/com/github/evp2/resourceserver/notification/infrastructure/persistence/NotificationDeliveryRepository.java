package com.github.evp2.resourceserver.notification.infrastructure.persistence;

import com.github.evp2.resourceserver.notification.domain.model.NotificationDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface NotificationDeliveryRepository extends JpaRepository<NotificationDelivery, UUID> {
}
