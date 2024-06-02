package com.mb_lab.halal_cash.Util;

import android.util.Log;

import com.mb_lab.halal_cash.Enums.UserIdTypeEnums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputInformationValidation {
    private final String TAG = "InputInformationValidation.class";

    public String getUserIdType(String input) {
        if (isEmail(input)) {
            return UserIdTypeEnums.EMAIL.getValue();
        } else if (isPhoneNumber(input)) {
            return UserIdTypeEnums.PHONE.getValue();
        }

        return null;
    }

    private boolean isEmail(String input) {
        // Regular expression for matching email addresses
        Pattern emailPattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b");
        Matcher emailMatcher = emailPattern.matcher(input);
        return emailMatcher.matches();
    }

    private boolean isPhoneNumber(String input) {
        // Regular expression for matching phone numbers
        Pattern phonePattern = Pattern.compile("\\b[\\d()+-]+\\d{3,}\\d*\\b");
        Matcher phoneMatcher = phonePattern.matcher(input);
        return phoneMatcher.matches();
    }

    public boolean passwordPatternValidation(String password) {
        // Check if password length is between 8 and 30 characters
        if (password.length() < 7 || password.length() > 29) {
            Log.d(TAG, "passwordPatternValidation: Range Problem..");
            return false;
        }

        // Check if password contains at least one uppercase letter
        boolean hasUppercase = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
                break;
            }
        }
        if (!hasUppercase) {
            return false;
        }
//
        // Check if password contains at least one number
        boolean hasNumber = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
                break;
            }
        }
        if (!hasNumber) {
            return false;
        }

        // If all criteria are met, return true
        return true;
    }

    public boolean userIDAndPasswordSimilarity(String id, String password) {
        if (id.trim().equals(password.trim())) {
            return false;
        }
        return true;

    }
}
