package com.mb_lab.halal_cash.P2P.BuySellActions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.P2P.P2PActivity;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.pay.PaySend.PaySend_Successful;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Buy_Sell_Successfull_Activity extends AppCompatActivity {
    private final static String TAG = "Buy_Sell_Successfull_Activity";

    ViewLoadingAnimation viewLoadingAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_buy_sell_successfull);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        viewLoadingAnimation = new ViewLoadingAnimation(Buy_Sell_Successfull_Activity.this);
        try {

            ((TextView) findViewById(R.id.textPaySucc)).setText("Processing..");



            Bundle bundle = getIntent().getExtras();
            Log.d(TAG, "onCreate: trying getting Previously added data.. ");
            String adsType = bundle.getString("adsType");
            String adId = bundle.getString("adId");
            String payable_amount = bundle.getString("payable_amount");
            String pay_pin = bundle.getString("pay_pin");

            Log.d(TAG, "onCreate: adId:" + adId);

            confirmAds(adsType.toLowerCase(), Integer.parseInt(adId), Integer.parseInt(pay_pin) ,payable_amount);

        } catch (Exception e) {

//            viewLoadingAnimation.showLoading(false);
            Log.e(TAG, "onCreate: Failed to get Data from intent..", e);
            finish();
        }
        findViewById(R.id.textViewDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void confirmAds(@NonNull String adsType, @NonNull int adsId,  int pay_pin, @NonNull String payable_amount) {
        viewLoadingAnimation.showLoading(true);
        Log.d(TAG, "confirmAds: called..");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI.PostBuySellAds retrofitAPI = retrofit.create(RetrofitAPI.PostBuySellAds.class);

        // calling a method to create a post and passing our modal class.
        Call<ResponseBody> call = retrofitAPI.createPostForBuySellAds("Bearer " + new UserSessionManager(Buy_Sell_Successfull_Activity.this).getToken(), adsType, adsId, pay_pin, payable_amount);

        // on below line we are executing our method.
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: successful: ");

                Log.d(TAG, "onResponse: " + response.code());

                if (response.body() != null && response.isSuccessful()) {

                    // this method is called when we get response from our api.
                    Toast.makeText(Buy_Sell_Successfull_Activity.this, adsType + " Successful", Toast.LENGTH_LONG).show();
                    ((TextView) findViewById(R.id.textPaySucc)).setText("Successful");
                    viewLoadingAnimation.showLoading(false);
//                    startActivity(new Intent(Buy_Sell_Successfull_Activity.this, P2PActivity.class));
//                    finish();
                } else {
                    Toast.makeText(Buy_Sell_Successfull_Activity.this, adsType + " Not Successful", Toast.LENGTH_SHORT).show();
                    viewLoadingAnimation.showLoading(false);
                    finish();
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                finish();
            }
        });
    }
}