package com.paymentdemo.strategy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentStrategyTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalOutput;

    @BeforeEach
    void redirectOutput() throws Exception {
        originalOutput = System.out;
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOutput);
    }

    @Test
    void creditCardShouldPrintSuccessMessage() {
        new CreditCardPayment().pay(new BigDecimal("500.00"));

        assertEquals("Kredi kartı ile 500 TL ödeme başarılı.", printedLine());
    }

    @Test
    void bankTransferShouldPrintSuccessMessage() {
        new BankTransferPayment().pay(new BigDecimal("250"));

        assertEquals("Havale / EFT ile 250 TL ödeme başarılı.", printedLine());
    }

    @Test
    void digitalWalletShouldPrintSuccessMessage() {
        new DigitalWalletPayment().pay(new BigDecimal("75.50"));

        assertEquals("Dijital cüzdan ile 75.5 TL ödeme başarılı.", printedLine());
    }

    private String printedLine() {
        return output.toString(StandardCharsets.UTF_8).strip();
    }
}
