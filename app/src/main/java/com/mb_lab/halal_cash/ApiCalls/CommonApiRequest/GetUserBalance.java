package com.mb_lab.halal_cash.ApiCalls.CommonApiRequest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mb_lab.halal_cash.DataModels.UserWalletResponse;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserWalletManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserBalance extends RetrofitClient {

    private String bdt = "Loading..";
    private String gold = "Loading..";
    private String platinum = "Loading..";
    private String palladium = "Loading..";
    private String silver = "Loading..";
    private boolean status = false;


    public GetUserBalance(Context context, String TAG) {
        super(context,TAG);
        getWalletData();
    }

    private void getWalletData() {
        Log.d(TAG, "getWalletData called successful: ");

        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.WalletInformation retrofitAPI = retrofit.create(RetrofitAPI.WalletInformation.class);
        Call<UserWalletResponse> call = retrofitAPI.createGetHeaderForUserWalletInformation("Bearer " + TOKEN, TOKEN);

        // on below line we are executing our method.
        call.enqueue(new Callback<UserWalletResponse>() {
            @Override
            public void onResponse(Call<UserWalletResponse> call, Response<UserWalletResponse> response) {
                Log.d(TAG, "onResponse: successful: ");

                UserWalletResponse userWalletResponse = response.body();

 ;
                int responseCode = response.code();
                Log.d(TAG, "onResponse: Status Code: " + responseCode);

                if (userWalletResponse != null && response.isSuccessful()) {
                    try {
                        status = userWalletResponse.getStatus();

                        bdt = userWalletResponse.getWallet().getBdt();
                        gold = userWalletResponse.getWallet().getGold();
                        platinum = userWalletResponse.getWallet().getPlatinium();
                        palladium = userWalletResponse.getWallet().getPalladium();
                        silver = userWalletResponse.getWallet().getSilver();

                        Log.d(TAG, "onResponse: wallet Status: " + status);
                        Log.d(TAG, "onResponse: wallet bdt: " + bdt);
                        Log.d(TAG, "onResponse: wallet gold: " + gold);
                        new UserWalletManager(context).updateAllBalanceInfo(bdt,gold,platinum,palladium,silver);
                        Log.d(TAG, "onViewCreated:  balance BDT"+new UserWalletManager(context).getBdt());
                        Log.d(TAG, "onViewCreated:  balance UserWalletManager updated" );

                    } catch (Exception e) {
                        Toast.makeText(context, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onViewCreated:  balance UserWalletManager updated failed!!" );
                    }



                } else {
                    Log.d(TAG, "onResponse: Not successful: ");

                    // this method is called when we get response from our api.
                    Toast.makeText(context, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<UserWalletResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
//                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}
