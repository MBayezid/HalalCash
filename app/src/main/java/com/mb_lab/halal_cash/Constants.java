package com.mb_lab.halal_cash;

public class Constants {
    public static final String BASE_URL = "https://halalcash.mblab.tech/";
    public static final String LOGIN_URL = "api/login";
    public static final String REGISTRATION_URL = "api/registration";
    public static final String ACCOUNT_AUTHENTICATION_URL = "api/account-verification";
    public static final String REFRESH_TOKEN_URL = "api/refresh-token";
    public static final String HOME_PROFILE_URL = "api/profile";
    public static final String WALLET_URL = "api/wallet";
    public static final String GET_RECEIVER_URL = "api/send/get-receiver";
    public static final String GET_CHECK_SENDING_BALANCE_URL = "api/send/check-sending-balance";
    public static final String GET_CHECK_PAY_PIN_STATUS_URL = "api/profile/check-pay-pin-status";
    public static final String POST_SET_PAY_PIN_URL = "api/profile/set-pay-pin";
    public static final String POST_SEND_STORE_URL = "api/send/send-store";
    public static final String GET_TRANSACTION_HISTORY_URL = "api/transaction-history";
    public static final String GET_UNLOCK_RECEIVED_TRANSACTION_URL = "api/send/unlock-send-money";

    public static final String GET_ALL_P2P_ADS_URL = "api/ads/getAllAds";

    public static final String POST_P2P_CREATE_ADS_REQUEST_URL = "api/ads/store";
    public static final String GET_P2P_CREATED_ADS_URL = "api/ads/user-ads";
    public static final String POST_UPDATE_USER_PROFILE_NAME_URL = "api/home-profile/update-name";
    public static final String POST_UPDATE_USER_PROFILE_IMAGE_URL = "api/home-profile/update-image";
    public static final String GET_CURRENT_GOLD_PRICE_URL = "api/asset/gold-price";
    public static final String POST_BUY_SELL_ADS_URL = "api/ads";
    public static final String GET_ALL_ADS_TRANSACTIONS = "api/ads/transactions";
    public static final String GET_DEPOSIT_AGENT_LIST_URL = "api/deposit-agents";
    public static final String POST_RESET_PASSWORD_1_REQUEST_URL = "api/reset-password/request";
    public static final String POST_RESET_PASSWORD_2_VERIFY_OTP_URL = "api/reset-password/verify";
    public static final String POST_RESET_PASSWORD_3_SET_PASSWORD_URL = "api/reset-password/set-password";



    public static final String GET_ALL_HOME_NOTIFICATION_URL = "api/home-notification";
    public static final String GET_ALL_GIFTS_URL = "api/gifts";

    public static final String POST_UPDATE_USER_ID_1_REQUEST_URL = "api/reset-user-id/request";
    public static final String POST_UPDATE_USER_ID_2_VERIFY_URL = "api/reset-user-id/verify";
    public static final String POST_UPDATE_USER_ID_3_UPDATE_URL = "api/reset-user-id/update";
    public static final String POST_DELETE_USER_ACCOUNT_PERMANENTLY_URL = "api/profile/delete";
}
