package com.mb_lab.halal_cash.P2P.BuySellActions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.PayPin.CheckPayPinStatus;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.UserAdsResponse;
import com.mb_lab.halal_cash.Enums.AdsType;
import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
import com.mb_lab.halal_cash.Enums.TransactionTypeEnums;
import com.mb_lab.halal_cash.PayPin.PayPin0;
import com.mb_lab.halal_cash.PayPin.ResetPayPin0;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserCurrentGoldPrice;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.Util.CopyTextToClipBoard;
import com.mb_lab.halal_cash.pay.PaySend.PaySend_2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Buy_Sell_Activity extends AppCompatActivity {
    private final String TAG = "Buy_Sell_Activity";
    String adsType;
    String adId;
    String userUnitPrice;
    String limitMin;
    String limitMax;
    String totalGoldForSell;
    String currentGoldPrice;
    String orderId;
    ViewLoadingAnimation viewLoadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buy_sell);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left + 20, systemBars.top + 20, systemBars.right + 20, systemBars.bottom + 20);
            return insets;
        });

        viewLoadingAnimation = new ViewLoadingAnimation(Buy_Sell_Activity.this);
        try {
            UserAdsResponse.Data data = (UserAdsResponse.Data) getIntent().getSerializableExtra("data");

            adId = data.getId().toString();

            if (Integer.parseInt(data.getAd_type()) == AdsType.BUY.getValue()) {
                adsType = new ReturnEnumInString().getEnumToString(AdsType.SELL.getValue());
            } else {
                adsType = new ReturnEnumInString().getEnumToString(AdsType.BUY.getValue());
            }

            Log.d(TAG, "onCreate: adId and adType: " + adId + " " + adsType);
            userUnitPrice = String.format("%.2f", Float.parseFloat(data.getUser_price()));
            limitMin = String.format("%.2f", Float.parseFloat(data.getOrder_limit_min()));
            limitMax = String.format("%.2f", Float.parseFloat(data.getOrder_limit_max()));
            totalGoldForSell = String.format("%.2f", Float.parseFloat(data.getAdvertise_total_amount()));
            currentGoldPrice = new UserCurrentGoldPrice(Buy_Sell_Activity.this).getGoldUnitPriceLow();
            orderId = data.getAds_unique_num();

            ((TextView) findViewById(R.id.textView70)).setText(adsType + " GOLD");
            ((TextView) findViewById(R.id.actionBtn)).setText(adsType + " GOLD");
            ((TextView) findViewById(R.id.textView71)).setText(currentGoldPrice + " TK");
            ((TextView) findViewById(R.id.textView76)).setText("Limit Tk. " + limitMin + " - Tk. " + limitMax);
            ((TextView) findViewById(R.id.totalAmount)).setText("-- " + totalGoldForSell + " gm");
            ((TextView) findViewById(R.id.faitAmount)).setText("-- " + userUnitPrice);

        } catch (Exception exception) {
            finish();
        }
        ((TextView) findViewById(R.id.actionBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payable_amount;
                try {
                    payable_amount = ((EditText) findViewById(R.id.textView74)).getText().toString().trim();
                    if (!payable_amount.isEmpty()) {
                        if (Float.parseFloat(payable_amount) <= Float.parseFloat(limitMax) && Float.parseFloat(payable_amount) >= Float.parseFloat(limitMin)) {
                            viewSendingDetails(adsType, payable_amount, orderId);
                        } else {
                            ((EditText) findViewById(R.id.textView74)).setError("Exceed limits");
                        }
                    } else
                        ((EditText) findViewById(R.id.textView74)).setError("Insert BDT equivalent to GOLD");

                } catch (Exception exception) {
                    Log.e(TAG, "onClick: ", exception);
                }
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void viewSendingDetails(String adsType, String payable_amount, String orderId) {

        BottomSheetDialog dialog = new BottomSheetDialog(Buy_Sell_Activity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_detail_view_before_buy_sell_action);
//        TODO AsserTypeEnum must return Currency in String

        ((TextView) dialog.findViewById(R.id.adsType)).setText(adsType);
        float priceInBdt = Float.parseFloat(payable_amount) / Float.parseFloat(userUnitPrice);
        ((TextView) dialog.findViewById(R.id.data_1)).setText(String.format("%.2f", priceInBdt) + " gm Gold");
        ((TextView) dialog.findViewById(R.id.data_2)).setText(payable_amount + " BDT");
        ((TextView) dialog.findViewById(R.id.data_3)).setText(orderId);


        dialog.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.payId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CopyTextToClipBoard(Buy_Sell_Activity.this, "Pay ID", ((TextView) dialog.findViewById(R.id.data_3)).getText().toString().trim());
            }
        });
        dialog.findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.textViewContinue).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //                confirmAds(adsType.toLowerCase(), Integer.parseInt(adId), payable_amount);
                CheckPayPinStatus checkPayPinStatus = new CheckPayPinStatus(Buy_Sell_Activity.this, TAG);
                checkPayPinStatus.call(new CheckPayPinStatus.OnCheckPayPinStatusListener() {
                    @Override
                    public void onSuccess(boolean isSuccess) {
                        if (isSuccess) {
                            // Handle success
                            Intent intent = new Intent(Buy_Sell_Activity.this, ConfirmPayPin.class);
                            intent.putExtra("adsType", adsType);
                            intent.putExtra("adId", adId);
                            intent.putExtra("payable_amount", payable_amount);
                            startActivity(intent);
                            finish();
//                            confirmAds(adsType.toLowerCase(), Integer.parseInt(adId), payable_amount);
                        } else {
                            // Handle failure
                            startActivity(new Intent(Buy_Sell_Activity.this, ResetPayPin0.class));
                        }
                    }
                });


                return false;
            }
        });

        dialog.create();
        dialog.show();
    }
//
//    private void confirmAds(@NonNull String adsType, @NonNull int adsId, @NonNull String payable_amount) {
//        viewLoadingAnimation.showLoading(true);
//        Log.d(TAG, "confirmAds: called..");
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RetrofitAPI.PostBuySellAds retrofitAPI = retrofit.create(RetrofitAPI.PostBuySellAds.class);
//        int pay_pin = 12345;
//        // calling a method to create a post and passing our modal class.
//        Call<ResponseBody> call = retrofitAPI.createPostForBuySellAds("Bearer " + new UserSessionManager(Buy_Sell_Activity.this).getToken(), adsType, adsId, pay_pin, payable_amount);
//
//        // on below line we are executing our method.
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d(TAG, "onResponse: successful: ");
//
//                Log.d(TAG, "onResponse: " + response.code());
//
//                if (response.body() != null && response.isSuccessful()) {
//
//                    // this method is called when we get response from our api.
//                    Toast.makeText(Buy_Sell_Activity.this, adsType + " Successful", Toast.LENGTH_LONG).show();
//                    viewLoadingAnimation.showLoading(false);
//                    startActivity(new Intent(Buy_Sell_Activity.this, Buy_Sell_Successfull_Activity.class));
//                    finish();
//                } else {
//                    Toast.makeText(Buy_Sell_Activity.this, adsType + " Not Successful", Toast.LENGTH_SHORT).show();
//                    viewLoadingAnimation.showLoading(false);
//                    finish();
//                }
//
//            }
//
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                // setting text to our text view when
//                // we get error response from API.
//                Log.e(TAG, "onFailure: " + t.getMessage());
//                finish();
//            }
//        });
//    }
}