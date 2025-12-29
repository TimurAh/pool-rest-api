package com.example.poolrest.Reservations;

import java.time.LocalTime;

public record ReservationSlotDto(LocalTime time, Long count) {
}
