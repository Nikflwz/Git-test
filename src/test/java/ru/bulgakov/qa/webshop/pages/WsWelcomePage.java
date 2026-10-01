package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WsWelcomePage {
    private final SelenideElement registrationButton = $("a.ico-register");
    private final SelenideElement loginButton = $("a.ico-login");
    private final SelenideElement computerMenu = $$("ul.top-menu li a").get(1);
    private final SelenideElement desktopsLink = $(byText("Desktops"));


    @Step("Открыть страницу регистрации")
    public WsRegistrationPage openRegistration() {
        registrationButton.click();
        return new WsRegistrationPage();
    }

    @Step("Открыть страницу логина")
    public WsLoginPage openLogin() {
        loginButton.click();
        return new WsLoginPage();
    }

    @Step("Открыть каталог Desktops через меню")
    public WsCatalogPage openDesktops() {
        computerMenu.hover();
        desktopsLink.click();
        return new WsCatalogPage();
    }


}
