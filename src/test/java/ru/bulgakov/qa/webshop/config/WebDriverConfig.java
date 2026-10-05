package ru.bulgakov.qa.webshop.config;

import org.aeonbits.owner.Config;

@org.aeonbits.owner.Config.LoadPolicy(Config.LoadType.MERGE)
@org.aeonbits.owner.Config.Sources({
        "system:properties",
        "classpath:config/${run}.properties"
})

public interface WebDriverConfig extends Config {

    @DefaultValue("local")
    String run();

    @DefaultValue("edge")
    String browser();

    String browserVersion();

    @DefaultValue("1920x1080")
    String browserSize();

    String selenoidUrl();

    String selenoidUser();

    String selenoidPassword();

    boolean enableVideo();

    boolean enableVNC();
}
