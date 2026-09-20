package ru.bulgakov.qa;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.pages.PaymentPage;
import ru.bulgakov.qa.pages.WelcomePage;
import ru.bulgakov.qa.pages.WikiArticlePage;
import ru.bulgakov.qa.pages.YandexSearchPage;

import static com.codeborne.selenide.Selenide.open;

public class QaTest {

    private static final String YANDEX_URL = "https://ya.ru/";
    private static final String COURSE_HOST = "ivanbulgakovqa.ru";
    private static final String EXPECTED_PRICE = "47 000.00";

    @BeforeAll
    static void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadTimeout = 30000;
        Configuration.timeout = 10000;
    }

    @Test
    void coursePriceShouldBe47000Test() {

        open(YANDEX_URL, YandexSearchPage.class)
                .search("ivanbulgakovqa")
                .closeDefaultBrowserBannerIfAppeared()
                .openLink(COURSE_HOST)
                .switchToWindow(1, WelcomePage.class)
                .openCostSection()
                .clickWantToQa()
                .clickRunToPay()
                .switchToWindow(2, PaymentPage.class)
                .checkPriceAmount(EXPECTED_PRICE);
    }

    @Test
    void githubArticleShouldOpenFromYandexTest() {
        open(YANDEX_URL, YandexSearchPage.class)
                .search("github")
                .closeDefaultBrowserBannerIfAppeared()
                .openWikiArticle()
                .switchToWindow(1, WikiArticlePage.class)
                .checkTitle("GitHub")
                .checkArticleContains("GitHub.com");
    }

}

