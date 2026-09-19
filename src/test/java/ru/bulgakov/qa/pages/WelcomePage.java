package ru.bulgakov.qa.pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.*;

public class WelcomePage {

    public WelcomePage clickPrice() {
        $$(".t-menu__list li")
                .findBy(Condition.text("Стоимость"))
                .click();

        return this;
    }
}