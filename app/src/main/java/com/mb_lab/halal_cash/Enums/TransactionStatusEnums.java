package com.mb_lab.halal_cash.Enums;

public enum TransactionStatusEnums {
    RECEIVED_FAILED(24),
    SENT(25),
    PENDING(26),
    CLAIMED(27),
    EXPIRED(28),
    REFUNDED(29);
    private final int value;

    TransactionStatusEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}