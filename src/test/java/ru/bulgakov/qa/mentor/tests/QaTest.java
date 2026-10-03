package ru.bulgakov.qa.mentor.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.mentor.pages.PaymentPage;
import ru.bulgakov.qa.mentor.pages.WelcomePage;
import ru.bulgakov.qa.mentor.pages.WikiArticlePage;
import ru.bulgakov.qa.mentor.pages.YandexSearchPage;
import ru.bulgakov.qa.webshop.test.TestBase;

import static com.codeborne.selenide.Selenide.open;

@Epic("Mentor tasks")
@Feature("Поиск и переходы между сайтами")
@Owner("n.nikflwz")
public class QaTest extends TestBase {

    private static final String YANDEX_URL = "https://ya.ru/";
    private static final String COURSE_HOST = "ivanbulgakovqa.ru";
    private static final String EXPECTED_PRICE = "47 000.00";

    @Test
    @Story("Стоимость курса Ивана Булгакова")
    @DisplayName("Цена курса ivanbulgakovqa.ru должна быть 47 000.00")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "TASK-140", url = "https://example.com/TASK-140")
    @Description("Через Яндекс ищем сайт курса, переходим в раздел стоимости и проверяем итоговую цену")
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
    @Story("Открытие статьи GitHub из Яндекса")
    @DisplayName("Из результатов поиска Яндекса открывается статья GitHub в Википедии")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "TASK-141")
    @Description("Ищем 'github' в Яндексе, переходим на статью в Википедии и проверяем заголовок и содержимое")
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