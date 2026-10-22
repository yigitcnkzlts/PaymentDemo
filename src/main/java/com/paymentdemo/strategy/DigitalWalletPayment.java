package com.paymentdemo.strategy;

import java.math.BigDecimal;

public class DigitalWalletPayment implements PaymentStrategy {
    @Override
    public void pay(BigDecimal amount) {
        System.out.printf("Dijital cüzdan ile %s TL ödeme başarılı.%n", format(amount));
    }

    private String format(BigDecimal amount) {
        return amount.stripTrailingZeros().toPlainString();
    }
}
