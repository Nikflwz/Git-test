package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

public class WsCatalogPage {

    private final ElementsCollection productCards = $$("div.product-grid div");

    public WsProductPage openProduct(int index) {
        productCards.shouldBe(sizeGreaterThan(index));
        productCards.get(index).click();
        return new WsProductPage();
    }

    public WsProductPage openProduct(String name) {
        productCards.findBy(text(name)).click();
        return new WsProductPage();
    }
}
