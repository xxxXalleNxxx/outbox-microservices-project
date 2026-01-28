package ru.arapov.reservationmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReservationMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationMicroserviceApplication.class, args);
    }

}
