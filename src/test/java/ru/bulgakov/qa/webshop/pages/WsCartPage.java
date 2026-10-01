package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.SelenideElement;
import ru.bulgakov.qa.webshop.dto.CartExpectation;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class WsCartPage {
    private final SelenideElement productName = $("a.product-name");
    private final SelenideElement quantityInput = $("input.qty-input");
    private final SelenideElement productSubtotal = $("span.product-subtotal");

    public WsCartPage checkProductName(String expectedName) {
        productName.shouldHave(text(expectedName));
        return this;
    }

    public WsCartPage checkQuantity(String expectedQuantity) {
        quantityInput.shouldHave(attribute("value", expectedQuantity));
        return this;
    }

    public WsCartPage checkSubtotal(String expectedSubtotal) {
        productSubtotal.shouldHave(text(expectedSubtotal));
        return this;
    }

    public WsCartPage checkMatches(CartExpectation expected) {
        checkProductName(expected.productName());
        checkQuantity(expected.quantity());
        checkSubtotal(expected.subtotal());
        return this;
    }
}
