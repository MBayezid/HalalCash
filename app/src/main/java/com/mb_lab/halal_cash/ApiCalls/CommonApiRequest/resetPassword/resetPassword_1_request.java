package com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.resetPassword;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.RetrofitClient;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.GetGoldPriceResponse;
import com.mb_lab.halal_cash.DataModels.SimpleResponse;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserCurrentGoldPrice;
import com.mb_lab.halal_cash.Util.AutoInsertOTP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;


public class resetPassword_1_request extends RetrofitClient {
    ViewLoadingAnimation viewLoadingAnimation;

    public resetPassword_1_request(Context context, String TAG) {
        super(context, TAG);
        viewLoadingAnimation = new ViewLoadingAnimation(context);
    }

    public void call(String user_id_type, String user_id, View view) {

        viewLoadingAnimation.showLoading(true);
        Log.d(TAG, "resetPassword_1_request() called... ");

        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<SimpleResponse> call = retrofitAPI.createPostForResetPassword_1_Request(user_id_type, user_id);

        // on below line we are executing our method.
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                Log.d(TAG, "onResponse: successful: ");


                Log.d(TAG, "onResponse: resetPassword_1_request Status Code: " + response.code());

                if (response.body() != null && response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String message = response.body().getMessage();
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    viewLoadingAnimation.showLoading(false);

                    if (status){
                    new AutoInsertOTP(message, view);
                    }else {

                    }
                } else {
                    Log.d(TAG, "onResponse:  resetPassword_1_request  failed!!" + response.body().getMessage());
                    viewLoadingAnimation.showLoading(false);
                    // this method is called when we get response from our api.
//                    Toast.makeText(context, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                Log.d(TAG, "onResponse:  gold price Not successful: ");
                viewLoadingAnimation.showLoading(false);
                Toast.makeText(context, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
//                responseTV.setText("Error found is : " + t.getMessage());
            }
        });

    }

}
