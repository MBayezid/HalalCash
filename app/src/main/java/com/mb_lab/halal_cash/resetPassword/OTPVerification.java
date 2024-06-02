package com.mb_lab.halal_cash.resetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.resetPassword.resetPassword_1_request;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.resetPassword.resetPassword_2_verifyOtp;
import com.mb_lab.halal_cash.Enums.UserIdTypeEnums;
import com.mb_lab.halal_cash.PayPin.ResetPayPin0;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

public class OTPVerification extends AppCompatActivity {
    private static final String TAG = "OTPVerification";
    Intent intent;
    String user_id_type;
    String user_id;

    UserSessionManager userSessionManager;
    String intentValue;

    resetPassword_1_request reset_password_1_request;
    resetPassword_2_verifyOtp resetPassword2VerifyOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        userSessionManager = new UserSessionManager(this);

        reset_password_1_request = new resetPassword_1_request(this, TAG);
        resetPassword2VerifyOtp = new resetPassword_2_verifyOtp(this, TAG);

        try {
            intentValue = getIntent().getStringExtra(changePasswordConstants.REQUEST_FOR);
            Log.d(TAG, "onCreate: intentValue: " + intentValue);
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }


        //update ui
        if (intentValue.equals(changePasswordConstants.EMAIL_VERIFICATION)) {
            ((TextView) findViewById(R.id.textView10)).setText("Email Verification");
            ((TextView) findViewById(R.id.textView35)).setText("Enter the 5-digit verification code sent to ******@gmail.com");
            user_id_type = UserIdTypeEnums.EMAIL.getValue();
            user_id = userSessionManager.getUserEmail();

        } else if (intentValue.equals(changePasswordConstants.PHONE_VERIFICATION)) {
            ((TextView) findViewById(R.id.textView10)).setText("Phone Verification");
            ((TextView) findViewById(R.id.textView35)).setText("Enter the 5-digit verification code sent to 01XX-XXXX-XXX");
            user_id_type = UserIdTypeEnums.PHONE.getValue();
            user_id = userSessionManager.getUserPhone();
        } else {
            finish();
        }

        reset_password_1_request.call(user_id_type, user_id, ((EditText) findViewById(R.id.editText2)));


        findViewById(R.id.textViewContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = ((EditText) findViewById(R.id.editText2)).getText().toString().trim();
                Log.d(TAG, "onClick: code: " + code + "->" + code.length());
                if (code.length() == 5) {
                    try {
                        int otp = Integer.parseInt(code);
                        resetPassword2VerifyOtp.call(user_id, user_id_type, String.valueOf(otp));

                    } catch (Exception _) {

                    }
                }


//                startActivity(new Intent(OTPVerification.this, ResetPayPin0.class));
            }
        });
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher();

            }
        });
        findViewById(R.id.textView11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reset_password_1_request.call(user_id_type, user_id, ((EditText) findViewById(R.id.editText2)));

                Toast.makeText(OTPVerification.this, "OTP Sent.", Toast.LENGTH_LONG).show();

            }
        });


    }

//    private void sendOTP() {
//
//    }

}