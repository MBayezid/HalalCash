package com.mb_lab.halal_cash.resetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

public class chooseVerificationOption extends AppCompatActivity {
    private static final String TAG = "chooseVerificationOption";
    Intent intent;

    UserSessionManager userSessionManager;
    String intentValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_verification_option);


        userSessionManager = new UserSessionManager(this);

        intent = new Intent(chooseVerificationOption.this, OTPVerification.class);

        try {
            intentValue = getIntent().getStringExtra(changePasswordConstants.REQUEST_FOR);
        }catch (Exception e){
            Log.e(TAG, "onCreate: ", e);
        }



        findViewById(R.id.constraintLayoutEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: userEmail: "+userSessionManager.getUserEmail());
                if (!userSessionManager.getUserEmail().equals("null")){
                    intent = new Intent(chooseVerificationOption.this, OTPVerification.class);
                    intent.putExtra(changePasswordConstants.REQUEST_FOR, changePasswordConstants.EMAIL_VERIFICATION );
                    startActivity(intent);
                }else {
                    Toast.makeText(chooseVerificationOption.this,"Email not added yet.",Toast.LENGTH_LONG).show();
                }

            }
        });
        findViewById(R.id.constraintLayoutPhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: userPhone: "+userSessionManager.getUserPhone());
                if (!userSessionManager.getUserPhone().equals("null")){
                    intent = new Intent(chooseVerificationOption.this, OTPVerification.class);
                    intent.putExtra(changePasswordConstants.REQUEST_FOR, changePasswordConstants.PHONE_VERIFICATION);
                    startActivity(intent);
                }else {
                    Toast.makeText(chooseVerificationOption.this,"Phone not added yet.",Toast.LENGTH_LONG).show();
                }
            }
        });


    }



}