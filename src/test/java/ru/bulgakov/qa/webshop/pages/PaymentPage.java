package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class PaymentPage extends BasePage {

    private final SelenideElement priceAmount = $x("//h3[@data-at='H3']");

    public PaymentPage checkPriceAmount(String expectedAmount) {
        priceAmount.shouldBe(visible, Duration.ofSeconds(10));
        priceAmount.shouldHave(text(expectedAmount));
        return this;
    }
}
