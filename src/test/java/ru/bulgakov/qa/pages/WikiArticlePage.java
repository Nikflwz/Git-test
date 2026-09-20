package ru.bulgakov.qa.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class WikiArticlePage extends BasePage {
    private final SelenideElement title = $("#firstHeading"),
            articleContent = $("#mw-content-text");

    public WikiArticlePage checkTitle(String expectedTitle) {
        title.shouldHave(text(expectedTitle));
        return this;
    }

    public WikiArticlePage checkArticleContains(String expectedText) {
        articleContent.shouldHave(text(expectedText));
        return this;
    }
}

