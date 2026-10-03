package ru.bulgakov.qa.webshop.test;

import io.qameta.allure.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.webshop.pages.WsWelcomePage;

import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static ru.bulgakov.qa.webshop.config.Config.WEB_SHOP_URL;


public class RegistrationTest extends TestBase {

    private static final Faker faker = new Faker();

    @Test
    @Owner("n.nikflwz")
    @Tag("positive")
    @Severity(CRITICAL)
    @Epic("Авторизация")
    @Feature("Регистрация")
    @Story("Регистрация нового пользователя")
    @Link(name = "TASK-120", url = "https://example.com/TASK-120")
    @DisplayName("Успешная регистрация нового пользователя")
    @Description("Создаём нового пользователя через интерфейс со случайными данными")
    void registrationTest() {
        String password = faker.internet().password();
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
