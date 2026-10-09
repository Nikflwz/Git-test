package ru.bulgakov.qa.booking.config;

import org.aeonbits.owner.ConfigFactory;
import ru.bulgakov.qa.webshop.config.WebDriverConfig;

public class BookingAPIConfig {

    private static final BookingConfig config = ConfigFactory.create(BookingConfig.class, System.getProperties());

    public static BookingConfig getBookingConfig() {
        return config;
    }
}
