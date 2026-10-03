package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;

public class WsCatalogPage extends BasePage {

    private final ElementsCollection productLinks = $$("h2.product-title a");

    @Step("Открыть товар '{name}'")
    public WsProductPage openProduct(String name) {
        productLinks.findBy(exactText(name)).click();
        return new WsProductPage();
    }
}
