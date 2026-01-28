package ru.arapov.reservationmicroservice.model;

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
@Table(name = "reservation_outbox")
public class ReservationEvent {

    @Id
    String eventId;
    String reservationId;
    String customerPhone;
    String customerEmail;
    String customerName;

    @Enumerated(EnumType.STRING)
    EventType eventType;

    @Column(columnDefinition = "TEXT")
    String payload;

    @Enumerated(EnumType.STRING)
    EventStatus eventStatus;

    LocalDateTime createdAt;
    LocalDateTime sentAt;

    Integer retryCount;
}
