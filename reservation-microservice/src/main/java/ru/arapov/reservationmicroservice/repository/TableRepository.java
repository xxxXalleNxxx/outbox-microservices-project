package ru.arapov.reservationmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.arapov.reservationmicroservice.model.RestaurantTable;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, String> {

    List<RestaurantTable> findRestaurantTableByRestaurantId(String restaurantId);
}
