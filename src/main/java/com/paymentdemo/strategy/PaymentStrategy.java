package com.paymentdemo.strategy;

import java.math.BigDecimal;

public interface PaymentStrategy {
    void pay(BigDecimal amount);
}
