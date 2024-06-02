package com.mb_lab.halal_cash.Util;

import android.content.Context;
import android.util.Log;

import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.SessionManagers.UserWalletManager;

public class ResetUserInfo {
    public ResetUserInfo(Context context) {
        try {
            new UserSessionManager(context).updateUserAllInfo(null, null, null, null, null, null,null, null, null, null);
            new UserWalletManager(context).updateAllBalanceInfo();
            Log.d(context.getPackageName(), "ResetUserInfo: Successful");

        } catch (Exception e) {
            Log.e(context.getPackageName(), "ResetUserInfo: logout error,", e);

        }

    }
}
