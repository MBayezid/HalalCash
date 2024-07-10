package com.mb_lab.halal_cash.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetBitmapFromUrl;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetGoldCurrentPrice;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetUserBalance;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.SplashActivity;
import com.mb_lab.halal_cash.home.homeFragments.ComingSoonFragment;
import com.mb_lab.halal_cash.home.homeFragments.FutureFragment;
import com.mb_lab.halal_cash.home.homeFragments.HomeFragment;
import com.mb_lab.halal_cash.home.homeFragments.MarketFragment;
import com.mb_lab.halal_cash.home.homeFragments.TrendFragment;
import com.mb_lab.halal_cash.home.homeFragments.WalletFragment;


public class Home extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private final String TAG = "HomeActivity";
    private String TOKEN = null;

    private UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);
        userSessionManager = new UserSessionManager(Home.this);
        TOKEN = userSessionManager.getToken();
        try {

            Log.d(TAG, "onCreate: " + TOKEN);
            if (TOKEN == null) {
                Toast.makeText(Home.this, "Error.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Home.this, SplashActivity.class));
                finish();
            }
        } catch (Exception e) {

        }

//        try {
//            // Preload images in the background
//
//            new GetBitmapFromUrl(Home.this, TAG).preLoadImageFromUrlAndLoad(new UserSessionManager(Home.this).getImageUrl(), ((ImageView)view.findViewById(R.id.btnHomeProfile)));
////            Glide.with(Home.this)
////                    .load(Home.this)
////                    .apply(new RequestOptions().centerCrop()
////                            .diskCacheStrategy(DiskCacheStrategy.ALL)
////                            .override(100, 100)) // Adjust size as needed
////                    .preload();
//
//        } catch (Exception e) {
//            Log.e(TAG, "onClick: failed to Load Home profile..", e);
//        }

        bottomNavigationView = findViewById(R.id.home_bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new HomeFragment()).commit();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
////                    new GetUserBalance(Home.this, TAG);
////                    new GetGoldCurrentPrice(Home.this, TAG);
//
//
//                } catch (Exception e) {
//                    Log.e(TAG, "onViewCreated: Failed to update GetUserBalance + GetUserBalance ...",e);
//                }
//            }
//        }).start();

    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        // By using switch we can easily get
        // the selected fragment
        // by using there id.
        Fragment selectedFragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.action_home) {
            selectedFragment = new HomeFragment();
            bottomNavigationView.setBackgroundColor(getColor(R.color.black));
        } else if (itemId == R.id.action_market) {
//            selectedFragment = new MarketFragment();
            selectedFragment = new ComingSoonFragment();
            bottomNavigationView.setBackgroundColor(getColor(R.color.black));

        } else if (itemId == R.id.action_trend) {
//            selectedFragment = new TrendFragment();
            selectedFragment = new ComingSoonFragment();
            bottomNavigationView.setBackgroundColor(getColor(R.color.black));

        } else if (itemId == R.id.action_future) {
//            selectedFragment = new FutureFragment();
            selectedFragment = new ComingSoonFragment();
            bottomNavigationView.setBackgroundColor(getColor(R.color.black));

        } else if (itemId == R.id.action_wallet) {
            selectedFragment = new WalletFragment();
            bottomNavigationView.setBackgroundColor(getColor(R.color.gray));
        }
        // It will help to replace the
        // one fragment to other.
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, selectedFragment).commit();
//            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_out_left,R.anim.slide_in_right).replace(R.id.home_fragment_container, selectedFragment).commit();
        }

        return true;
    };

}