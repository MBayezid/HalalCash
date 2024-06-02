package com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.PayPin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.RetrofitClient;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.SimpleResponse;
import com.mb_lab.halal_cash.DataModels.UserLoginResponse;
import com.mb_lab.halal_cash.Enums.TransactionTypeEnums;
import com.mb_lab.halal_cash.PayPin.PayPin0;
import com.mb_lab.halal_cash.PayPin.ResetPayPin0;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.pay.PaySend.PaySend_2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckPayPinStatus extends RetrofitClient {
    ViewLoadingAnimation viewLoadingAnimation;

    public CheckPayPinStatus(Context context, String TAG) {
        super(context, TAG);
        viewLoadingAnimation = new ViewLoadingAnimation(context);
    }

    public boolean call(final OnCheckPayPinStatusListener listener) {

        viewLoadingAnimation.showLoading(true);

        Log.d(TAG, "CheckPayPinStatus called successful....... ");

        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.CheckPayPinStatus checkPayPinStatus = retrofit.create(RetrofitAPI.CheckPayPinStatus.class);
        Call<SimpleResponse> asynCall = checkPayPinStatus.createGetCheckPayPinStatus("Bearer " + new UserSessionManager(context).getToken());

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<SimpleResponse>() {

            public void onResponse(@NonNull Call<SimpleResponse> call, @NonNull Response<SimpleResponse> response) {
                Log.d(TAG, "onResponse: ");
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: response Successful..");

//                    Toast.makeText(context, "Insert your 5 digit Pay Pin.", Toast.LENGTH_SHORT).show();

                    viewLoadingAnimation.showLoading(false);
                    if (response.body().getStatus()) {
                        // call listener method indicating success
                        listener.onSuccess(true);

                    } else {
                        // call listener method indicating success
                        listener.onSuccess(false);
                    }
                } else {
                    viewLoadingAnimation.showLoading(false);
                    Toast.makeText(context, "Update your Pay Pin First.", Toast.LENGTH_SHORT).show();
                    // call listener method indicating failure
                    listener.onSuccess(false);
                }
            }

            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                viewLoadingAnimation.showLoading(false);
                Toast.makeText(context, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
                // call listener method indicating failure
                listener.onSuccess(false);
            }
        });
        return false;
    }

//    public boolean call() {
//
//        viewLoadingAnimation.showLoading(true);
//
//        Log.d(TAG, "CheckPayPinStatus called successful....... ");
//
//        // below line is to create an instance for our retrofit api class.
//        RetrofitAPI.CheckPayPinStatus checkPayPinStatus = retrofit.create(RetrofitAPI.CheckPayPinStatus.class);
//        Call<SimpleResponse> asynCall = checkPayPinStatus.createGetCheckPayPinStatus("Bearer " + new UserSessionManager(context).getToken());
//
//        // on below line we are executing our method.
//        Callback callback=new Callback<SimpleResponse>() {
//
//            public void onResponse(@NonNull Call<SimpleResponse> call, @NonNull Response<SimpleResponse> response) {
//                Log.d(TAG, "onResponse: ");
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d(TAG, "onResponse: response Successful..");
//
//                    Toast.makeText(context, "Insert your 5 digit Pay Pin.", Toast.LENGTH_SHORT).show();
//
//
//                    viewLoadingAnimation.showLoading(false);
////                    context.startActivity(intent);
//                } else {
////                    Log.d(TAG, "onResponse: response Error..: " + response.body().getMessage());
//                    // TODO: 3/11/2024 make user set his pay pin for the first time
//                    viewLoadingAnimation.showLoading(false);
//                    Toast.makeText(context, "Update your Pay Pin First.", Toast.LENGTH_SHORT).show();
//
////                    context.startActivity(intent);
//
//
//                }
//
//
//
//            }
//            public void onFailure(Call<SimpleResponse> call, Throwable t) {
//                // setting text to our text view when
//                // we get error response from API.
//                Log.e(TAG, "onFailure: " + t.getMessage());
//                viewLoadingAnimation.showLoading(false);
//                Toast.makeText(context, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
////                ((EditText) findViewById(R.id.editText)).setError("Network Error.");
//
//            }
//        };
//        asynCall.enqueue(callback);
//        return false;
//
//    }

    public interface OnCheckPayPinStatusListener {
        void onSuccess(boolean isSuccess);
    }

}
