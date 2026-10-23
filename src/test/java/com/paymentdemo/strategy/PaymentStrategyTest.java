package com.paymentdemo.strategy;

import com.paymentdemo.model.PaymentResult;
import com.paymentdemo.model.PaymentStatus;
import com.paymentdemo.model.PaymentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentStrategyTest {
    @Test
    void creditCardShouldReturnExpectedPaymentResult() {
        BigDecimal amount = new BigDecimal("500.00");

        PaymentResult result = new CreditCardPayment().pay(amount);

        assertSuccessfulResult(result, PaymentType.CREDIT_CARD, amount,
                "Kredi kartı ile ödeme başarılı.");
    }

    @Test
    void bankTransferShouldReturnExpectedPaymentResult() {
        BigDecimal amount = new BigDecimal("250");

        PaymentResult result = new BankTransferPayment().pay(amount);

        assertSuccessfulResult(result, PaymentType.BANK_TRANSFER, amount,
                "Havale / EFT ile ödeme başarılı.");
    }

    @Test
    void digitalWalletShouldReturnExpectedPaymentResult() {
        BigDecimal amount = new BigDecimal("75.50");

        PaymentResult result = new DigitalWalletPayment().pay(amount);

        assertSuccessfulResult(result, PaymentType.DIGITAL_WALLET, amount,
                "Dijital cüzdan ile ödeme başarılı.");
    }

    private void assertSuccessfulResult(PaymentResult result, PaymentType paymentType,
                                        BigDecimal amount, String message) {
        assertNotNull(result.transactionId());
        assertEquals(paymentType, result.paymentType());
        assertEquals(amount, result.amount());
        assertEquals(PaymentStatus.SUCCESS, result.status());
        assertEquals(message, result.message());
    }
}
