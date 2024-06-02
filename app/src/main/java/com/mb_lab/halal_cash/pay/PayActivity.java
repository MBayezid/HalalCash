package com.mb_lab.halal_cash.pay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetGoldCurrentPrice;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetUserBalance;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.SplashActivity;
import com.mb_lab.halal_cash.home.homeFragments.ComingSoonFragment;
import com.mb_lab.halal_cash.pay.payFragments.PayHistoryFragment;
import com.mb_lab.halal_cash.pay.payFragments.PayHomeFragment;

public class PayActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private final String TAG = "PayActivity";
    private String TOKEN = null;
    private Fragment selectedFragment = null;
    private UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);


        try {

            userSessionManager = new UserSessionManager(PayActivity.this);
            TOKEN = userSessionManager.getToken();
            Log.d(TAG, "onCreate: " + TOKEN);



        } catch (Exception e) {
            Toast.makeText(PayActivity.this, "Error.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(PayActivity.this, SplashActivity.class));
            finish();
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new PayHomeFragment()).commit();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    new GetUserBalance(PayActivity.this, TAG);
                    new GetGoldCurrentPrice(PayActivity.this, TAG);


                } catch (Exception e) {
                    Log.e(TAG, "onViewCreated: Failed to update GetUserBalance + GetUserBalance ...",e);
                }
            }
        }).start();

    }

    @Override
    public void onBackPressed() {


        // Otherwise, call the default behavior
        super.onBackPressed();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        // By using switch we can easily get
        // the selected fragment
        // by using there id.
        selectedFragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.action_home) {
            selectedFragment = new PayHomeFragment();
            bottomNavigationView.setBackgroundColor(getColor(R.color.gray));

        } else if (itemId == R.id.action_history) {

            bottomNavigationView.setBackgroundColor(getColor(R.color.black));
            selectedFragment = new PayHistoryFragment(PayActivity.this);

        } else if (itemId == R.id.action_notification) {

            bottomNavigationView.setBackgroundColor(getColor(R.color.black));
            selectedFragment = new ComingSoonFragment();

        } else if (itemId == R.id.action_wallet) {
            bottomNavigationView.setBackgroundColor(getColor(R.color.gray));
//            selectedFragment = new WalletFragment();

        }
        // It will help to replace the
        // one fragment to other.
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, selectedFragment).commit();
        }
        return true;
    };
}