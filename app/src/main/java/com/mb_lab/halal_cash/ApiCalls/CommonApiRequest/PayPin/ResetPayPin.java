package com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.PayPin;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.RetrofitClient;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.SimpleResponse;
import com.mb_lab.halal_cash.PayPin.ResetPayPin0;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetPayPin extends RetrofitClient {
    ViewLoadingAnimation viewLoadingAnimation;

    public ResetPayPin(Context context, String TAG) {
        super(context, TAG);
        viewLoadingAnimation = new ViewLoadingAnimation(context);
    }

    public void call(final OnPayPinReset listener, String PayPin) {

        viewLoadingAnimation.showLoading(true);
        Log.d(TAG, "ResetPayPin: Started...");
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.SetPayPin validReceiverInformation = retrofit.create(RetrofitAPI.SetPayPin.class);
        Call<SimpleResponse> asynCall = validReceiverInformation.createGetSetPayPin("Bearer " + new UserSessionManager(context).getToken(), PayPin);

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(@NonNull Call<SimpleResponse> call, @NonNull Response<SimpleResponse> response) {
                Log.d(TAG, "onResponse: ");
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: response Successful..");


                    viewLoadingAnimation.showLoading(false);
                    // 3/14/2024  Pay Pin Successfully Recorded
                    Toast.makeText(context, "Pay Pin Successfully Recorded.", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(PaySend_2.this,PayPin0.class));
                    listener.onSuccess(true);

                } else {
                    // 3/14/2024  Pay Pin not Recorded.
                    viewLoadingAnimation.showLoading(false);
                    Toast.makeText(context, "Pay Pin not Recorded.", Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Something went wrong!\nContact Support...", Toast.LENGTH_LONG).show();
                    listener.onSuccess(false);
                }


            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(context, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
                listener.onSuccess(false);

            }
        });
    }

    public interface OnPayPinReset {
        void onSuccess(boolean isSuccess);
    }
}
