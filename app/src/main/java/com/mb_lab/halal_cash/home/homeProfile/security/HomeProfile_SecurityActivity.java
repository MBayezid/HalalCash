package com.mb_lab.halal_cash.home.homeProfile.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.resetPassword.changePasswordConstants;
import com.mb_lab.halal_cash.resetPassword.chooseVerificationOption;

public class HomeProfile_SecurityActivity extends AppCompatActivity {
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_profile_security);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        findViewById(R.id.userEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeProfile_SecurityActivity.this, ChangeUserId_Activity.class);
                intent.putExtra(changePasswordConstants.REQUEST_FOR, changePasswordConstants.REQUEST_FOR_EMAIL_CHANGE);
                startActivity(intent);
            }
        });
        findViewById(R.id.userPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeProfile_SecurityActivity.this, chooseVerificationOption.class);
                intent.putExtra(changePasswordConstants.REQUEST_FOR, changePasswordConstants.REQUEST_FOR_RESET_PASSWORD);
                startActivity(intent);

            }
        });
        findViewById(R.id.userContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeProfile_SecurityActivity.this, ChangeUserId_Activity.class);
                intent.putExtra(changePasswordConstants.REQUEST_FOR, changePasswordConstants.REQUEST_FOR_PHONE_CHANGE);
                startActivity(intent);

            }
        });
        findViewById(R.id.imageView21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }
}