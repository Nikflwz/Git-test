package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class WsLoginPage {

    private final SelenideElement pageTitle = $("div.page-title h1");
    private final SelenideElement inputEmail = $("input#Email");
    private final SelenideElement inputPassword = $("input#Password");
    private final SelenideElement rememberMeCheckBox = $("input#RememberMe");
    private final SelenideElement loginButton = $("input.login-button");

    public WsLoginPage checkLoginPageOpened() {
        pageTitle.shouldHave(text("Welcome, Please Sign In!"));
        return this;
    }

    @Step("Ввести электронную почту: {email}")
    public WsLoginPage enterEmail(String email) {
        inputEmail.setValue(email);
        return this;
    }

    @Step("Ввести пароль: {password}")
    public WsLoginPage enterPassword(String password) {
        inputPassword.setValue(password);
        return this;
    }

    public WsLoginPage checkRememberMe() {
        rememberMeCheckBox.click();
        return this;
    }

    @Step("Подтвердить регистрацию")
    public WsWelcomePage submitLogin() {
        loginButton.click();
        return new WsWelcomePage();
    }

    @Step("Проверить, что появилось сообщение с ошибкой валидации почты")
    public WsLoginPage verifyEmailValidationErrorAppear() {
        $("span.field-validation-error").shouldBe(visible);
        return this;
    }

    public WsWelcomePage login(String email, String password, boolean rememberMe) {
        enterEmail(email)
                .enterPassword(password);

        if (rememberMe) {
            checkRememberMe();
        }

        return submitLogin();
    }
}
