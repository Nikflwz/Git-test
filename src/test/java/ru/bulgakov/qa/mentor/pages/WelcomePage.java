package ru.bulgakov.qa.mentor.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class WelcomePage extends BasePage {

    private final SelenideElement costMenuLink = $("a.t-menu__link-item[href='#cost']"),
            wantToQaButton = $(byText("Хочу вкатиться в QA")),
            runToPayButton = $(byText("Бегу оплачивать"));

    public WelcomePage openCostSection() {
        costMenuLink.click();
        return this;
    }

    public WelcomePage clickWantToQa() {
        wantToQaButton.click();
        return this;
    }

    public WelcomePage clickRunToPay() {
        runToPayButton.click();
        return this;
    }

}