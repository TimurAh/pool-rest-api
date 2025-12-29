package com.example.poolrest.Reservations;

import com.example.poolrest.Reservations.ReservationStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
//учитывая, что требуется потом добавить запись на несколько часов datetime странно
public record ReservationDto(
        @Null
        Long id,
        @NotNull
        Long clientId,
        @FutureOrPresent
        @NotNull
        LocalDateTime datetime
) {

}
