package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class WsCartPage extends BasePage {

    private final SelenideElement productName = $("a.product-name");
    private final SelenideElement productUnitPrice = $("span.product-unit-price");
    private final SelenideElement quantityInput = $("input.qty-input");
    private final SelenideElement productSubtotal = $("span.product-subtotal");

    @Step("Прочитать имя товара в корзине")
    public String getItemName() {
        return productName.getText();
    }

    @Step("Прочитать количество товара")
    public String getQuantity() {
        return quantityInput.getValue();
    }

    @Step("Прочитать цену за штуку")
    public String getUnitPrice() {
        return productUnitPrice.getText();
    }

    @Step("Прочитать subtotal")
    public String getSubtotal() {
        return productSubtotal.getText();
    }
}
