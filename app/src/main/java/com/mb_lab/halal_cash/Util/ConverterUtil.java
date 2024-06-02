package com.mb_lab.halal_cash.Util;

import android.util.Log;

import androidx.annotation.NonNull;

public class ConverterUtil {
    private String TAG;

    public ConverterUtil(String TAG) {
        this.TAG = TAG;
    }

    public String convertNumberToString(@NonNull Number number) {
        try {
            return String.format("%.2f", number);
        } catch (Exception exception) {
            Log.e("ConverterUtil", "convertNumberToString: ", exception);
        }
        return null;
    }

    public Number convertStringToNumber(@NonNull String string) {
        try {

        } catch (Exception exception) {
            Log.e("ConverterUtil", "convertNumberToString: ", exception);
        }
        return null;
    }

    public Float convertStringToFloat_2f(String string) {
        try {
            return Float.parseFloat(String.format("%.2f", string.trim()));
        } catch (Exception exception) {
            Log.e("ConverterUtil", "convertNumberToString: ", exception);
        }
        return null;
    }

    public String convertStringToString_2f(@NonNull String string) {
        try {
            return String.format("%.2f", Float.parseFloat(string.trim()));

        } catch (Exception exception) {
            Log.e("ConverterUtil", "convertStringToString_2f: ", exception);
        }
        return null;
    }
}
