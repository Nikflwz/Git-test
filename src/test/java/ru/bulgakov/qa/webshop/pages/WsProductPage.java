package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.bulgakov.qa.webshop.dto.CartExpectation;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class WsProductPage {

    private final ElementsCollection processorOptions = $$("dl dd ul li input[type='radio']");

    private final SelenideElement itemPrice = $("[itemprop=price]");
    private final SelenideElement itemName = $("[itemprop=name]");
    private final SelenideElement quantityInput = $("input.qty-input");
    private final SelenideElement addToCartButton = $("input.add-to-cart-button");
    private final SelenideElement successNotification = $("div.bar-notification.success");
    private final SelenideElement notificationText = $("p.content");
    private final SelenideElement cartQuantity = $("span.cart-qty");
    private final SelenideElement cartLink = $("a.ico-cart");

    public enum Processor {
        SLOW(0),
        MEDIUM(1),
        FAST(2);

        private final int index;

        Processor(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    @Step("Выбрать процессор с индексом {index}")
    public WsProductPage selectProcessor(int index) {
        processorOptions.shouldBe(sizeGreaterThan(index));
        processorOptions.get(index).click();
        return this;
    }

    @Step("Выбрать процессор: {processor}")
    public WsProductPage selectProcessor(Processor processor) {
        return selectProcessor(processor.getIndex());
    }

    @Step("Прочитать название товара")
    public String getItemName() {
        return itemName.getText();
    }

    @Step("Прочитать цену товара")
    public String getItemPrice() {
        return itemPrice.getText();
    }

    @Step("Посчитать итоговую сумму для количества {quantity}")
    public String calculateSubtotal(String quantity) {
        float price = Float.parseFloat(getItemPrice());
        float qty = Float.parseFloat(quantity);
        return String.valueOf(price * qty);
    }

    @Step("Запомнить ожидание корзины для количества {quantity}")
    public CartExpectation rememberCartExpectation(String quantity) {
        return new CartExpectation(getItemName(), quantity, calculateSubtotal(quantity));
    }

    @Step("Установить количество: {quantity}")
    public WsProductPage setQuantity(String quantity) {
        quantityInput.setValue(quantity);
        return this;

    }

    @Step("Нажать 'Add to cart'")
    public WsProductPage addToCart() {
        addToCartButton.click();
        return this;
    }

    @Step("Проверить уведомление о добавлении товара")
    public WsProductPage checkItemAddedToCart() {
        successNotification.shouldBe(visible);
        notificationText.shouldHave(text("The product has been added to your"));
        return this;
    }

    @Step("Проверить, что счётчик корзины показывает ({expectedQuantity})")
    public WsProductPage checkCartQuantity(String expectedQuantity) {
        cartQuantity.shouldHave(text("(" + expectedQuantity + ")"));
        return this;
    }

    @Step("Открыть корзину")
    public WsCartPage openCart() {
        successNotification.should(disappear);
        cartLink.click();
        return new WsCartPage();
    }
}

