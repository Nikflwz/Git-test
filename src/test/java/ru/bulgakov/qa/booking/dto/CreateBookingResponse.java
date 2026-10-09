package ru.bulgakov.qa.booking.dto;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBookingResponse {
    private Integer bookingid;
    private CreateBookingDTO booking;
}
