package com.mb_lab.halal_cash.SessionManagers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class UserSessionManager {

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
    private static final String KEY_PREFER_NAME = "UserSessionManager";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PAY_ID = "pay_id";
    public static final String KEY_VERIFIED = "verified";
    public static final String KEY_is_authenticated = "is_authenticated";
    public static final String KEY_ACCOUNT_TYPE = "account_type";
    public static final String KEY_IMAGE_URL = "image_url";
    public static final String KEY_IS_FIRST_TIME_LOGIN = "is_first_time_logged_in";

    // Constructor
    public UserSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(KEY_PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void updateUserAllInfo(@Nullable String token, @Nullable String id, @Nullable String name, @Nullable String email, @Nullable String phone, @Nullable String pay_id, @Nullable Boolean verified, @Nullable Boolean isAuthenticated, @Nullable String account_type, @Nullable String imageUrl) {

        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PAY_ID, pay_id);
        editor.putBoolean(KEY_VERIFIED, Boolean.TRUE.equals(verified));
        editor.putBoolean(KEY_is_authenticated, Boolean.TRUE.equals(isAuthenticated));
        editor.putString(KEY_ACCOUNT_TYPE, account_type);
        editor.putString(KEY_IMAGE_URL, imageUrl);
        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getAllUserInfo() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        hashMap.put(KEY_ID, pref.getString(KEY_ID, null));
        hashMap.put(KEY_NAME, pref.getString(KEY_NAME, null));
        hashMap.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        hashMap.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        hashMap.put(KEY_PAY_ID, pref.getString(KEY_PAY_ID, null));
        hashMap.put(KEY_VERIFIED, String.valueOf(pref.getBoolean(KEY_VERIFIED, false)));
        hashMap.put(KEY_is_authenticated, String.valueOf(pref.getBoolean(KEY_is_authenticated, false)));
        hashMap.put(KEY_ACCOUNT_TYPE, pref.getString(KEY_ACCOUNT_TYPE, null));

        return hashMap;
    }

    public void updateToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, null);
    }

    public void updateImageUrl(String imageUrl) {
        editor.putString(KEY_IMAGE_URL, imageUrl);
        editor.commit();
    }

    public void updateUserName(String name) {
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    public String getImageUrl() {
        return pref.getString(KEY_IMAGE_URL, null);
    }

//    public void uploadImageBitmap(Bitmap bitmap) {
//        editor.put(KEY_IMAGE_URL, bitmap);
//        editor.commit();
//    }


    public String getUserPayId() {
        return pref.getString(KEY_PAY_ID, null);
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, null);
    }
    public void updateUseEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }
    public String getUserPhone() {
        return pref.getString(KEY_PHONE, null);
    }
    public void updateUsePhone(String phone) {
        editor.putString(KEY_PHONE, phone);
        editor.commit();
    }
    public String getUserType() {
        return pref.getString(KEY_ACCOUNT_TYPE, null);
    }

    public void updateFirstTimeLoginStatus(boolean state) {
        editor.putBoolean(KEY_IS_FIRST_TIME_LOGIN, state);
        editor.commit();
    }

    public boolean getFirstTimeLoginStatus() {
        return pref.getBoolean(KEY_IS_FIRST_TIME_LOGIN, false);
    }
}
