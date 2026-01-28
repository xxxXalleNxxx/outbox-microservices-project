package ru.arapov.reservationmicroservice.repository;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.arapov.reservationmicroservice.model.EventStatus;
import ru.arapov.reservationmicroservice.model.EventType;
import ru.arapov.reservationmicroservice.model.ReservationEvent;

import java.util.List;

@Repository
public interface ReservationEventRepository extends JpaRepository<ReservationEvent, String> {

    List<ReservationEvent> findReservationEventByEventStatusOrderByCreatedAt(EventStatus status);
}
