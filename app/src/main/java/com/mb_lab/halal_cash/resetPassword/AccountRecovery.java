package com.mb_lab.halal_cash.resetPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.GetReceiverInformationResponse;
import com.mb_lab.halal_cash.Enums.UserIdTypeEnums;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.Util.InputInformationValidation;
import com.mb_lab.halal_cash.Util.ResetUserInfo;
import com.mb_lab.halal_cash.pay.PaySend.PaySend;
import com.mb_lab.halal_cash.pay.PaySend.PaySend_2;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountRecovery extends AppCompatActivity {

    private String user_id_type = null;
    String user_id = null;
    private final String TAG = "AccountRecovery";
    UserSessionManager userSessionManager;
    ViewLoadingAnimation viewLoadingAnimation;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_recovery);

        userSessionManager = new UserSessionManager(this);
        viewLoadingAnimation = new ViewLoadingAnimation(AccountRecovery.this);
        intent = new Intent(AccountRecovery.this, OTPVerification.class);
        findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ResetUserInfo(AccountRecovery.this);
                //todo check user_id and match password
                user_id = Objects.requireNonNull(((TextInputEditText) findViewById(R.id.userId)).getText()).toString();
                user_id_type = new InputInformationValidation().getUserIdType(user_id);
                Log.d(TAG, "user_id _type: " + user_id_type);
                if (user_id_type != null) {
                    if (user_id_type.equals(UserIdTypeEnums.EMAIL.getValue())) {
                        userSessionManager.updateUseEmail(user_id);
                        intent.putExtra(changePasswordConstants.REQUEST_FOR, changePasswordConstants.EMAIL_VERIFICATION);
                        startActivity(intent);
//                        getAccountValidation(user_id_type, user_id);
                    } else if ((user_id_type.equals(UserIdTypeEnums.PHONE.getValue()))) {
                        userSessionManager.updateUsePhone(user_id);
                        intent.putExtra(changePasswordConstants.REQUEST_FOR, changePasswordConstants.PHONE_VERIFICATION);
//                        getAccountValidation(user_id_type, user_id);
                        startActivity(intent);
                    }

                } else {
                    ((TextInputLayout) findViewById(R.id.textFieldEmail)).setError("Only email or phone allowed as id.");
                    ((TextInputLayout) findViewById(R.id.textFieldEmail)).setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);

                }
            }
        });
    }


    private void getAccountValidation(String user_id_type, String value) {

        viewLoadingAnimation.showLoading(true);

        Log.d(TAG, "getAccountValidation called successful....... ");
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
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: Caught Error: ." + e);


                    }
                    viewLoadingAnimation.showLoading(false);
                    startActivity(intent);
                } else {
                    viewLoadingAnimation.showLoading(false);
                    ((TextInputLayout) findViewById(R.id.textFieldEmail)).setError("User Not Found.");

                }


            }

            @Override
            public void onFailure(Call<GetReceiverInformationResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
//                asynCall.cancel();
                viewLoadingAnimation.showLoading(false);
                Toast.makeText(AccountRecovery.this, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}