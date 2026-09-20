package ru.bulgakov.qa.pages;

import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.switchTo;

public abstract class BasePage {
    public <T> T switchToWindow(int index, Class<T> pageClass) {
        switchTo().window(index);
        return page(pageClass);
    }


}
