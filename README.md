# Pool REST API

REST API для управления бронированиями в бассейне.

Реализовано на **Spring Boot 3** + **JPA** + **PostgreSQL**.

## Функционал

### Обязательное (по ТЗ)
- Регистрация клиентов
- Бронирование по часам (10:00, 11:00 и т.д.)
- Получение занятых слотов на дату
- Создание и отмена брони

### Дополнительно
- График работы по дням недели
- Поиск броней по дате
- Валидация телефона и email

### Не реализовано
- Ограничение 10 человек в час
- Ограничение 1 посещение в день
- Праздничные дни с отдельным графиком
- Запись на несколько часов подряд

## Эндпоинты

### Клиенты
- `GET /api/v0/pool/client/all` — все клиенты
- `GET /api/v0/pool/client/get/{id}` — клиент по ID
- `POST /api/v0/pool/client/add` — создать клиента
- `DELETE /api/v0/pool/client/delete/{id}` — удалить клиента

### Бронирования
- `GET /api/v0/pool/timetable/all?date=2025-12-30` — занятые слоты на дату
- `POST /api/v0/pool/timetable/reserve` — создать бронь
- `POST /api/v0/pool/timetable/cancel` — отменить бронь

## Запуск

```bash
git clone https://github.com/TimurAh/pool-rest-api.git
cd pool-rest-api
./mvnw spring-boot:run