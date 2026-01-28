package ru.arapov.notificationsmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.arapov.notificationsmicroservice.model.NotificationEvent;
import ru.arapov.notificationsmicroservice.model.NotificationLog;
import ru.arapov.notificationsmicroservice.model.NotificationType;
import ru.arapov.notificationsmicroservice.repository.NotificationLogRepository;
import tools.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final NotificationLogRepository notificationLogRepository;

    @KafkaListener(topics = "reservation-events")
    public void reservationNotifications(ConsumerRecord<String, String> record) {

        String payload = record.value();
        NotificationEvent event = objectMapper.readValue(payload, NotificationEvent.class);

        sendEmailNotifications(event);
    }

    private void sendEmailNotifications(NotificationEvent event) {

        String subject = "";
        try {

        switch (event.getEventType()) {
            case RESERVATION_CREATED -> {
                emailService.createdNotification(event);
                subject = event.getEventType().toString();
                break;
            }
            case RESERVATION_CONFIRMED -> {
                emailService.confirmNotification(event);
                subject = event.getEventType().toString();

                break;
            }
            case RESERVATION_CANCELLED -> {
                emailService.cancelNotification(event);
                subject = event.getEventType().toString();
                break;
            }
        }

            NotificationLog log = new NotificationLog();
            log.setId(UUID.randomUUID().toString());
            log.setReservationId(event.getReservationId());
            log.setCustomerEmail(event.getCustomerEmail());
            log.setCustomerPhone(event.getCustomerPhone());
            log.setNotificationType(NotificationType.EMAIL);
            log.setSubject(subject);
            log.setSentAt(LocalDateTime.now());
            log.setSuccess(true);

            notificationLogRepository.save(log);
    } catch (Exception e) {
            NotificationLog log = new NotificationLog();
            log.setId(UUID.randomUUID().toString());
            log.setReservationId(event.getReservationId());
            log.setCustomerEmail(event.getCustomerEmail());
            log.setCustomerPhone(event.getCustomerPhone());
            log.setNotificationType(NotificationType.EMAIL);
            log.setSubject(subject);
            log.setSentAt(LocalDateTime.now());
            log.setSuccess(false);
            log.setErrorResponse(e.getMessage());

            notificationLogRepository.save(log);
        }
    }
}
