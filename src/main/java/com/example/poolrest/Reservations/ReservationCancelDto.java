package com.example.poolrest.Reservations;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

//учитывая, что требуется потом добавить запись на несколько часов datetime странно
public record ReservationCancelDto(
        @NotNull
        Long clientId,
        @NotNull
        Long orderId
) {

}
