package ru.bulgakov.qa.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;

    public BookingDTO(String firstname, Integer totalprice, String checkin) {
        this.firstname = firstname;
        this.totalprice = totalprice;
        this.bookingdates = new BookingDates();
        this.bookingdates.checkin = checkin;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)

    public static class BookingDates {
        private String checkin;
        private String checkout;
    }
}
