package ru.arapov.reservationmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arapov.reservationmicroservice.model.*;
import ru.arapov.reservationmicroservice.repository.ReservationEventRepository;
import ru.arapov.reservationmicroservice.repository.ReservationRepository;
import tools.jackson.databind.json.JsonMapper;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {

    private final JsonMapper jsonMapper;
    private final ReservationRepository reservationRepository;
    private final ReservationEventRepository reservationEventRepository;

    @Transactional
    public String createReservation(BookRequest bookRequest) {

        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setCustomerName(bookRequest.getCustomerName());
        reservation.setCustomerEmail(bookRequest.getCustomerEmail());
        reservation.setCustomerPhone(bookRequest.getCustomerPhone());
        reservation.setReservationTime(bookRequest.getReservationTime());
        reservation.setGuestsCount(bookRequest.getGuestsCount());
        reservation.setTableId(bookRequest.getTableId());
        reservation.setCreatedTime(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.PENDING);
        reservationRepository.save(reservation);

        ReservationEvent event = new ReservationEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setReservationId(reservation.getId());
        event.setCustomerName(reservation.getCustomerName());
        event.setCustomerEmail(reservation.getCustomerEmail());
        event.setCustomerPhone(reservation.getCustomerPhone());
        event.setEventType(EventType.RESERVATION_CREATED);
        event.setPayload(createPayload(reservation, EventType.RESERVATION_CREATED));
        event.setEventStatus(EventStatus.PENDING);
        event.setCreatedAt(LocalDateTime.now());
        event.setSentAt(null);
        event.setRetryCount(0);
        reservationEventRepository.save(event);

        return reservation.getId();
    }

    @Transactional
    public void confirmReservation(String reservationId) {

        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation Id cannot be null!");
        }

        Reservation reservation = reservationRepository.findReservationById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with id - " + reservationId));

        if (!reservation.getStatus().equals(ReservationStatus.PENDING)) {
            throw new IllegalStateException("cannot confirm non-pending reservation!");
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);

        ReservationEvent event = new ReservationEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setReservationId(reservation.getId());
        event.setCustomerName(reservation.getCustomerName());
        event.setCustomerEmail(reservation.getCustomerEmail());
        event.setCustomerPhone(reservation.getCustomerPhone());
        event.setEventType(EventType.RESERVATION_CONFIRMED);
        event.setPayload(createPayload(reservation, EventType.RESERVATION_CONFIRMED));
        event.setEventStatus(EventStatus.PENDING);
        event.setCreatedAt(LocalDateTime.now());
        event.setSentAt(null);
        event.setRetryCount(0);
        reservationEventRepository.save(event);
    }

    @Transactional
    public void cancelReservation(String reservationId) {
        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation Id cannot be null!");
        }

        Reservation reservation = reservationRepository.findReservationById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with id - " + reservationId));

        if (reservation.getReservationTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot cancel past reservation");
        }

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation already cancelled");
        }

        Duration timeUntilReservation = Duration.between(LocalDateTime.now(), reservation.getReservationTime());
        if (timeUntilReservation.toHours() < 1) {
            throw new IllegalStateException("Cannot cancel reservation less than 1 hour before");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

        ReservationEvent event = new ReservationEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setReservationId(reservation.getId());
        event.setCustomerName(reservation.getCustomerName());
        event.setCustomerEmail(reservation.getCustomerEmail());
        event.setCustomerPhone(reservation.getCustomerPhone());
        event.setEventType(EventType.RESERVATION_CANCELLED);
        event.setPayload(createPayload(reservation, EventType.RESERVATION_CANCELLED));
        event.setEventStatus(EventStatus.PENDING);
        event.setCreatedAt(LocalDateTime.now());
        event.setSentAt(null);
        event.setRetryCount(0);
        reservationEventRepository.save(event);
    }

    private String createPayload(Reservation reservation, EventType eventType) {
        Map<String, Object> payload = new HashMap<>();

        payload.put("eventId", UUID.randomUUID().toString());
        payload.put("eventType", eventType.toString());
        payload.put("reservationId", reservation.getId());
        payload.put("customerName", reservation.getCustomerName());
        payload.put("customerPhone", reservation.getCustomerPhone());
        payload.put("customerEmail", reservation.getCustomerEmail());
        payload.put("reservationTime", reservation.getReservationTime());
        payload.put("status", reservation.getStatus());

        return jsonMapper.writeValueAsString(payload);
    }
}
