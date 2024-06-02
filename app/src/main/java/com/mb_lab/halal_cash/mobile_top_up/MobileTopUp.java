package com.mb_lab.halal_cash.mobile_top_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.mobileTopUp.MobileTopUpHistory;

public class MobileTopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_top_up);

        findViewById(R.id.btnHistory).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MobileTopUp.this, MobileTopUpHistory.class));
            }
        });
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
    }
}