package com.mb_lab.halal_cash.Util;

import android.view.View;
import android.widget.TextView;

import com.mb_lab.halal_cash.Constants;

public class AutoInsertOTP {
    public AutoInsertOTP(String fullMessage, View view) {
        String tokens[] = fullMessage.trim().split(" ");
        int l = tokens.length;
        ((TextView) view).setText(tokens[l - 1]);
    }
}
