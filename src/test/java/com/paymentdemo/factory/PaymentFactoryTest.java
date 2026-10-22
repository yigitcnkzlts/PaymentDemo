package com.paymentdemo.factory;

import com.paymentdemo.model.PaymentType;
import com.paymentdemo.strategy.BankTransferPayment;
import com.paymentdemo.strategy.CreditCardPayment;
import com.paymentdemo.strategy.DigitalWalletPayment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentFactoryTest {
    @Test
    void shouldCreateCreditCardPayment() {
        assertInstanceOf(CreditCardPayment.class,
                PaymentFactory.createPayment(PaymentType.CREDIT_CARD));
    }

    @Test
    void shouldCreateBankTransferPayment() {
        assertInstanceOf(BankTransferPayment.class,
                PaymentFactory.createPayment(PaymentType.BANK_TRANSFER));
    }

    @Test
    void shouldCreateDigitalWalletPayment() {
        assertInstanceOf(DigitalWalletPayment.class,
                PaymentFactory.createPayment(PaymentType.DIGITAL_WALLET));
    }

    @Test
    void shouldRejectNullPaymentType() {
        assertThrows(IllegalArgumentException.class,
                () -> PaymentFactory.createPayment(null));
    }
}
