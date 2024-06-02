package com.mb_lab.halal_cash.Enums;

public enum AdsType {

    SELL(50),
    BUY(51);

    private final int value;

    AdsType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
