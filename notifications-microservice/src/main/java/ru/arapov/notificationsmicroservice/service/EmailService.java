package ru.arapov.notificationsmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.arapov.notificationsmicroservice.model.NotificationEvent;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    public void createdNotification(NotificationEvent event) {
        log.info("Reservation created" +
                        "\nDearly {}, your reservation {} was created successfully",
                event.getCustomerName(), event.getReservationTime());
    }

    public void confirmNotification(NotificationEvent event) {
        log.info("Reservation confirmed" +
                        "\n{}, your reservation was confirmed! Waiting for you {}",
                event.getCustomerName(), event.getReservationTime());
    }

    public void cancelNotification(NotificationEvent event) {
        log.info("Reservation cancelled" +
        "\n{}, your reservation on {} was cancelled",
                event.getCustomerName(), event.getReservationTime());
    }
}
