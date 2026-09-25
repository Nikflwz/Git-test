package ru.bulgakov.qa.mentor.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage extends BasePage {

    private final SelenideElement priceAmount = $("[class*='price']");

    public PaymentPage checkPriceAmount(String expectedAmount) {
        priceAmount.shouldHave(text(expectedAmount));
        return this;
    }
}
