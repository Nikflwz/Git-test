package ru.bulgakov.qa.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class YandexSearchResultsPage {

    private final SelenideElement closePopup =
            $(".DistributionButtonClose_view_button");

    public YandexSearchResultsPage closeDefaultBrowserSelectWindow() {
        try {
            closePopup
                    .shouldBe(Condition.visible, Duration.ofSeconds(3))
                    .click();
        } catch (ElementNotFound e) {
            // Попап не появился — продолжаем тест
        }

        return this;
    }

    public YandexSearchResultsPage openLink(String webSiteName) {
        $$("a[href='https://" + webSiteName + "/']")
                .findBy(Condition.visible)
                .scrollIntoView("{block: 'center'}")
                .click();

        return this;
    }
}