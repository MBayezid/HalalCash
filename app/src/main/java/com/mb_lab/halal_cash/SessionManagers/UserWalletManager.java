package com.mb_lab.halal_cash.SessionManagers;

import android.content.Context;
import android.content.SharedPreferences;

import com.mb_lab.halal_cash.Util.ConverterUtil;

import java.util.HashMap;

public class UserWalletManager {

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
    private static final String KEY_PREFER_NAME = "UserWalletManager";


    public static final String KEY_bdt = "KEY_bdt";
    public static final String KEY_gold = "KEY_gold";
    public static final String KEY_platinum = "KEY_platinum";
    public static final String KEY_palladium = "KEY_palladium";
    public static final String KEY_silver = "KEY_silver";

    private ConverterUtil converterUtil;

    // Constructor
    public UserWalletManager(Context context) {
        this.context = context;
        converterUtil = new ConverterUtil(context.getPackageName());
        pref = context.getSharedPreferences(KEY_PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void updateAllBalanceInfo(String bdt, String gold, String platinum, String palladium, String silver) {
        editor.putString(KEY_bdt, converterUtil.convertStringToString_2f(bdt));
        editor.putString(KEY_gold, converterUtil.convertStringToString_2f(gold));
        editor.putString(KEY_palladium, converterUtil.convertStringToString_2f(platinum));
        editor.putString(KEY_platinum, converterUtil.convertStringToString_2f(palladium));
        editor.putString(KEY_silver, converterUtil.convertStringToString_2f(silver));

        // commit changes
        editor.commit();
    }
    public void updateAllBalanceInfo() {
        editor.putString(KEY_bdt, null);
        editor.putString(KEY_gold, null);
        editor.putString(KEY_palladium,null);
        editor.putString(KEY_platinum, null);
        editor.putString(KEY_silver, null);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getAllBalance() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put(KEY_bdt, pref.getString(KEY_bdt, null));
        hashMap.put(KEY_gold, pref.getString(KEY_gold, null));
        hashMap.put(KEY_platinum, pref.getString(KEY_platinum, null));
        hashMap.put(KEY_palladium, pref.getString(KEY_palladium, null));
        hashMap.put(KEY_silver, pref.getString(KEY_silver, null));

        return hashMap;
    }


    public String getBdt() {
        return pref.getString(KEY_bdt, "00.00");
    }

    public String getGold() {
        return pref.getString(KEY_gold, "00.00");
    }

    public String getPlatinum() {
        return pref.getString(KEY_platinum, "00.00");
    }

    public String getPalladium() {
        return pref.getString(KEY_palladium, "00.00");
    }

    public String getSilver() {
        return pref.getString(KEY_silver, "00.00");
    }
}
