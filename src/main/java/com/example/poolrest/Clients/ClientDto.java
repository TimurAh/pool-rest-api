package com.example.poolrest.Clients;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

public record ClientDto(
        @Null
        Long id,
        @NotNull
        String name,
        @Email
        String email,
        @Pattern(
                regexp = "^\\+?[1-9]\\d{1,14}$"
        )
        String phone
) {

}
