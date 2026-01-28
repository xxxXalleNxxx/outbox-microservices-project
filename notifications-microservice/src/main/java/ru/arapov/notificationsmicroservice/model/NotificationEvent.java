package ru.arapov.notificationsmicroservice.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEvent {

    String eventId;

    @Enumerated(EnumType.STRING)
    EventType eventType;

    String reservationId;
    String customerPhone;
    String customerEmail;
    String customerName;
    LocalDateTime reservationTime;
    String payload;
}
