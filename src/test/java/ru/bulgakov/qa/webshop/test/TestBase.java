package ru.bulgakov.qa.webshop.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import ru.bulgakov.qa.webshop.config.WebDriverConfig;
import ru.bulgakov.qa.webshop.util.AttachManager;

import static ru.bulgakov.qa.webshop.config.Config.getSelenoidChromeOptions;
import static ru.bulgakov.qa.webshop.config.Config.getWebDriverConfig;

public class TestBase {

    private static final WebDriverConfig config = getWebDriverConfig();

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.browserSize = config.browserSize();
        Configuration.browser = config.browser();

        if ("remote".equals(System.getProperty("run", "local"))) {
            Configuration.remote =
                    "https://" + config.selenoidUser() + ":" + config.selenoidPassword() + "@" + config.selenoidUrl();
            Configuration.browserCapabilities = getSelenoidChromeOptions();
        }
    }

    @AfterEach
    void after() {
        if (!WebDriverRunner.hasWebDriverStarted()) {
            return;
        }

        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();

        AttachManager.takeScreenshot();
        AttachManager.pageSource();
        AttachManager.browserConsoleLogs();

        if("remote".equals(config.run())) {
            AttachManager.addVideo();
        }

        Selenide.closeWebDriver();
    }
}