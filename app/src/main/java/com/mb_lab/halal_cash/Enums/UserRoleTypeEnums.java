package com.mb_lab.halal_cash.Enums;

public enum UserRoleTypeEnums {
    AGENT (3),
    REGULAR (4);


    private final int value;
    UserRoleTypeEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
