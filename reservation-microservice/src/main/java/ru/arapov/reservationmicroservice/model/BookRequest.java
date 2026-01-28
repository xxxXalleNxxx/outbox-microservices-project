package ru.arapov.reservationmicroservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {

        String customerPhone;
        String customerEmail;
        String customerName;
        LocalDateTime reservationTime;
        Integer guestsCount;
        String tableId;

}
