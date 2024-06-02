package com.mb_lab.halal_cash.P2P;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetGoldCurrentPrice;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetUserBalance;
import com.mb_lab.halal_cash.P2P.p2pFragments.P2pAdsFragment;
import com.mb_lab.halal_cash.P2P.p2pFragments.P2pHomeFragment;
import com.mb_lab.halal_cash.P2P.p2pFragments.P2pOrderFragment;
import com.mb_lab.halal_cash.P2P.p2pFragments.P2pProfileFragment;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

public class P2PActivity extends AppCompatActivity {

    private static final String TAG = "P2PActivity";
    private BottomNavigationView bottomNavigationView;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2p_pactivity);
        userSessionManager = new UserSessionManager(P2PActivity.this);
        bottomNavigationView = findViewById(R.id.p2p_bottomNavigationView);

        updateUi(userSessionManager.getUserType().toLowerCase().trim(), bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.p2p_fragment_container, new P2pHomeFragment(P2PActivity.this)).commit();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    new GetUserBalance(P2PActivity.this, TAG);
                    new GetGoldCurrentPrice(P2PActivity.this, TAG);


                } catch (Exception e) {
                    Log.e(TAG, "onViewCreated: Failed to update GetUserBalance + GetUserBalance ...",e);
                }
            }
        }).start();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        // By using switch we can easily get
        // the selected fragment
        // by using there id.
        Fragment selectedFragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.action_home) {
            selectedFragment = new P2pHomeFragment(P2PActivity.this);
//            selectedFragment.getView().findViewById(R.id.bellNotification).setVisibility(View.GONE);
            bottomNavigationView.setBackgroundColor(getColor(R.color.black));
        } else if (itemId == R.id.action_ads) {
            selectedFragment = new P2pAdsFragment();

        } else if (itemId == R.id.action_order) {
            selectedFragment = new P2pOrderFragment();

        } else if (itemId == R.id.action_profile) {
            selectedFragment = new P2pProfileFragment();

        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.p2p_fragment_container, selectedFragment).commit();
        }

        return true;
    };

    private void updateUi(String AccountType, View view) {
        Log.d(TAG, "updateUi: UserType : "+AccountType);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //        view.findViewById(R.id.p2p_bottomNavigationView).setVisibility(View.GONE);
                if (AccountType.equals("agent")) {
                    view.setVisibility(View.VISIBLE);


                } else {
                    view.setVisibility(View.GONE);
                }
            }
        }).start();
    }
}