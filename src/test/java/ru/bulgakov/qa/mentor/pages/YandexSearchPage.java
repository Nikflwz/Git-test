package ru.bulgakov.qa.mentor.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class YandexSearchPage extends BasePage {

    private final SelenideElement searchInput = $("#text"),
            submitButton = $("[type=submit]");

    public YandexSearchPage setSearchQuery(String query) {
        searchInput.setValue(query);
        return this;
    }

    public YandexSearchResultsPage search(String query) {
        return setSearchQuery(query).submit();
    }

    public YandexSearchResultsPage submit() {
        submitButton.click();
        return page(YandexSearchResultsPage.class);
    }
}
