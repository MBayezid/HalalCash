package com.mb_lab.halal_cash.home.homeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetBitmapFromUrl;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetGoldCurrentPrice;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetUserBalance;
import com.mb_lab.halal_cash.QRCodeActivity.ScanQRCodeActivity;


import com.mb_lab.halal_cash.deposit.DepositActivity;
import com.mb_lab.halal_cash.home.homeNotification.HomeNotificationActivity;
import com.mb_lab.halal_cash.mobileTopUp.MobileTopUp;
import com.mb_lab.halal_cash.P2P.P2PActivity;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.pay.PayActivity;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.sendCash.SendCash;
import com.mb_lab.halal_cash.SessionManagers.UserWalletManager;
import com.mb_lab.halal_cash.Util.MailSupport;
import com.mb_lab.halal_cash.home.homeProfile.HomeProfile;
import com.mb_lab.halal_cash.pay.PaySend.PaySend;

public class HomeFragment extends Fragment {

    private String bdt;
    private String gold;
    private String platinum;
    private String palladium;
    private String silver;
    private String imageUrl;
    private boolean status = false;
    private final String TAG = "HomeFragment";
    private UserWalletManager userWalletManager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        init();
        View view = inflater.inflate(R.layout.home_fragment, container, false);
//        ((TextInputLayout)view.findViewById(R.id.textFieldBalance)).getEndIconMode();
//        ((TextInputLayout)view.findViewById(R.id.textFieldBalance)).setEndIconActivated(false);
//        ((TextView) view.findViewById(R.id.textView6)).setText(bdt);
        ((TextInputEditText) view.findViewById(R.id.textView6)).setText(bdt);
        ((TextView) view.findViewById(R.id.textView2)).setText(gold);

        imageUrl = new UserSessionManager(getContext()).getImageUrl();
        Log.d(TAG, "onCreateView: ");

        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: HomeFragment View  Updated...");
        if (((TextInputLayout) view.findViewById(R.id.textFieldBalance)).getEndIconMode() != TextInputLayout.END_ICON_PASSWORD_TOGGLE) {
            Log.d(TAG, "onClick: " + ((TextInputLayout) view.findViewById(R.id.textFieldBalance)).END_ICON_PASSWORD_TOGGLE);
            //todo
        }

        view.findViewById(R.id.pay_icon_se).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PayActivity.class));
            }
        });
        view.findViewById(R.id.homeNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HomeNotificationActivity.class));
            }
        });


        view.findViewById(R.id.btnHomeProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HomeProfile.class));
            }
        });
        view.findViewById(R.id.deposit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DepositActivity.class));
            }
        });
        view.findViewById(R.id.btnHomePay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PaySend.class));
            }
        });
        view.findViewById(R.id.support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    new MailSupport(getActivity());


                } catch (Exception e) {
                    Log.e(TAG, "onClick: Erron on Calling Support..", e);
                }
            }
        });
        view.findViewById(R.id.p2p_icon_se).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), P2PActivity.class));
            }
        });
        view.findViewById(R.id.startScanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScanQRCodeActivity.class));
            }
        });
        view.findViewById(R.id.sendCash_icon_se).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SendCash.class));
            }
        });
        view.findViewById(R.id.mobileTopUp_icon_se).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MobileTopUp.class));
            }
        });

//        ((TextView) view.findViewById(R.id.textView2)).setText("12.12");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new GetUserBalance(getActivity(), TAG);
                    Thread.sleep(4000);
                    init();
                    ((TextView) view.findViewById(R.id.textView2)).post(() -> {

                        ((TextView) view.findViewById(R.id.textView2)).setText(gold);
                        Log.d(TAG, "onViewCreated: Thread called: Balance (Gold) Updated...");
                    });
                    ((TextView) view.findViewById(R.id.textView6)).post(() -> {

                        ((TextView) view.findViewById(R.id.textView6)).setText(bdt);
                        Log.d(TAG, "onViewCreated: Thread called: Balance (BDT) Updated...");
                    });

                    new GetGoldCurrentPrice(getActivity(), TAG);


//                    if (status) {
//                        ((TextView) view.findViewById(R.id.textView2)).setText(gold);
//                        ((TextView) view.findViewById(R.id.textView6)).setText(bdt);
//                        Log.d(TAG, "onViewCreated: Balance Updated...");
//                    }

                } catch (Exception e) {
                    Log.d(TAG, "onViewCreated: Failed to update Home View + Balance ...");
                }
            }
        }).start();

        try {
            // Preload images in the background

            Log.d(TAG, "onViewCreated: trying load image using glide()... Url is: " + imageUrl);
            new GetBitmapFromUrl(getActivity(), TAG).preLoadImageFromUrlAndLoad(imageUrl, ((ImageView) view.findViewById(R.id.btnHomeProfile)));
//            Glide.with(getActivity())
//                    .load(new UserSessionManager(getActivity()).getImageUrl()).centerCrop()
//                    .apply(new RequestOptions()
//                            .diskCacheStrategy(DiskCacheStrategy.ALL))
//                    .into((ImageView)view.findViewById(R.id.btnHomeProfile));

        } catch (Exception e) {
            Log.e(TAG, "onClick: failed to Load Home profile..", e);
        }

    }


    private void init() {
        userWalletManager = new UserWalletManager(getActivity());
        bdt = userWalletManager.getBdt();
        gold = userWalletManager.getGold();
        platinum = userWalletManager.getPlatinum();
        palladium = userWalletManager.getPalladium();
        silver = userWalletManager.getSilver();
    }

}
