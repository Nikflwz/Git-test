package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
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

    public WsLoginPage enterEmail(String email) {
        inputEmail.setValue(email);
        return this;
    }

    public WsLoginPage enterPassword(String password) {
        inputPassword.setValue(password);
        return this;
    }

    public WsLoginPage checkRememberMe() {
        rememberMeCheckBox.click();
        return this;
    }

    public WsWelcomePage submitLogin() {
        loginButton.click();
        return new WsWelcomePage();
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
