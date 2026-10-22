package com.paymentdemo.singleton;

import com.paymentdemo.model.PaymentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentServiceTest {
    private final PaymentService paymentService = PaymentService.getInstance();

    @Test
    void getInstanceShouldAlwaysReturnSameObject() {
        assertSame(PaymentService.getInstance(), PaymentService.getInstance());
    }

    @Test
    void shouldProcessPositiveAmount() {
        assertDoesNotThrow(() -> paymentService.processPayment(
                PaymentType.CREDIT_CARD, new BigDecimal("500.00")));
    }

    @Test
    void shouldRejectZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> paymentService.processPayment(
                PaymentType.BANK_TRANSFER, BigDecimal.ZERO));
    }

    @Test
    void shouldRejectNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> paymentService.processPayment(
                PaymentType.DIGITAL_WALLET, new BigDecimal("-1")));
    }
}
