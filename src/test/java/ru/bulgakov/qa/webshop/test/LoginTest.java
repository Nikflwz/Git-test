package ru.bulgakov.qa.webshop.test;

import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.bulgakov.qa.webshop.pages.WsLoginPage;
import ru.bulgakov.qa.webshop.pages.WsRegistrationPage;
import ru.bulgakov.qa.webshop.pages.WsWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.qa.webshop.config.Config.*;

public class LoginTest extends TestBase {

    private static final Faker faker = new Faker();
    private String email;
    private String password;

    @Nested
public class PositiveTests {

    @BeforeEach
    void beforeEach() {

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
    @Tags({@Tag("UI"), @Tag("positive")})
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

    @Tags({@Tag("UI"), @Tag("negative")})
    @ParameterizedTest(name = "Авторизация с невалидным email {0}")
    @CsvFileSource(resources = "/email.csv")
    void invalidEmailLoginTest(String email) {
        open(WEB_SHOP_LOGIN_URL, WsLoginPage.class)
                .enterEmail(email)
                .enterPassword("password")
                .verifyEmailValidationErrorAppear()
                .submitLogin();
    }
}
