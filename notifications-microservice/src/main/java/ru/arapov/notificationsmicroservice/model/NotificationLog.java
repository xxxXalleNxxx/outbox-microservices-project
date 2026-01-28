package ru.arapov.notificationsmicroservice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "notification_logs")
public class NotificationLog {

    @Id
    String id;
    String reservationId;
    String customerPhone;
    String customerEmail;

    @Enumerated(EnumType.STRING)
    NotificationType notificationType;

    String subject;

    LocalDateTime sentAt;
    Boolean success;

    @Column(columnDefinition = "TEXT")
    String errorResponse;
}
