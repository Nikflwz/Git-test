package ru.bulgakov.qa.webshop.test;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.webshop.pages.WsWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.qa.webshop.config.Config.WEB_SHOP_URL;


public class RegistrationTest {

    private static final Faker faker = new Faker();

    @Test
    void registrationTest() {

        String password = faker.name().fullName();
        String email = faker.internet().emailAddress();


        open(WEB_SHOP_URL, WsWelcomePage.class)
                .openRegistration()
                .verifyRegistrationIsOpened()
                .selectMaleGender()
                .enterFirstName(faker.name().firstName())
                .enterLastName(faker.name().lastName())
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(password)
                .submitRegistration()
                .checkRegistrationCompleted()
                .checkUserLoggedIn(email);
    }
}
