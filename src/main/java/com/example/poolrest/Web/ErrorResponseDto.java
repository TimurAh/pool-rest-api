package com.example.poolrest.Web;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String message,
        LocalDateTime errorTime,
        String errorMessage
) {

}
