package ru.arapov.notificationsmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.stereotype.Repository;
import ru.arapov.notificationsmicroservice.model.NotificationLog;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, String> {
}
