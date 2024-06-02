package com.mb_lab.halal_cash.P2P.CreateAds;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.mb_lab.halal_cash.R;


public class PostNormalAds extends AppCompatActivity {
    protected int position = 1; // Set this based on your condition


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_normal_ads);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left + 20, systemBars.top + 20, systemBars.right + 20, systemBars.bottom + 20);
            v.setBackgroundColor(getColor(R.color.black));
            return insets;
        });


//        updateUi(position);
        loadFragment(new CreateNormalAdsFragmentOne());
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                position++;
////                updateUi(position);
//            }
//        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
//        position--;
//        if (position >= 1) {
//            updateUi(position);
//        } else {
//            finish();
//        }
    }

    private void updateUi(int position) {

//        Fragment newFragment = new CreateNormalAdsFragmentOne();
        // Load the appropriate fragment based on the condition
        if (position == 1) {
            loadFragment(new CreateNormalAdsFragmentOne());

        } else if (position == 2) {

            loadFragment(new CreateNormalAdsFragmentTwo());
        } else if (position == 3) {

            loadFragment(new CreateNormalAdsFragmentThree());
        } else if (position == 4) {

            Toast.makeText(getBaseContext(), "Submited", Toast.LENGTH_LONG).show();
        }


    }

    private void loadFragment(Fragment newFragment) {

        getSupportFragmentManager()

                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_out_right, R.anim.slide_in_left)
                .addToBackStack(null)
                .replace(R.id.fragment_container, newFragment)
                .commit();
    }
}