package com.mb_lab.halal_cash.home.homeProfile.security;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.resetPassword.OTPVerification;
import com.mb_lab.halal_cash.resetPassword.changePasswordConstants;
import com.mb_lab.halal_cash.resetPassword.chooseVerificationOption;

import java.util.Objects;

public class ChangeUserId_Activity extends AppCompatActivity {
    private static final String TAG = "ChangeUserId_Activity";
    Intent intent;

    UserSessionManager userSessionManager;
    String intentValue;
    ImageView userIdTypeIcon, editUserId;
    TextView textView82, userId, userIdCreatedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_user_id);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userIdTypeIcon = findViewById(R.id.userIdTypeIcon);
        editUserId = findViewById(R.id.editUserId);
        textView82 = findViewById(R.id.textView82);
        userId = findViewById(R.id.userId);
        userIdCreatedAt = findViewById(R.id.userIdCreatedAt);

        userSessionManager = new UserSessionManager(this);

//        intent = new Intent(ChangeUserId_Activity.this, OTPVerification.class);

        try {
            intentValue = getIntent().getStringExtra(changePasswordConstants.REQUEST_FOR);
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }
//update ui
        if (Objects.equals(intentValue, changePasswordConstants.REQUEST_FOR_EMAIL_CHANGE)) {
            textView82.setText("Email Verification");
            userIdTypeIcon.setImageDrawable(getDrawable(R.drawable.email_round_icon));
            if (!userSessionManager.getUserEmail().equals("null")){
                userId.setText(userSessionManager.getUserEmail());
            }else {
                userId.setText("example@gmail.com");
            }
        } else if (Objects.equals(intentValue, changePasswordConstants.REQUEST_FOR_PHONE_CHANGE)) {
            textView82.setText("Phone Verification");
            userIdTypeIcon.setImageDrawable(getDrawable(R.drawable.icon_round_phone));
            if (!userSessionManager.getUserPhone().equals("null")){
                userId.setText(userSessionManager.getUserPhone());
            }else {
                userId.setText("01XX-XXX-XXX-X");
            }
        } else {
            finish();
        }
        findViewById(R.id.editUserId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: userEmail: "+userSessionManager.getUserEmail());
//                if (!userSessionManager.getUserEmail().equals("null")){
//                    intent = new Intent(chooseVerificationOption.this, OTPVerification.class);
//                    intent.putExtra(changePasswordConstants.REQUEST_FOR, changePasswordConstants.EMAIL_VERIFICATION );
//                    startActivity(intent);
//                }else {
//                    Toast.makeText(chooseVerificationOption.this,"Email not added yet.",Toast.LENGTH_LONG).show();
//                }

            }
        });

    }
}