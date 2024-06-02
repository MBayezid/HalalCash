package com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.resetPassword;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.RetrofitClient;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.SimpleResponse;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.Util.AutoInsertOTP;
import com.mb_lab.halal_cash.resetPassword.SetNewPasswordActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class resetPassword_2_verifyOtp extends RetrofitClient {
    ViewLoadingAnimation viewLoadingAnimation;

    public resetPassword_2_verifyOtp(Context context, String TAG) {
        super(context, TAG);
        viewLoadingAnimation = new ViewLoadingAnimation(context);
    }

    public void call(String user_id, String user_id_type, String code) {

        viewLoadingAnimation.showLoading(true);
        Log.d(TAG, "resetPassword_2_verifyOtp() called... ");

        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<SimpleResponse> call = retrofitAPI.createPostForResetPassword_2_VerifyOtp(user_id, user_id_type, code);

        // on below line we are executing our method.
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                Log.d(TAG, "onResponse: successful: ");


                Log.d(TAG, "onResponse: resetPassword_2_verifyOtp Status Code: " + response.code());

                if (response.body() != null && response.isSuccessful()) {
                    String message = response.body().getMessage();
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                    new AutoInsertOTP(message, view);
                    viewLoadingAnimation.showLoading(false);
                    Log.d(TAG, "onResponse: goTo SetNewPasswordActivity.class");


                    //goTo SetNewPasswordActivity.class
                    context.startActivity(new Intent(context, SetNewPasswordActivity.class));
                } else {
                    Log.d(TAG, "onResponse:  resetPassword_2_verifyOtp  failed!!");
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
