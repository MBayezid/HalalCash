package com.mb_lab.halal_cash.SessionManagers;

import android.content.Context;
import android.content.SharedPreferences;

import com.mb_lab.halal_cash.Util.ConverterUtil;

import java.util.HashMap;

public class UserCurrentGoldPrice {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    private Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
//    private static final String KEY_PREFER_NAME = context.getString(R.string.app_name).toString();
    private static final String KEY_PREFER_NAME = "UserCurrentGoldPrice";


    public static final String KEY_DATE = "DATE";
    public static final String KEY_LOW_PRICE = "LOW_PRICE";
    public static final String KEY_HIGH_PRICE = "HIGH_PRICE";

    private ConverterUtil converterUtil;

    // Constructor
    public UserCurrentGoldPrice(Context context) {
        this.context = context;
        converterUtil = new ConverterUtil(context.getPackageName());
        pref = context.getSharedPreferences(KEY_PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void updateCurrentGoldPrice(String date, String lowPrice, String highPrice) {

        editor.putString(KEY_DATE, date);
        editor.putString(KEY_LOW_PRICE, converterUtil.convertStringToString_2f(lowPrice));
        editor.putString(KEY_HIGH_PRICE, converterUtil.convertStringToString_2f(highPrice));

        // commit changes
        editor.commit();

    }



    public String getGoldUnitPriceLow() {
        return pref.getString(KEY_LOW_PRICE, "0");
    }

    public String getGoldUnitPriceHigh() {
        return pref.getString(KEY_HIGH_PRICE, "0");
    }

    public String getGoldUnitPriceUpdatedDate() {
        return pref.getString(KEY_DATE, "0");
    }

}
