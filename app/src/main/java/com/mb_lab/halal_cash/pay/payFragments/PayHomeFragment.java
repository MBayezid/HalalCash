package com.mb_lab.halal_cash.pay.payFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetUserBalance;
import com.mb_lab.halal_cash.QRCodeActivity.MyQRCodeMainActivity;
import com.mb_lab.halal_cash.QRCodeActivity.ScanQRCodeActivity;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.SessionManagers.UserWalletManager;
import com.mb_lab.halal_cash.Util.CopyTextToClipBoard;
import com.mb_lab.halal_cash.Util.MailSupport;
import com.mb_lab.halal_cash.pay.PayProfile;
import com.mb_lab.halal_cash.pay.PaySend.PaySend;
import com.mb_lab.halal_cash.pay.payGift.PayGiftActivity;

public class PayHomeFragment extends Fragment {


    private String bdt;
    private String gold;
    private String platinum;
    private String palladium;
    private String silver;
    private boolean status = false;
    private final String TAG = "PayHomeFragment";
    private UserWalletManager userWalletManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        userWalletManager = new UserWalletManager(getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.pay_home_fragment, container, false);
        ((TextView) view.findViewById(R.id.textView21)).setText(bdt);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.pay_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PaySend.class));
            }
        });
        view.findViewById(R.id.support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MailSupport(getActivity());
            }
        });
        view.findViewById(R.id.btnPayProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PayProfile.class));
            }
        });
        view.findViewById(R.id.p2p_receive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyQRCodeMainActivity.class));
            }
        });
        view.findViewById(R.id.pay_scanToPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ScanQRCodeActivity.class));
            }
        });
        view.findViewById(R.id.pay_gift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PayGiftActivity.class));
            }
        });

        ((TextView) view.findViewById(R.id.textView5)).setText(new UserSessionManager(getActivity()).getAllUserInfo().get(new UserSessionManager(getActivity()).KEY_PAY_ID).toString());

        view.findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CopyTextToClipBoard(requireContext(), "Own Pay ID", new UserSessionManager(getActivity()).getAllUserInfo().get(new UserSessionManager(getActivity()).KEY_PAY_ID).toString());
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    new GetUserBalance(requireContext(), TAG);
                    init();
                    Thread.sleep(3000);

                    ((TextView) view.findViewById(R.id.textView21)).post(() -> {

                        ((TextView) view.findViewById(R.id.textView21)).setText(bdt);
                        Log.d(TAG, "onViewCreated: Thread called: Balance (BDT) Updated...");
                    });


                } catch (Exception e) {
                    Log.d(TAG, "onViewCreated: Failed to update Home View + Balance ...");
                }
            }
        }).start();
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
