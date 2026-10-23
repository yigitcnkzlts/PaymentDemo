package com.paymentdemo;

import com.paymentdemo.model.PaymentResult;
import com.paymentdemo.model.PaymentType;
import com.paymentdemo.singleton.PaymentService;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    private static final String MENU = """
            ===== PAYMENT DEMO =====
            1 - Kredi Kartı
            2 - Havale / EFT
            3 - Dijital Cüzdan
            0 - Çıkış
            """;

    public static void main(String[] args) {
        PaymentService paymentService = PaymentService.getInstance();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.print(MENU);
                System.out.print("Seçiminiz: ");
                String selection = scanner.nextLine().trim();

                if ("0".equals(selection)) {
                    System.out.println("Uygulamadan çıkılıyor. Hoşça kalın!");
                    running = false;
                    continue;
                }

                PaymentType paymentType = paymentTypeFrom(selection);
                if (paymentType == null) {
                    System.out.println("Geçersiz seçim. Lütfen 0 ile 3 arasında bir değer girin.\n");
                    continue;
                }

                System.out.print("Ödeme tutarı (TL): ");
                String amountInput = scanner.nextLine().trim().replace(',', '.');

                try {
                    BigDecimal amount = new BigDecimal(amountInput);
                    PaymentResult paymentResult = paymentService.processPayment(paymentType, amount);
                    printPaymentResult(paymentResult);
                } catch (NumberFormatException exception) {
                    System.out.println("Geçersiz tutar. Lütfen sayısal bir değer girin.");
                } catch (IllegalArgumentException exception) {
                    System.out.println(exception.getMessage());
                }

                System.out.println();
            }
        }
    }

    private static PaymentType paymentTypeFrom(String selection) {
        return switch (selection) {
            case "1" -> PaymentType.CREDIT_CARD;
            case "2" -> PaymentType.BANK_TRANSFER;
            case "3" -> PaymentType.DIGITAL_WALLET;
            default -> null;
        };
    }

    private static void printPaymentResult(PaymentResult paymentResult) {
        System.out.println("\n===== ÖDEME SONUCU =====");
        System.out.printf("İşlem ID: %s%n", paymentResult.transactionId());
        System.out.printf("Ödeme Yöntemi: %s%n", displayName(paymentResult.paymentType()));
        System.out.printf("Tutar: %s TL%n", formatAmount(paymentResult.amount()));
        System.out.printf("Durum: %s%n", paymentResult.status());
        System.out.printf("Mesaj: %s%n", paymentResult.message());
        System.out.println("=========================");
    }

    private static String displayName(PaymentType paymentType) {
        return switch (paymentType) {
            case CREDIT_CARD -> "Kredi Kartı";
            case BANK_TRANSFER -> "Havale / EFT";
            case DIGITAL_WALLET -> "Dijital Cüzdan";
        };
    }

    private static String formatAmount(BigDecimal amount) {
        return amount.stripTrailingZeros().toPlainString();
    }
}
