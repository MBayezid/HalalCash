package com.mb_lab.halal_cash.ApiCalls.CommonApiRequest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mb_lab.halal_cash.DataModels.GetGoldPriceResponse;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserCurrentGoldPrice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetGoldCurrentPrice extends RetrofitClient{
   public GetGoldCurrentPrice(Context context, String TAG) {
        super(context, TAG);
        getCurrentGoldPrice();
    }

    private void getCurrentGoldPrice() {
        Log.d(TAG, "getCurrentGoldPrice() called... ");

        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.GetCurrentGoldPriceUrl retrofitAPI = retrofit.create(RetrofitAPI.GetCurrentGoldPriceUrl.class);
        Call<GetGoldPriceResponse> call = retrofitAPI.createGetCurrentGoldPrice("Bearer " + TOKEN);

        // on below line we are executing our method.
        call.enqueue(new Callback<GetGoldPriceResponse>() {
            @Override
            public void onResponse(Call<GetGoldPriceResponse> call, Response<GetGoldPriceResponse> response) {
                Log.d(TAG, "onResponse: successful: ");


                Log.d(TAG, "onResponse: getCurrentGoldPrice Status Code: " + response.code());

                if (response.body() != null && response.isSuccessful()) {
                      Log.d(TAG, "onResponse: got gold price successfully..");
                      try {
                          new UserCurrentGoldPrice(context).updateCurrentGoldPrice(response.body().getDate(),response.body().getPrice(),response.body().getHighest_price());
                          Log.d(TAG, "onResponse: current Gold Price Recorded: date: "+response.body().getDate());
                      }catch (Exception e){
                          Log.e(TAG, "onResponse: current Gold Price Recording failed :", e);
                      }

                } else {

                    Log.d(TAG, "onResponse:  gbalance getCurrentGoldPrice updated failed!!");

                    // this method is called when we get response from our api.
//                    Toast.makeText(context, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<GetGoldPriceResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                Log.d(TAG, "onResponse:  gold price Not successful: ");
                Toast.makeText(context, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
//                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}
