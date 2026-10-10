package ru.bulgakov.qa.booking.config;

import org.aeonbits.owner.Config;

@org.aeonbits.owner.Config.LoadPolicy(Config.LoadType.MERGE)
@org.aeonbits.owner.Config.Sources({
        "system:properties",
        "classpath:config/booking.properties"
})

public interface BookingConfig extends Config {

    String username();
    String password();

    String bookingUrl();
}
