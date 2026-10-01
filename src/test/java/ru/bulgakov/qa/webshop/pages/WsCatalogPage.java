package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

public class WsCatalogPage {

    private final ElementsCollection productCards = $$("div.product-grid div");

    @Step("Открыть товар по индексу {index}")
    public WsProductPage openProduct(int index) {
        productCards.shouldBe(sizeGreaterThan(index));
        productCards.get(index).click();
        return new WsProductPage();
    }

    @Step("Открыть товар с названием '{name}'")
    public WsProductPage openProduct(String name) {
        productCards.findBy(text(name)).click();
        return new WsProductPage();
    }
}
