package ru.bulgakov.qa.mentor.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class WelcomePage extends BasePage {

    private final SelenideElement costMenuLink = $("a.t-menu__link-item[href='#cost']"),
            wantToQaButton = $(byText("Хочу вкатиться в QA")),
            runToPayButton = $(byText("Бегу оплачивать"));

    @Step("Открыть раздел 'Стоимость'")
    public WelcomePage openCostSection() {
        costMenuLink.click();
        return this;
    }

    @Step("Нажать кнопку 'Хочу вкатиться в QA'")
    public WelcomePage clickWantToQa() {
        wantToQaButton.click();
        return this;
    }

    @Step("Нажать кнопку 'Бегу оплачивать'")
    public WelcomePage clickRunToPay() {
        runToPayButton.click();
        return this;
    }
}