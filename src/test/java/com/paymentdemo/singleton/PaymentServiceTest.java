package com.paymentdemo.singleton;

import com.paymentdemo.model.PaymentResult;
import com.paymentdemo.model.PaymentStatus;
import com.paymentdemo.model.PaymentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        BigDecimal amount = new BigDecimal("500.00");

        PaymentResult result = paymentService.processPayment(PaymentType.CREDIT_CARD, amount);

        assertEquals(amount, result.amount());
        assertEquals(PaymentType.CREDIT_CARD, result.paymentType());
        assertEquals(PaymentStatus.SUCCESS, result.status());
        assertNotNull(result.transactionId());
    }

    @Test
    void shouldRejectNullAmount() {
        assertThrows(IllegalArgumentException.class, () -> paymentService.processPayment(
                PaymentType.CREDIT_CARD, null));
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

    @Test
    void shouldCreateDifferentTransactionIdsForDifferentPayments() {
        PaymentResult firstResult = paymentService.processPayment(
                PaymentType.CREDIT_CARD, new BigDecimal("100"));
        PaymentResult secondResult = paymentService.processPayment(
                PaymentType.CREDIT_CARD, new BigDecimal("100"));

        assertNotEquals(firstResult.transactionId(), secondResult.transactionId());
    }
}
