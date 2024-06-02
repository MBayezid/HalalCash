package com.mb_lab.halal_cash.pay.PaySend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.GetReceiverInformationResponse;
import com.mb_lab.halal_cash.Enums.UserIdTypeEnums;
import com.mb_lab.halal_cash.Registration_Verification.VerificationAccount;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaySend extends AppCompatActivity {
    private static final String TAG = "PaySend";
    private String user_id_type = "Email";

    private TextView btnEmail, btnPhone, btnPayId;
    private EditText editTextUserId;
    private UserSessionManager userSessionManager;
    private TabLayout tabLayout;
    ViewLoadingAnimation viewLoadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_send);
        viewLoadingAnimation = new ViewLoadingAnimation(PaySend.this);

        userSessionManager = new UserSessionManager(PaySend.this);

        viewLoadingAnimation.showLoading(false);
        user_id_type = UserIdTypeEnums.EMAIL.getValue();

        tabLayout = findViewById(R.id.tabLayouts);
        editTextUserId = findViewById(R.id.textView2111);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                editTextUserId.setHint(tab.getText().toString() + " of Receiver.");

                switch (tab.getPosition()) {
                    case 0:
                        user_id_type = UserIdTypeEnums.EMAIL.getValue();
                        ((TextView) findViewById(R.id.textViewStatus)).setVisibility(View.GONE);
                        break;
                    case 1:
                        user_id_type = UserIdTypeEnums.PHONE.getValue();
                        ((TextView) findViewById(R.id.textViewStatus)).setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.textViewStatus)).setText("Receiver can find Pay ID under Wallets - Funding - Pay");
                        break;
                    case 2:
                        user_id_type = UserIdTypeEnums.PAYID.getValue();
                        ((TextView) findViewById(R.id.textViewStatus)).setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.textViewStatus)).setText("Receiver can find Pay ID under Home - Top-left - Avatar");

                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        findViewById(R.id.textViewContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo check userIdType and idText and make sure both are correlated
                String userId = editTextUserId.getText().toString();
                if (userId != null && userId.length() >= 8 && user_id_type != null) {

                    getValidReceiverInformation(user_id_type, userId);

                } else {
                    editTextUserId.setError("Invalid user id.");
                }
            }
        });


    }


    private void getValidReceiverInformation(String user_id_type, String value) {

        viewLoadingAnimation.showLoading(true);

        Log.d(TAG, "getValidReceiverInformation called successful....... ");
        // below line is for displaying our progress bar.
//        loadingPB.setVisibility(View.VISIBLE);

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.ValidReceiverInformation validReceiverInformation = retrofit.create(RetrofitAPI.ValidReceiverInformation.class);
        Call<GetReceiverInformationResponse> asynCall = validReceiverInformation.createGetValidReceiverInformation("Bearer " + userSessionManager.getToken(), user_id_type, value);

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<GetReceiverInformationResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetReceiverInformationResponse> call, @NonNull Response<GetReceiverInformationResponse> response) {
                Log.d(TAG, "onResponse: ");
                Log.d(TAG, "onResponse: response Code ..:" + response.code());
                GetReceiverInformationResponse userLoginResponse = response.body();
                if (userLoginResponse != null && response.isSuccessful()) {
                    try {
//                            Log.d(TAG, "onResponse: user Data=> " + userLoginResponse.getData().getId());
                        String receiverId = String.valueOf(userLoginResponse.getData().getId());
                        String receiverName = userLoginResponse.getData().getName();
                        String receiverPayId = userLoginResponse.getData().getPay_id();

                        Log.d(TAG, "onResponse: User Data => " + receiverId + " " + receiverName + " " + receiverPayId);
                        Intent intent = new Intent(PaySend.this, PaySend_2.class);

                        intent.putExtra(userSessionManager.KEY_ACCOUNT_TYPE, user_id_type);
                        intent.putExtra("obj", userLoginResponse);
//                        new ViewLoadingAnimation(PaySend.this, false);
                        startActivity(intent);


                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: Caught Error: ." + e);

                        viewLoadingAnimation.showLoading(false);
                    }
                } else {
                    editTextUserId.setError("Invalid user id.");
                    viewLoadingAnimation.showLoading(false);
                    ((EditText) findViewById(R.id.textView2111)).setError("User Not Found.");
                }



            }

            @Override
            public void onFailure(Call<GetReceiverInformationResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
//                asynCall.cancel();
                viewLoadingAnimation.showLoading(false);
                Toast.makeText(PaySend.this, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}