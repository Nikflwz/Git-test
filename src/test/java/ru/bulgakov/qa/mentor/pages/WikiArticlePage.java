package ru.bulgakov.qa.mentor.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class WikiArticlePage extends BasePage {

    private final SelenideElement title = $("#firstHeading"),
            articleContent = $("#mw-content-text");

    @Step("Проверить заголовок статьи: {expectedTitle}")
    public WikiArticlePage checkTitle(String expectedTitle) {
        title.shouldHave(text(expectedTitle));
        return this;
    }

    @Step("Проверить, что статья содержит текст: {expectedText}")
    public WikiArticlePage checkArticleContains(String expectedText) {
        articleContent.shouldHave(text(expectedText));
        return this;
    }
}