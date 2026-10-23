package com.paymentdemo.model;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResult(
        UUID transactionId,
        PaymentType paymentType,
        BigDecimal amount,
        PaymentStatus status,
        String message
) {
}
