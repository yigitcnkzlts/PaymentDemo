package com.paymentdemo;

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
                    paymentService.processPayment(paymentType, amount);
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
}
