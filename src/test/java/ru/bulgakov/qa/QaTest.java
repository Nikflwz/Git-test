package ru.bulgakov.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ex.ElementNotFound;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.pages.*;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class QaTest {

    @Test
    void mentoringPriceShouldBe47000Test() {

        Configuration.timeout = 10000;
        YandexSearchPage yaSearch = new YandexSearchPage();
        YandexSearchResultsPage yaSearchResults = new YandexSearchResultsPage();
        WelcomePage welcomePage = new WelcomePage();
        PaymentPage paymentPage = new PaymentPage();
        VerifyPrice verifyPrice = new VerifyPrice();

        open("https://ya.ru/");

        yaSearch
                .search("ivanbulgakovqa")
                .submit();

        yaSearchResults
                .closeDefaultBrowserSelectWindow()
                .openLink("ivanbulgakovqa.ru");

        switchTo().window(1);

        welcomePage
                .clickPrice();

        paymentPage
                .clickWantToStartQA()
                .clickPay();

        switchTo().window(2);

        verifyPrice
                .checkPrice("47 000.00");
    }
    @Test
    void findGitWiki() {
        /*
        * 1. Открыть браузер
        * 2. Ввести данные сайта (GitHub)
        * 3. Нажать кнопку поиск
        * 4. В поисковой выдаче найти нужный сайт и кликнуть на него (GitHub Wiki)
        * 5. Нажать кнопку "Ссылки"
        * 6. Проверить что есть ссылка с надписью "GitHub.com"
        * 7. Открыть браузер
        * */

        Configuration.holdBrowserOpen = true;

        open("https://ya.ru/");
        $("#text").setValue("github");
        $("[type=submit]").click();

        try {
            $(".DistributionButtonClose_view_button")
                    .shouldBe(Condition.visible, Duration.ofSeconds(3))
                    .click();
        } catch (ElementNotFound e) {
            // Попап не появился
        }

        $$("a[href='https://ru.wikipedia.org/wiki/GitHub']")
                .findBy(Condition.visible)
                .scrollIntoView("{block: 'center'}")
                .click();

        switchTo().window(1);
        $("#toc-Ссылки").click();
        $("#mwAsE").shouldHave(Condition.text("GitHub.com"));
    }

}

