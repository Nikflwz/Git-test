package ru.bulgakov.qa.booking;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.booking.dto.AuthRequest;
import ru.bulgakov.qa.booking.dto.AuthResponse;
import ru.bulgakov.qa.booking.dto.CreateBookingDTO;
import ru.bulgakov.qa.booking.dto.CreateBookingDTO.BookingDates;
import ru.bulgakov.qa.booking.dto.CreateBookingResponse;

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
    void authTest() {
        String user = "admin";
        String password = "password123";

        Response resp = given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest(user, password))
                .post(BOOKING_URL + "/auth")
                .then()
                .extract().response();

        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.as(AuthResponse.class).getToken()).isNotNull();
    }

    @Test
    void createBookingTest() {
        CreateBookingResponse resp = given()
                .contentType(ContentType.JSON)
                .body(buildBookingRequest())
                .post(BOOKING_URL + "/booking")
                .then()
                .statusCode(200)
                .extract().as(CreateBookingResponse.class);

        assertThat(resp.getBookingid()).isNotNull();
        assertThat(resp.getBooking().getTotalprice()).isEqualTo(999);
        assertThat(resp.getBooking().getBookingDates().getCheckin()).isEqualTo("2026-08-10");
        assertThat(resp.getBooking().getDepositpaid()).isTrue();
    }

    private static CreateBookingDTO bookingRequest() {
        CreateBookingDTO booking = new CreateBookingDTO();
        booking.setFirstname("Vladimir");
        booking.setLastname("Putin");
        booking.setTotalprice(999);
        booking.setDepositpaid(true);
        booking.setBookingDates(new BookingDates("2026-08-10", "2027-08-10"));
        booking.setAdditionalneeds("money");

        return booking;
    }

    private static CreateBookingDTO buildBookingRequest() {
        return CreateBookingDTO.builder()
                .firstname("Vladimir")
                .lastname("Putin")
                .totalprice(999)
                .depositpaid(true)
                .bookingDates(BookingDates.builder()
                        .checkin("2026-08-10")
                        .checkout("2027-08-10")
                        .build())
                .additionalneeds("money")
                .build();
    }
}
