package ru.bulgakov.qa.webshop.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.webshop.dto.CartExpectation;
import ru.bulgakov.qa.webshop.pages.WsProductPage;
import ru.bulgakov.qa.webshop.pages.WsWelcomePage;
import ru.bulgakov.qa.webshop.steps.AuthSteps;

import static com.codeborne.selenide.Selenide.open;
import static ru.bulgakov.qa.webshop.config.Config.WEB_SHOP_URL;

@Epic("Покупки")
@Feature("Корзина")
@Owner("n.nikflwz")
public class CartTest extends TestBase {
    private final AuthSteps authSteps = new AuthSteps();

    @BeforeEach
    void beforeEach() {
        authSteps.registerNewUser();
    }

    @Test
    @Story("Добавление товара в корзину")
    @DisplayName("Добавление товара в корзину с проверкой суммы и количества")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "TASK-130")
    void addItemToCartTest() {
        String quantity = "2";
        int productIndex = 0;

        WsProductPage productPage = open(WEB_SHOP_URL, WsWelcomePage.class)
                .openDesktops()
                .openProduct(productIndex)
                .selectProcessor(0)
                .setQuantity(quantity)
                .addToCart()
                .checkItemAddedToCart()
                .checkCartQuantity(quantity);

        CartExpectation expected = productPage.rememberCartExpectation(quantity);

        productPage.openCart()
                .checkMatches(expected);
    }
}
