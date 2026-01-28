package ru.arapov.reservationmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.arapov.reservationmicroservice.model.EventStatus;
import ru.arapov.reservationmicroservice.model.ReservationEvent;
import ru.arapov.reservationmicroservice.repository.ReservationEventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class OutboxPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ReservationEventRepository reservationEventRepository;


    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void publish() {

        log.info("OutboxPublisher started...");

        List<ReservationEvent> events = reservationEventRepository.findReservationEventByEventStatusOrderByCreatedAt(EventStatus.PENDING);

        log.info("Found {} pending events", events.size());

        for (ReservationEvent event : events) {
            try {
                kafkaTemplate.send("reservation-events", event.getPayload()).get(5, TimeUnit.SECONDS);
                event.setEventStatus(EventStatus.SENT);
                event.setSentAt(LocalDateTime.now());
                reservationEventRepository.save(event);

                log.info("Event published successfully: {}", event.getEventId());

            } catch (Exception e) {
                int retryCount = event.getRetryCount() + 1;
                event.setRetryCount(retryCount);

                if (retryCount > 3) {
                    event.setEventStatus(EventStatus.FAILED);
                    log.warn("Event {} marked as FAILED after {} retries", event.getEventId(), event.getRetryCount());
                } else {
                    event.setEventStatus(EventStatus.PENDING);
                }

                reservationEventRepository.save(event);
            }
        }
    }
}
