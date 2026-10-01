package ru.bulgakov.qa.webshop.test;

import io.qameta.allure.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.bulgakov.qa.webshop.pages.WsLoginPage;
import ru.bulgakov.qa.webshop.pages.WsRegistrationPage;
import ru.bulgakov.qa.webshop.pages.WsWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.qa.webshop.config.Config.*;

@Epic("Авторизация")
@Feature("Вход в систему")
@Owner("n.nikflwz")
public class LoginTest extends TestBase {

    private static final Faker faker = new Faker();
    private String email;
    private String password;

    @Nested
    @DisplayName("Позитивные сценарии входа")
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
    @Story("Успешный вход с валидными данными")
    @DisplayName("Успешный вход зарегистрированного пользователя")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "TASK-121")
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

    @ParameterizedTest(name = "Авторизация с невалидным email {0}")
    @CsvFileSource(resources = "/email.csv")
    @Story("Валидация email при входе")
    @DisplayName("Вход с невалидным email показывает ошибку валидации")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "TASK-122")
    void invalidEmailLoginTest(String email) {
        open(WEB_SHOP_LOGIN_URL, WsLoginPage.class)
                .enterEmail(email)
                .enterPassword("password")
                .verifyEmailValidationErrorAppear()
                .submitLogin();
    }
}
