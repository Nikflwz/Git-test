package ru.bulgakov.qa.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.bulgakov.qa.webshop.dto.CartExpectation;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
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

    public WsProductPage selectProcessor(Processor processor) {
        int index = processor.getIndex();
        processorOptions.shouldBe(sizeGreaterThan(index));
        processorOptions.get(index).click();
        return this;
    }

    public String getItemName() {
        return itemName.getText();
    }

    public String getItemPrice() {
        return itemPrice.getText();
    }

    public String calculateSubtotal(String quantity) {
        float price = Float.parseFloat(getItemPrice());
        float qty = Float.parseFloat(quantity);
        return String.valueOf(price * qty);
    }

    public CartExpectation rememberCartExpectation(String quantity) {
        return new CartExpectation(getItemName(), quantity, calculateSubtotal(quantity));
    }

    public WsProductPage setQuantity(String quantity) {
        quantityInput.setValue(quantity);
        return this;

    }

    public WsProductPage addToCart() {
        addToCartButton.click();
        return this;
    }

    public WsProductPage checkItemAddedToCart() {
        successNotification.shouldBe(visible);
        notificationText.shouldHave(text("The product has been added to your"));
        return this;
    }

    public WsProductPage checkCartQuantity(String expectedQuantity) {
        cartQuantity.shouldHave(text("(" + expectedQuantity + ")"));
        return this;
    }


    public WsCartPage openCart() {
        cartLink.click();
        return new WsCartPage();
    }
}

