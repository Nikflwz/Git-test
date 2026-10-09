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
import ru.bulgakov.qa.booking.dto.AuthRequest;
import ru.bulgakov.qa.booking.dto.AuthResponse;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthTest {

    private static final String BOOKING_URL = "https://restful-booker.herokuapp.com";

    @BeforeAll
    static void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    @Tag("positive")
    void authTest() {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest("admin", "password123"))
                .post(BOOKING_URL + "/auth")
                .then()
                .extract().response();

        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.as(AuthResponse.class).getToken()).isNotNull();
    }

    static Stream<Arguments> invalidAuthData() {
        return Stream.of(
                Arguments.of("Неверный пароль", new AuthRequest("admin", "wrong-password"), 200),
                Arguments.of("Неверный логин", new AuthRequest("wrong-user", "password123"), 200),
                Arguments.of("Пустой пароль", new AuthRequest("admin", ""), 200),
                Arguments.of("Пустой логин", new AuthRequest("", "password123"), 200),
                Arguments.of("Пустое body {}", new AuthRequest(null, null), 200)
        );
    }

    @ParameterizedTest(name = "Авторизация: {0}")
    @MethodSource("invalidAuthData")
    @Tag("negative")
    void authNegativeTest(String scenario, AuthRequest request, int expectedStatus) {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(request)
                .post(BOOKING_URL + "/auth")
                .then()
                .extract().response();

        assertThat(resp.statusCode())
                .as("Статус-код для сценария: " + scenario)
                .isEqualTo(expectedStatus);

        assertThat(resp.as(AuthResponse.class).getToken())
                .as("Токен не должен вернуться для сценария: " + scenario)
                .isNull();
    }

    @Test
    @Tag("negative")
    void authWithoutBodyTest() {
        Response resp = given()
                .contentType(ContentType.JSON)
                .post(BOOKING_URL + "/auth")
                .then()
                .extract().response();

        assertThat(resp.statusCode())
                .as("Статус-код для запроса без body")
                .isEqualTo(200);

        assertThat(resp.body().asString())
                .as("В теле не должно быть токена")
                .doesNotContain("\"token\"");
    }
}