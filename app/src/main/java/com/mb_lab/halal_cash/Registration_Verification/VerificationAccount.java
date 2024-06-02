package com.mb_lab.halal_cash.Registration_Verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.resetPassword.resetPassword_1_request;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.resetPassword.resetPassword_2_verifyOtp;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.Enums.UserIdTypeEnums;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.DataModels.UserRegistrationDataModel;
import com.mb_lab.halal_cash.DataModels.UserRegistrationResponse;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.Util.AutoInsertOTP;
import com.mb_lab.halal_cash.login.Login;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.resetPassword.OTPVerification;
import com.mb_lab.halal_cash.resetPassword.changePasswordConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerificationAccount extends AppCompatActivity {
    private String OTP = null;
    private String user_id = null;
    private String user_id_type = null;
    private final String TAG = "VerificationAccount";
    ViewLoadingAnimation viewLoadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);


        viewLoadingAnimation = new ViewLoadingAnimation(VerificationAccount.this);
        try {

            OTP = getIntent().getStringExtra("OTP");
            user_id = getIntent().getStringExtra("user_id");
            user_id_type = getIntent().getStringExtra("user_id_type");

            ((TextView) findViewById(R.id.textView15)).setText(OTP);
            new AutoInsertOTP(OTP, findViewById(R.id.editText2));
        } catch (Exception e) {
            Toast.makeText(VerificationAccount.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
        }

        //update ui
        if (user_id_type.equals(UserIdTypeEnums.EMAIL.getValue())) {
            ((TextView) findViewById(R.id.textView10)).setText("Email Verification");
            ((TextView) findViewById(R.id.textView35)).setText("Enter the 5-digit verification code sent to ******@gmail.com");


        } else if (user_id_type.equals(UserIdTypeEnums.PHONE.getValue())) {
            ((TextView) findViewById(R.id.textView10)).setText("Phone Verification");
            ((TextView) findViewById(R.id.textView35)).setText("Enter the 5-digit verification code sent to 01XX-XXXX-XXX");

        } else {
            finish();
        }
        findViewById(R.id.textView11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(VerificationAccount.this, "OTP Sent.", Toast.LENGTH_LONG).show();

            }
        });
        findViewById(R.id.textViewContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOTP();
            }
        });

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher();

            }
        });

    }

    public void confirmOTP() {

        viewLoadingAnimation.showLoading(true);
        OTP = ((EditText) findViewById(R.id.editText2)).getText().toString();
//        user_id = ((EditText) findViewById(R.id.editTextTextEmailAddress)).getText().toString();
        Log.d(TAG, "verifyOtp: " + OTP.length() + " " + OTP + " " + user_id + " " + user_id_type);
        if (OTP.length() == 5) {
            // below line is for displaying our progress bar.
//        loadingPB.setVisibility(View.VISIBLE);

            // on below line we are creating a retrofit
            // builder and passing our base url
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    // as we are sending data in json format so
                    // we have to add Gson converter factory
                    .addConverterFactory(GsonConverterFactory.create())
                    // at last we are building our retrofit builder.
                    .build();
            // below line is to create an instance for our retrofit api class.
            RetrofitAPI.AccountVerification retrofitAPI = retrofit.create(RetrofitAPI.AccountVerification.class);

            // passing data from our text fields to our modal class.
//        UserLoginDataModel modal = new UserLoginDataModel(email,phone,password);
            UserRegistrationDataModel modal = new UserRegistrationDataModel(user_id, user_id_type, OTP);

            // calling a method to create a post and passing our modal class.
            Call<UserRegistrationResponse> call = retrofitAPI.createPostBodyForUserAccountVerification(modal);

            // on below line we are executing our method.
            call.enqueue(new Callback<UserRegistrationResponse>() {
                @Override
                public void onResponse(Call<UserRegistrationResponse> call, Response<UserRegistrationResponse> response) {

                    // below line is for hiding our progress bar.
//                loadingPB.setVisibility(View.GONE);

                    // on below line we are setting empty text
                    // to our both edit text.


                    // we are getting response from our body
                    // and passing it to our modal class.
                    UserRegistrationResponse responseFromAPI = response.body();

                    // on below line we are getting our data from modal class and adding it to our string.
//                assert responseFromAPI != null;
                    int responseCode = response.code();
                    if (responseFromAPI != null && (responseCode == 201 || responseCode == 200)) {

                        Boolean Status = responseFromAPI.getStatus();
                        String Message = responseFromAPI.getMessage();

//                        ((TextView) findViewById(R.id.textView3)).setText(Message);

                        if (Status) {

                            Log.d(TAG, "onResponse: successfully Verified: ");
                            //todo take action on Verification
                            // this method is called when we get response from our api.
                            Toast.makeText(VerificationAccount.this, Message, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(VerificationAccount.this, Login.class);
                            intent.putExtra("user_id", user_id);
                            intent.putExtra("user_id_type", user_id_type);
                            viewLoadingAnimation.showLoading(false);
                            startActivity(intent);
                            finish();

                        } else {
                            viewLoadingAnimation.showLoading(false);
                            Toast.makeText(VerificationAccount.this, Message, Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Log.d(TAG, "onResponse: Not successful: ");
                        viewLoadingAnimation.showLoading(false);
                        // this method is called when we get response from our api.
                        Toast.makeText(VerificationAccount.this, "Something is wrong! try again.", Toast.LENGTH_SHORT).show();
                    }

//                String responseString = "Response Code : " + response.code() + "\nMessage : " + responseFromAPI.getMessage() + "\n" + "Token : " + responseFromAPI.getToken()+ "\n" + "Name : " + responseFromAPI.getUser().getName();

                    // below line we are setting our
                    // string to our text view.
//                ((TextView)findViewById(R.id.textView)).setText(responseString);


                }

                @Override
                public void onFailure(Call<UserRegistrationResponse> call, Throwable t) {
                    // setting text to our text view when
                    // we get error response from API.
                    Log.e(TAG, "onFailure: " + t.getMessage());
//                responseTV.setText("Error found is : " + t.getMessage());
                    viewLoadingAnimation.showLoading(false);
                }
            });

        } else {
            viewLoadingAnimation.showLoading(false);
            Toast.makeText(VerificationAccount.this, "OTP Must be 5 digits in length.", Toast.LENGTH_LONG).show();
            ((EditText) findViewById(R.id.editText2)).setText(null);
        }
    }
}