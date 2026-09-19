package ru.bulgakov.qa.pages;

import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {

    public PaymentPage clickWantToStartQA() {
        $("[role=button]").click();

        return this;
    }

    public PaymentPage clickPay() {
        $("#sbs-921114964-1741924491598").click();

        return this;
    }
}