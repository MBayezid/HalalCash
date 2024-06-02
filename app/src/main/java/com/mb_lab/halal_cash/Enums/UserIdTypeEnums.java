package com.mb_lab.halal_cash.Enums;

public enum UserIdTypeEnums {
    EMAIL("email"),
    PHONE("phone"),
    PAYID("pay_id");

    private final String value;

    UserIdTypeEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
