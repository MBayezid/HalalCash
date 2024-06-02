package com.mb_lab.halal_cash.Enums;

public enum AssetTypeEnums {
    BDT(100),
    GOLD(101),
    PLATINUM(102),
    PALLADIUM(103),
    SILVER(104);

    private final int value;

    AssetTypeEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

//    public String getStringValue() {
//        switch (value) {
//            case 100:
//                return "BDT";
//            case 101:
//                return "GOLD";
//            case 102:
//                return "PLATINUM";
//            case 103:
//                return "PALLADIUM";
//            case 104:
//                return "SILVER";
//
//        }
//        return null;
//    }
}