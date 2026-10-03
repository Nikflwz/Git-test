package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class WsProductPage extends BasePage {
    private final SelenideElement itemPrice = $("[itemprop=price]");
    private final SelenideElement itemName = $("[itemprop=name]");
    private final SelenideElement attributes = $("div.attributes");
    private final SelenideElement quantityInput = $("input.qty-input");
    private final SelenideElement addToCartButton = $("input.add-to-cart-button");
    private final SelenideElement successNotification = $("div.bar-notification.success");
    private final SelenideElement notificationText = successNotification.$("p.content");

    private ElementsCollection attributeOptions(String groupTitle) {
        return attributes.$$("dl dt")
                .findBy(text(groupTitle))
                .sibling(0)          // dt → соответствующий dd
                .$$("li");
    }

    @Step("Выбрать процессор: {processor}")
    public WsProductPage selectProcessor(String processor) {
        attributeOptions("Processor")
                .findBy(text(processor))
                .$("input")
                .click();
        return this;
    }

    @Step("Прочитать имя товара")
    public String getItemName() {
        return itemName.getText();
    }

    @Step("Прочитать цену товара")
    public String getItemPrice() {
        return itemPrice.getText();
    }

    @Step("Установить количество: {quantity}")
    public WsProductPage setQuantity(String quantity) {
        quantityInput.setValue(quantity);
        return this;
    }

    @Step("Добавить товар в корзину")
    public WsProductPage addToCart() {
        addToCartButton.click();
        return this;
    }

    @Step("Проверить уведомление о добавлении")
    public WsProductPage checkItemAddedToCart() {
        successNotification.shouldBe(visible);
        notificationText.shouldHave(text("The product has been added to your"));
        return this;
    }
}

