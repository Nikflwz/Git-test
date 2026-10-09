package ru.bulgakov.qa.booking;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.bulgakov.qa.booking.dto.CreateBookingDTO;
import ru.bulgakov.qa.booking.dto.CreateBookingDTO.BookingDates;
import ru.bulgakov.qa.booking.dto.CreateBookingResponse;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingTest {

    private static final String BOOKING_URL = "https://restful-booker.herokuapp.com";

    @BeforeAll
    static void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    @Tag("positive")
    void createBookingTest() {
        CreateBookingResponse resp = given()
                .contentType(ContentType.JSON)
                .body(bookingWith("Vladimir", "Putin", 999, "2026-08-10", "2027-08-10"))
                .post(BOOKING_URL + "/booking")
                .then()
                .statusCode(200)
                .extract().as(CreateBookingResponse.class);

        assertThat(resp.getBookingid()).isNotNull();
        assertThat(resp.getBooking().getTotalprice()).isEqualTo(999);
        assertThat(resp.getBooking().getBookingdates().getCheckin()).isEqualTo("2026-08-10");
        assertThat(resp.getBooking().getDepositpaid()).isTrue();
    }

    static Stream<Arguments> invalidBookingData() {
        return Stream.of(
                Arguments.of("Без firstname",
                        bookingWith(null, "Putin", 999, "2026-08-10", "2027-08-10"),
                        500),
                Arguments.of("Без lastname",
                        bookingWith("Vladimir", null, 999, "2026-08-10", "2027-08-10"),
                        500),
                Arguments.of("Отрицательная цена",
                        bookingWith("Vladimir", "Putin", -500, "2026-08-10", "2027-08-10"),
                        200),
                Arguments.of("Даты в неверном формате",
                        bookingWith("Vladimir", "Putin", 999, "не-дата", "2027-08-10"),
                        200),
                Arguments.of("Дата выезда раньше заезда",
                        bookingWith("Vladimir", "Putin", 999, "2026-08-10", "2026-08-01"),
                        200),
                Arguments.of("Пустое body",
                        new CreateBookingDTO(),
                        500)
        );
    }

    @ParameterizedTest(name = "Бронирование: {0}")
    @MethodSource("invalidBookingData")
    @Tag("negative")
    void createBookingNegativeTest(String scenario, CreateBookingDTO request, int expectedStatus) {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(request)
                .post(BOOKING_URL + "/booking")
                .then()
                .extract().response();

        assertThat(resp.statusCode())
                .as("Статус-код для сценария: " + scenario)
                .isEqualTo(expectedStatus);

        if (expectedStatus == 200) {
            CreateBookingResponse body = resp.as(CreateBookingResponse.class);
            assertThat(body.getBookingid())
                    .as("bookingid для сценария: " + scenario)
                    .isNotNull();
        }
    }

    private static CreateBookingDTO bookingWith(String firstname,
                                                String lastname,
                                                Integer totalprice,
                                                String checkin,
                                                String checkout) {
        return CreateBookingDTO.builder()
                .firstname(firstname)
                .lastname(lastname)
                .totalprice(totalprice)
                .depositpaid(true)
                .bookingdates(new BookingDates(checkin, checkout))
                .additionalneeds("money")
                .build();
    }
}