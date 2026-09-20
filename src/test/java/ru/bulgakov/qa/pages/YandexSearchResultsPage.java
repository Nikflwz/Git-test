package ru.bulgakov.qa.pages;


import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class YandexSearchResultsPage extends BasePage {

    private static final Duration BANNER_TIMEOUT = Duration.ofSeconds(3);
    private final SelenideElement distributionBannerClose = $(".DistributionButtonClose_view_button");
    private final SelenideElement wikiArticleLink = $("a[href*='ru.wikipedia.org/wiki/GitHub']");

    public YandexSearchResultsPage closeDefaultBrowserBannerIfAppeared() {
        if (distributionBannerClose.is(visible, BANNER_TIMEOUT)) {
            distributionBannerClose.click();
        }
        return this;
    }

    public YandexSearchResultsPage openLink(String host) {
        $$("a[href*='" + host + "']").filterBy(visible).first().click();
        return this;

    }

    public YandexSearchResultsPage openWikiArticle() {
        wikiArticleLink.click();
        return this;
    }
}