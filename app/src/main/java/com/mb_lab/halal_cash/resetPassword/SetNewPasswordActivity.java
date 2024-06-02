package com.mb_lab.halal_cash.resetPassword;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.resetPassword.resetPassword_3_setPassword;
import com.mb_lab.halal_cash.Enums.UserIdTypeEnums;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.Util.InputInformationValidation;

public class SetNewPasswordActivity extends AppCompatActivity {
    String user_id_type = null;
    String user_id = null;
    private final String TAG = "SetNewPasswordActivity";
    resetPassword_3_setPassword resetPassword3SetPassword;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_new_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userSessionManager = new UserSessionManager(this);
        resetPassword3SetPassword = new resetPassword_3_setPassword(SetNewPasswordActivity.this, TAG);

//update ui
        if (!userSessionManager.getUserEmail().equals("null")) {
            user_id_type = UserIdTypeEnums.EMAIL.getValue();
            user_id = userSessionManager.getUserEmail();

        } else if (!userSessionManager.getUserPhone().equals("null")) {
            user_id_type = UserIdTypeEnums.PHONE.getValue();
            user_id = userSessionManager.getUserPhone();
        } else {
            finish();
        }
        Log.d(TAG, "user_id & type: " + user_id + " & " + user_id_type);
        findViewById(R.id.postRegistrationData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postNewPassword();

            }
        });

    }

    public void postNewPassword() {
        try {
            String password = ((TextInputEditText) findViewById(R.id.userPassword)).getText().toString().trim();
            String confirm_password = ((TextInputEditText) findViewById(R.id.userConfirmPassword)).getText().toString().trim();

            if (new InputInformationValidation().passwordPatternValidation(password) && new InputInformationValidation().userIDAndPasswordSimilarity(user_id, password)) {

                if (password.equals(confirm_password) && user_id != null && user_id_type != null) {
                    Log.d(TAG, "user_id: " + user_id);
                    Log.d(TAG, "user_id_type: " + user_id_type);
                    Log.d(TAG, "password: " + password);
                    Log.d(TAG, "confirm_password: " + confirm_password);

                    resetPassword3SetPassword.call(user_id, user_id_type, password );

                } else {
                    ((TextInputLayout) findViewById(R.id.textFieldPassword2)).setError("Confirm password mismatch!");
                    ((TextInputLayout) findViewById(R.id.textFieldPassword2)).setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                }


            } else {
                ((TextInputLayout) findViewById(R.id.textFieldPassword1)).setError("1. Password length (min 8 to max 30 chars).\n2. At least one uppercase letter & number.\n[Don't use your email or phone number as password.]");
                ((TextInputLayout) findViewById(R.id.textFieldPassword1)).setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            }

        } catch (Exception e) {
            Log.e(TAG, "postRegistrationData: pattern Matching Error ", e);
        }

    }
}