package com.paymentdemo.singleton;

import com.paymentdemo.factory.PaymentFactory;
import com.paymentdemo.model.PaymentType;
import com.paymentdemo.strategy.PaymentStrategy;

import java.math.BigDecimal;

public final class PaymentService {
    private PaymentService() {
    }

    private static class InstanceHolder {
        private static final PaymentService INSTANCE = new PaymentService();
    }

    public static PaymentService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void processPayment(PaymentType paymentType, BigDecimal amount) {
        validateAmount(amount);
        PaymentStrategy paymentStrategy = PaymentFactory.createPayment(paymentType);
        paymentStrategy.pay(amount);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Ödeme tutarı boş olamaz.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Ödeme tutarı sıfırdan büyük olmalıdır.");
        }
    }
}
