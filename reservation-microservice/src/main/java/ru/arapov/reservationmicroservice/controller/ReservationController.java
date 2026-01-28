package ru.arapov.reservationmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.arapov.reservationmicroservice.model.BookRequest;
import ru.arapov.reservationmicroservice.service.ReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestBody BookRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    @PatchMapping("{id}/confirm")
    public ResponseEntity<?> confirmReservation(@PathVariable String id) {
        reservationService.confirmReservation(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable String id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok().build();
    }
}
