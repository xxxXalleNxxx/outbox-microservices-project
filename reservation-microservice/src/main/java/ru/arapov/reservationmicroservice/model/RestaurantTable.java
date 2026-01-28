package ru.arapov.reservationmicroservice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "restaurant_tables")
public class RestaurantTable {

    @Id
    String id;
    String restaurantId;
    String tableNumber;
    Integer capacity;
    Boolean isAvailable;
}
