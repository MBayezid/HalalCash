package com.mb_lab.halal_cash.mobileTopUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mb_lab.halal_cash.R;

public class MobileTopUpHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_top_up_history);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}