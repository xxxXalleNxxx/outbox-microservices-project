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
@Table(name = "reservations")
public class Reservation {

    @Id
    String id;

    String customerPhone;
    String customerEmail;
    String customerName;

    LocalDateTime reservationTime;
    LocalDateTime createdTime;

    Integer guestsCount;
    String tableId;

    @Enumerated(EnumType.STRING)
    ReservationStatus status;
}
