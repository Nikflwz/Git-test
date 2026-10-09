package ru.bulgakov.qa.booking;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.bulgakov.qa.booking.config.BookingConfig;
import ru.bulgakov.qa.booking.dto.AuthRequest;
import ru.bulgakov.qa.booking.dto.AuthResponse;
import ru.bulgakov.qa.booking.dto.BookingDTO;

import static io.restassured.RestAssured.given;
import static ru.bulgakov.qa.booking.config.BookingAPIConfig.getBookingConfig;

public class BookingAPIClient {
    private static final String BOOKING_URL = "https://restful-booker.herokuapp.com";
    private static final BookingConfig CFG = getBookingConfig();

    public Response auth(String user, String password) {
        return given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest(user, password))
                .post(BOOKING_URL + "/auth")
                .then()
                .extract().response();
    }

    public Response createBooking(BookingDTO bookingDTO) {
        return given()
                .contentType(ContentType.JSON)
                .body(bookingDTO)
                .post(BOOKING_URL + "/booking")
                .then()
                .extract().response();
    }

    public Response updateBooking(BookingDTO bookingDTO, Integer id) {
        return given()
                .cookie("token", getToken())
                .contentType(ContentType.JSON)
                .body(bookingDTO)
                .pathParam("BOOKING_ID", id)
                .put(BOOKING_URL + "/booking/{BOOKING_ID}")
                .then()
                .extract().response();
    }

    private String getToken() {
        return auth(CFG.username(), CFG.password()).as(AuthResponse.class).getToken();
    }
}
