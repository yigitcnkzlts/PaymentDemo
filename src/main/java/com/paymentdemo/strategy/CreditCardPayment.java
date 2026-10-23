package com.paymentdemo.strategy;

import com.paymentdemo.model.PaymentResult;
import com.paymentdemo.model.PaymentStatus;
import com.paymentdemo.model.PaymentType;

import java.math.BigDecimal;
import java.util.UUID;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public PaymentResult pay(BigDecimal amount) {
        return new PaymentResult(UUID.randomUUID(), PaymentType.CREDIT_CARD, amount,
                PaymentStatus.SUCCESS, "Kredi kartı ile ödeme başarılı.");
    }
}
