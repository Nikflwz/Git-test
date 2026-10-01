package ru.bulgakov.qa.webshop.pages;


import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.switchTo;


public abstract class BasePage {
    @Step("Переключиться на окно {index} и вернуть страницу {pageClass}")
    public <T> T switchToWindow(int index, Class<T> pageClass) {
        switchTo().window(index);
        return page(pageClass);
    }


}
