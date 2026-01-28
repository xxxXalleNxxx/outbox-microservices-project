###Reservation System with Outbox Pattern###

##Практическая реализация Outbox Pattern в распределенной системе для обеспечения надежной доставки событий между микросервисами.##

## Технологии##
#Java 17+, Spring Boot 3#

#PostgreSQL#

#Apache Kafka#

#Outbox Pattern#

#Docker#

##Микросервисы##
1. Reservation Service
Создание, подтверждение, отмена броней

Outbox Pattern: события сохраняются в БД в той же транзакции

OutboxPublisher: фоновая задача отправки событий в Kafka

2. Notification Service
Обработка событий из Kafka

Отправка уведомлений (реализовано логирование)

Сохранение логов отправки
