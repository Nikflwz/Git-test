package ru.bulgakov.qa.mentor.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class YandexSearchPage extends BasePage {

    private final SelenideElement searchInput = $("#text"),
            submitButton = $("[type=submit]");

    @Step("Ввести поисковый запрос: {query}")
    public YandexSearchPage setSearchQuery(String query) {
        searchInput.setValue(query);
        return this;
    }

    @Step("Найти в Яндексе: {query}")
    public YandexSearchResultsPage search(String query) {
        return setSearchQuery(query).submit();
    }

    @Step("Нажать кнопку поиска")
    public YandexSearchResultsPage submit() {
        submitButton.click();
        return page(YandexSearchResultsPage.class);
    }
}