package ru.bulgakov.qa.webshop.pages;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public abstract class BasePage {

    @Step("Переключиться на окно {index} и вернуть страницу {pageClass}")
    public <T> T switchToWindow(int index, Class<T> pageClass) {
        switchTo().window(index);
        return page(pageClass);
    }

    private final ElementsCollection headerLinks = $$("div.header-links ul li a");

    private final SelenideElement registrationButton = $("a.ico-register");
    private final SelenideElement loginButton = $("a.ico-login");
    private final SelenideElement computerMenu = $$("ul.top-menu li a").get(1);
    private final SelenideElement desktopsLink = $(byText("Desktops"));
    private final SelenideElement cartQuantityBadge = $("a.ico-cart span.cart-qty");
    private final SelenideElement cartLink = $("a.ico-cart");
    private final SelenideElement barNotification = $("div#bar-notification");

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

    @Step("Открыть каталог Desktops")
    public WsCatalogPage openDesktops() {
        computerMenu.hover();
        desktopsLink.click();
        return new WsCatalogPage();
    }

    @Step("Проверить счётчик корзины: ({expectedQuantity})")
    public BasePage checkCartQuantity(String expectedQuantity) {
        cartQuantityBadge.shouldHave(exactText("(" + expectedQuantity + ")"));
        return this;
    }

    @Step("Открыть корзину")
    public WsCartPage openCart() {
        barNotification.should(disappear, Duration.ofSeconds(6));
        cartLink.click();
        return new WsCartPage();
    }

    @Step("Проверить, что пользователь {email} залогинен")
    public BasePage checkUserLoggedIn(String email) {
        headerLinks.get(0).shouldHave(text(email));
        return this;
    }

}
