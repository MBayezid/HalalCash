package com.mb_lab.halal_cash.P2P.CreateAds;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.mb_lab.halal_cash.Enums.AdsType;
import com.mb_lab.halal_cash.Enums.AssetTypeEnums;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserCurrentGoldPrice;


public class CreateNormalAdsFragmentOne extends Fragment {
    private final String TAG = "CreateNormalAdsFragmentOne";

    private SharedViewModel viewModel;
    boolean error = true;
    private TabLayout tabLayout;
    private int AdType = AdsType.BUY.getValue();
    private float goldUnitPriceLow;
    private float goldUnitPriceHigh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Observe data changes in ViewModel

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Update data ");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_normal_add_fragment_1, container, false);
        try {
            goldUnitPriceLow = Float.parseFloat(new UserCurrentGoldPrice(requireActivity()).getGoldUnitPriceLow());
            goldUnitPriceHigh = Float.parseFloat(new UserCurrentGoldPrice(requireActivity()).getGoldUnitPriceHigh());
            ((TextView) view.findViewById(R.id.textView444)).setText(goldUnitPriceLow + " To " + goldUnitPriceHigh + " BDT");
        } catch (Exception e) {
            ((TextView) view.findViewById(R.id.textView444)).setText("00.00" + " To " + "00.00" + " BDT");
            Log.e(TAG, "onCreateView: ", e);
        }


        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tabLayout);
        // Set custom tab indicator
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Log.d(TAG, "onTabSelected: 1 Buy");
                        AdType = AdsType.BUY.getValue();

                        break;
                    case 1:
                        Log.d(TAG, "onTabSelected: 2 Sell");
                        AdType = AdsType.SELL.getValue();


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        view.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.setSharedData("ad_type", String.valueOf(AdType));

                viewModel.setSharedData("asset_type", String.valueOf(AssetTypeEnums.GOLD.getValue()));//Gold (fixed for now)

                try {
                    float user_price = Float.parseFloat(((EditText) view.findViewById(R.id.textView44)).getText().toString().trim());
                    if (user_price >= goldUnitPriceLow && user_price <= goldUnitPriceHigh) {
                        error = true;
                        viewModel.setSharedData("user_price", String.valueOf(user_price));
                        Log.d(TAG, "onClick: user_price updated : " + user_price);
                    } else {
                        error = false;
                        ((EditText) view.findViewById(R.id.textView44)).setError("Insert Gold unit price fits between range.");
                        ((EditText) view.findViewById(R.id.textView44)).setText(null);
                    }
                } catch (Exception e) {
                    error = false;
                    Log.e(TAG, "onPause: ", e);
//                    viewModel.setSharedData("user_price", "00.00");
                    ((TextView) view.findViewById(R.id.textView44)).setError("Enter Appropriate Amount.");
                }
                viewModel.setSharedData("price_type", "42");// Fixed (fixed for now)//todo create enum
                viewModel.setSharedData("payable_with", String.valueOf(AssetTypeEnums.BDT.getValue()));//BDT (fixed for now)


                if (error) {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                    R.anim.slide_out_right, R.anim.slide_in_left)
                            .addToBackStack(null)
                            .replace(R.id.fragment_container, new CreateNormalAdsFragmentTwo())
                            .commit();
                    Log.d(TAG, "onClick: Successfully committed..");
                } else Log.d(TAG, "onClick: Error committed..");

            }
        });

    }
}
