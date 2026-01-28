package ru.arapov.reservationmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.arapov.reservationmicroservice.model.Reservation;
import ru.arapov.reservationmicroservice.model.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

    Optional<Reservation> findReservationById(String id);
}
