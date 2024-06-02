package com.mb_lab.halal_cash.Enums;

public enum TransactionTypeEnums {
    DEPOSIT(30),
    SEND(31),
    RECEIVED(32),
    REFUND(33),
    GIFT(34),
    TOPUP(35),
    WITHDRAW(36);

    private final int value;

    TransactionTypeEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}