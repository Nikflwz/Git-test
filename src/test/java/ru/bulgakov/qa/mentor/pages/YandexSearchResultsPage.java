package ru.bulgakov.qa.mentor.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class YandexSearchResultsPage extends BasePage {

    private static final Duration BANNER_TIMEOUT = Duration.ofSeconds(3);
    private final SelenideElement distributionBannerClose = $(".DistributionButtonClose_view_button");
    private final SelenideElement wikiArticleLink = $("a[href*='ru.wikipedia.org/wiki/GitHub']");

    @Step("Закрыть баннер с предложением браузера (если появился)")
    public YandexSearchResultsPage closeDefaultBrowserBannerIfAppeared() {
        if (distributionBannerClose.is(visible, BANNER_TIMEOUT)) {
            distributionBannerClose.click();
        }
        return this;
    }

    @Step("Открыть ссылку на хост: {host}")
    public YandexSearchResultsPage openLink(String host) {
        $$("a[href*='" + host + "']").filterBy(visible).first().click();
        return this;
    }

    @Step("Открыть статью в Википедии")
    public YandexSearchResultsPage openWikiArticle() {
        wikiArticleLink.click();
        return this;
    }
}