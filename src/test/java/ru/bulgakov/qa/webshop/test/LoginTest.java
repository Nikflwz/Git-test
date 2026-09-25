package ru.bulgakov.qa.webshop.test;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.webshop.pages.WsRegistrationPage;
import ru.bulgakov.qa.webshop.pages.WsWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.qa.webshop.config.Config.WEB_SHOP_REGISTRATION_URL;
import static ru.bulgakov.qa.webshop.config.Config.WEB_SHOP_URL;

public class LoginTest {

    private static final Faker faker = new Faker();
    private String email;
    private String password;


    @BeforeEach
        void beforeAll() {

            password = faker.name().fullName();
            email = faker.internet().emailAddress();

            open(WEB_SHOP_REGISTRATION_URL, WsRegistrationPage.class)
                    .register(
                            faker.name().firstName(),
                            faker.name().lastName(),
                            email,
                            password)
                    .checkUserLoggedIn(email);

            clearBrowserCookies();
            clearBrowserLocalStorage();
        }

    @Test
    void successLoginTest() {

        open(WEB_SHOP_URL, WsWelcomePage.class)
                .openLogin()
                .checkLoginPageOpened()
                .enterEmail(email)
                .enterPassword(password)
                .checkRememberMe()
                .submitLogin();

    }
}
