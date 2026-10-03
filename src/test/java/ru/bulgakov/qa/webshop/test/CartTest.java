package ru.bulgakov.qa.webshop.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.webshop.pages.WsCartPage;
import ru.bulgakov.qa.webshop.pages.WsProductPage;
import ru.bulgakov.qa.webshop.pages.WsWelcomePage;
import ru.bulgakov.qa.webshop.steps.AuthSteps;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bulgakov.qa.webshop.config.Config.WEB_SHOP_URL;

public class CartTest extends TestBase {

    private static final String PRODUCT_NAME = "Build your own cheap computer";

    private final AuthSteps authSteps = new AuthSteps();

    @BeforeEach
    void beforeEach() {
        authSteps.registerNewUser();
    }

    @Test
    void addItemToCartTest() {
        String processor = "Slow";
        String quantity = "2";

        WsProductPage productPage = open(WEB_SHOP_URL, WsWelcomePage.class)
                .openDesktops()
                .openProduct(PRODUCT_NAME);

        float expectedUnitPrice = Float.parseFloat(productPage.getItemPrice())
                + processorSurcharge(processor);

        WsCartPage cartPage = productPage
                .selectProcessor(processor)
                .setQuantity(quantity)
                .addToCart()
                .checkItemAddedToCart()
                .checkCartQuantity(quantity)
                .openCart();

        String expectedUnitPriceText = String.format(Locale.US, "%.2f", expectedUnitPrice);
        String expectedSubtotalText = String.format(Locale.US, "%.2f",
                expectedUnitPrice * Float.parseFloat(quantity));

        assertAll(
                () -> assertEquals(PRODUCT_NAME, cartPage.getItemName()),
                () -> assertEquals(quantity, cartPage.getQuantity()),
                () -> assertEquals(expectedUnitPriceText, cartPage.getUnitPrice()),
                () -> assertEquals(expectedSubtotalText, cartPage.getSubtotal())
        );
    }

    private float processorSurcharge(String processor) {
        return switch (processor) {
            case "Slow" -> 0f;
            case "Medium" -> 15f;
            case "Fast" -> 100f;
            default -> throw new IllegalArgumentException("Unknown processor: " + processor);
        };
    }

}
