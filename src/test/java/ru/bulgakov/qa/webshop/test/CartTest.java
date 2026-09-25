package ru.bulgakov.qa.webshop.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.webshop.dto.CartExpectation;
import ru.bulgakov.qa.webshop.pages.WsProductPage;
import ru.bulgakov.qa.webshop.pages.WsWelcomePage;
import ru.bulgakov.qa.webshop.steps.AuthSteps;

import static com.codeborne.selenide.Selenide.open;
import static ru.bulgakov.qa.webshop.config.Config.WEB_SHOP_URL;

public class CartTest {
    private final AuthSteps authSteps = new AuthSteps();

    @BeforeEach
    void beforeEach() {
        authSteps.registerNewUser();
    }

    @Test
    void addItemToCartTest() {
        String quantity = "2";
        String productName = "0";

        WsProductPage productPage = open(WEB_SHOP_URL, WsWelcomePage.class)
                .openDesktops()
                .openProduct(productName)
                .selectProcessor(WsProductPage.Processor.SLOW)
                .setQuantity(quantity)
                .addToCart()
                .checkItemAddedToCart()
                .checkCartQuantity(quantity);

        CartExpectation expected = productPage.rememberCartExpectation(quantity);

        productPage.openCart()
                .checkMatches(expected);
    }
}
