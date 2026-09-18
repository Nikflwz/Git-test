package ru.bulgakov.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class QaTest {

    @Test
    void mentoringPriceShouldBe47000Test() {
        /*
         * Тест-кейс - проверить, что предоплата за обучение 470000 рублей
         * 1. Открыть поисковик (Яндекс)
         * 2. Ввести данные сайта(bulgakov qa)
         * 3. Нажать кнопку поиск
         * 4. В поисковой выдаче найти нужный сайт и кликнуть на него
         * 5. Нажать кнопку "Стоимость"
         * 6. Нажать кнопку "Хочу вкатиться в QA"
         * 7. Нажать кнопку "Бегу оплачивать"
         * 8. Проверяем, что стоимость 47000 рублей.*/

        open("https://ya.ru/");
        $("#text").setValue("ivanbulgakovqa");
        $("[type=submit]").click();

        try {
            $(".DistributionButtonClose_view_button")
                    .shouldBe(Condition.visible, Duration.ofSeconds(3))
                    .click();
        } catch (ElementNotFound e) {
            // Попап не появился
        }

        $$("a[href='https://ivanbulgakovqa.ru/']")
                .findBy(Condition.visible)
                .scrollIntoView("{block: 'center'}")
                .click();
        sleep(1500);

        switchTo().window(1);
        $$(".t-menu__list li").last().click();
        $("[role=button]").click();
        $("#sbs-921114964-1741924491598").click();
        sleep(1500);

        switchTo().window(2);
        $("h3").shouldHave(Condition.text("47 000.00"));

    }

    @Test
    void findGitRepository() {

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

