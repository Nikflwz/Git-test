package ru.bulgakov.qa.booking.steps;

import io.qameta.allure.Step;
import net.datafaker.Faker;
import ru.bulgakov.qa.booking.dto.BookingDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BookingSteps {
    private static final Faker faker = new Faker();

    @Step("Проверить соответствие всех полей в ответе")
    public static void bookingsShouldBeEqual(BookingDTO expected, BookingDTO actual) {
        assertAll(
                () -> assertThat(actual.getFirstname())
                        .as("firstname отличается от ожидаемого")
                        .isEqualTo(expected.getFirstname()),
                () -> assertThat(actual.getLastname())
                        .as("lastname отличается от ожидаемого")
                        .isEqualTo(expected.getLastname()),
                () -> assertThat(actual.getTotalprice())
                        .as("totalPrice отличается от ожидаемого")
                        .isEqualTo(expected.getTotalprice()),
                () -> assertThat(actual.getDepositpaid())
                        .as("depositpaid отличается от ожидаемого")
                        .isEqualTo(expected.getDepositpaid()),
                () -> assertThat(actual.getAdditionalneeds())
                        .as("additionalneeds отличается от ожидаемого")
                        .isEqualTo(expected.getAdditionalneeds()),
                () -> assertThat(actual.getBookingdates())
                        .as("bookingdates равен null")
                        .isNotNull(),
                () -> assertThat(actual.getBookingdates().getCheckin())
                        .as("checkin отличается от ожидаемого")
                        .isEqualTo(expected.getBookingdates().getCheckin()),
                () -> assertThat(actual.getBookingdates().getCheckout())
                        .as("checkout отличается от ожидаемого")
                        .isEqualTo(expected.getBookingdates().getCheckout())
        );
    }

    public static BookingDTO buildBookingRequest() {
        return BookingDTO.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(1000, 10000))
                .depositpaid(faker.bool().bool())
                .bookingdates(BookingDTO.BookingDates.builder()
                        .checkin("2026-08-10")
                        .checkout("2027-08-10")
                        .build())
                .additionalneeds(faker.videoGame().title())
                .build();
    }
}
