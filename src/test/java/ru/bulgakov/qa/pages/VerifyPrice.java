package ru.bulgakov.qa.pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.*;

public class VerifyPrice {

    public VerifyPrice checkPrice(String expectedPrice) {
        $("h3").shouldHave(Condition.text(expectedPrice));

        return this;
    }
}