package com.mb_lab.halal_cash.home.homeFragments;

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
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserWalletManager;

public class WalletFragment extends Fragment {
    private String TOKEN = null;
    private final String TAG = "WalletFragment";

    private String bdt;
    private String gold;
    private String platinum;
    private String palladium;
    private String silver;
    private UserWalletManager userWalletManager;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + TOKEN);

        userWalletManager = new UserWalletManager(requireContext());
        bdt = userWalletManager.getBdt();
        gold = userWalletManager.getGold();
        platinum = userWalletManager.getPlatinum();
        palladium = userWalletManager.getPalladium();
        silver = userWalletManager.getSilver();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.walle_fragment, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) view.findViewById(R.id.topBalance)).setText(bdt);//todo change on Asset type



        ((TextView) view.findViewById(R.id.textView61)).setText(gold);
        ((TextView) view.findViewById(R.id.textView611)).setText(silver);
        ((TextView) view.findViewById(R.id.textView612)).setText(palladium);
        ((TextView) view.findViewById(R.id.textView613)).setText(platinum);




        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    new GetUserBalance(requireContext(),TAG);
                    Log.d(TAG, "WalletFragment: trying update wallet");
                    Log.d(TAG, "onViewCreated: Thread called: delay 3 secs..");
                    Thread.sleep(3000);

                    ((TextView) view.findViewById(R.id.textView61)).post(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) view.findViewById(R.id.textView61)).setText(gold);
                        }
                    });
                    ((TextView) view.findViewById(R.id.textView611)).post(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) view.findViewById(R.id.textView611)).setText(silver);
                        }
                    });
                    ((TextView) view.findViewById(R.id.textView612)).post(new Runnable() {
                        @Override
                        public void run() {

                            ((TextView) view.findViewById(R.id.textView612)).setText(palladium);
                        }
                    });
                    ((TextView) view.findViewById(R.id.textView613)).post(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) view.findViewById(R.id.textView613)).setText(platinum);
                        }
                    });



                } catch (Exception e) {
                    Log.d(TAG, "onViewCreated: Failed to update Home View + Balance ...");
                    Log.e(TAG, "onCreate: Balance update Failed!!", e);
                }
            }
        }).start();

    }


}
