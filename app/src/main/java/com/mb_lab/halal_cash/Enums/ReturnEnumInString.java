package com.mb_lab.halal_cash.Enums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ReturnEnumInString {

    //.............AdsType....................
    public static final int SELL = 50;
    public static final int BUY = 51;

    //.............TransactionStatusEnums....................
    public static final int DEPOSIT = 30;
    public static final int SEND = 31;
    public static final int RECEIVED = 32;

    public static final int REFUND = 33;
    public static final int REFUNDED = 29;
    public static final int GIFT = 34;
    public static final int TOPUP = 35;
    public static final int WITHDRAW = 36;
    // ...............................TransactionTypeEnums....................
    public static final int RECEIVED_FAILED = 24;
    public static final int SENT = 25;
    public static final int PENDING = 26;
    public static final int CLAIMED = 27;
    public static final int EXPIRED = 28;

//    public static final int REFUND  = 29;

    //.....................AssetTypeEnums......................
    public static final int BDT = 100;
    public static final int GOLD = 101;
    public static final int PLATINUM = 102;
    public static final int PALLADIUM = 103;
    public static final int SILVER = 104;

    //    ......................UserRoleTypeEnums.....................
    public static final int AGENT = 3;
    public static final int REGULAR = 4;


    //    ......................UserIdTypeEnums.....................
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String PAYID = "pay_id";


    public String getEnumToString(@Nullable int enumInt) {
        switch (enumInt) {
            case RECEIVED_FAILED:
                return "RECEIVED FAILED";
            case DEPOSIT:
                return "Deposit";
            case SEND:
                return "Send";
            case RECEIVED:
                return "Received";
            case GIFT:
                return "Gift";
            case SENT:
                return "Sent";
            case TOPUP:
                return "TOP UP";

            case PENDING:
                return "Pending";
            case CLAIMED:
                return "Claimed";
            case EXPIRED:
                return "Expired";
            case REFUNDED:
                return "Refunded";

            case BDT:
                return "BDT";
            case GOLD:
                return "GOLD";
            case PLATINUM:
                return "PLATINUM";
            case PALLADIUM:
                return "PALLADIUM";
            case SILVER:
                return "SILVER";

            case BUY:
                return "Buy";
            case SELL:
                return "Sell";
        }
        return null;
    }


}
