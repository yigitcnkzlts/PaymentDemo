package com.paymentdemo.factory;

import com.paymentdemo.model.PaymentType;
import com.paymentdemo.strategy.BankTransferPayment;
import com.paymentdemo.strategy.CreditCardPayment;
import com.paymentdemo.strategy.DigitalWalletPayment;
import com.paymentdemo.strategy.PaymentStrategy;

public final class PaymentFactory {
    private PaymentFactory() {
    }

    public static PaymentStrategy createPayment(PaymentType paymentType) {
        if (paymentType == null) {
            throw new IllegalArgumentException("Ödeme yöntemi boş olamaz.");
        }

        return switch (paymentType) {
            case CREDIT_CARD -> new CreditCardPayment();
            case BANK_TRANSFER -> new BankTransferPayment();
            case DIGITAL_WALLET -> new DigitalWalletPayment();
        };
    }
}
