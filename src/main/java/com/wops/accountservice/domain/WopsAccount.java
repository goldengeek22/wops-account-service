package com.wops.accountservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

/**
 * @author Alexandre AMEVOR
 */
public record WopsAccount(

        @NotBlank(message = "The account number must be set")
        String number,
        @NotNull(message = "The account creation date must be set")
        Date creationDate,
        boolean active,
        boolean locked
) {
}
