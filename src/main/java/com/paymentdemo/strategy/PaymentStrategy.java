package com.paymentdemo.strategy;

import com.paymentdemo.model.PaymentResult;

import java.math.BigDecimal;

public interface PaymentStrategy {
    PaymentResult pay(BigDecimal amount);
}
