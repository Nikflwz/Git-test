package ru.bulgakov.qa.booking;

import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.booking.config.BookingConfig;
import ru.bulgakov.qa.booking.dto.AuthResponse;
import ru.bulgakov.qa.booking.dto.BookingDTO;
import ru.bulgakov.qa.booking.dto.CreateBookingResponse;
import ru.bulgakov.qa.booking.steps.BookingSteps;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bulgakov.qa.booking.config.BookingAPIConfig.getBookingConfig;
import static ru.bulgakov.qa.booking.steps.BookingSteps.buildBookingRequest;

public class BookingTest extends BaseApiTest {
    private static final Faker faker = new Faker();
    private static final BookingConfig CFG = getBookingConfig();
    private final BookingApiClient bookingClient = new BookingApiClient();

    @Test
    void authTest() {
        Response resp = bookingClient.auth(CFG.username(), CFG.password());

        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.as(AuthResponse.class).getToken()).isNotNull();
    }

    @Test
    void createBookingTest() {
        BookingDTO bookingDTO = buildBookingRequest();
        Response resp = bookingClient.createBooking(bookingDTO);
        assertThat(resp.getStatusCode()).isEqualTo(200);

        CreateBookingResponse createBookingResponse = resp.as(CreateBookingResponse.class);
        assertThat(createBookingResponse.getBookingid()).isNotNull();
        BookingSteps.bookingsShouldBeEqual(bookingDTO, createBookingResponse.getBooking());
    }

    @Test
    void updateBookingTest() {
        Response createResp = bookingClient.createBooking(buildBookingRequest());
        assertThat(createResp.getStatusCode()).isEqualTo(200);

        BookingDTO bookingDTO = buildBookingRequest();
        Response updateResponse = bookingClient
                .updateBooking(bookingDTO, createResp.as(CreateBookingResponse.class).getBookingid());

        assertThat(updateResponse.getStatusCode()).isEqualTo(200);

        BookingDTO updatedBookingDto = updateResponse.as(BookingDTO.class);
        BookingSteps.bookingsShouldBeEqual(bookingDTO, updatedBookingDto);
    }

    @Test
    void partialUpdateBookingTest() {
        Response createResp = bookingClient.createBooking(buildBookingRequest());
        assertThat(createResp.getStatusCode()).isEqualTo(200);

        BookingDTO bookingDTO = new BookingDTO
                (
                        faker.name().firstName(),
                        faker.number().numberBetween(10001, 12000),
                        "2000-02-22"
                );

        Response updateResponse = bookingClient
                .partialUpdateBooking(bookingDTO, createResp.as(CreateBookingResponse.class).getBookingid());

        assertThat(updateResponse.getStatusCode()).isEqualTo(200);

        BookingDTO updatedBookingDto = updateResponse.as(BookingDTO.class);

        assertThat(bookingDTO.getFirstname()).isEqualTo(updatedBookingDto.getFirstname());
        assertThat(bookingDTO.getTotalprice()).isEqualTo(updatedBookingDto.getTotalprice());
        assertThat(bookingDTO.getBookingdates().getCheckin()).isEqualTo(updatedBookingDto.getBookingdates().getCheckin());

    }

    @Test
    void deleteBookingTest() {
        Integer bookingId = bookingClient
                .createBooking(buildBookingRequest()).as(CreateBookingResponse.class).getBookingid();

        Response deleteResp = bookingClient.deleteBooking(bookingId);
        assertThat(deleteResp.getStatusCode()).isEqualTo(201);

        Response getResp = bookingClient.getBooking(bookingId);
        assertThat(getResp.getStatusCode()).isEqualTo(404);


    }

}
