package com.mb_lab.halal_cash.Registration_Verification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.UserRegistrationDataModel;
import com.mb_lab.halal_cash.DataModels.UserRegistrationResponse;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.Util.InputInformationValidation;
import com.mb_lab.halal_cash.login.Login;
import com.mb_lab.halal_cash.policy.PrivacyPolicy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registration extends AppCompatActivity {
    private String OTP = null;
    private String user_id_type = null;
    String user_id = null;
    private final String TAG = "RegistrationActivity";
    ViewLoadingAnimation viewLoadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewLoadingAnimation = new ViewLoadingAnimation(Registration.this);

        findViewById(R.id.gotoPrivicypolicy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, PrivacyPolicy.class));


            }
        });        findViewById(R.id.postRegistrationData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Login.class));
                finishAffinity();

            }
        });
        findViewById(R.id.goToLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Login.class));
            }
        });

        findViewById(R.id.postRegistrationData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo check user_id and match password
                user_id = ((EditText) findViewById(R.id.userId)).getText().toString().trim();
                user_id_type = new InputInformationValidation().getUserIdType(user_id);
                Log.d(TAG, "user_id _type: " + user_id_type);
                if (user_id_type != null) {
                    postRegistrationData();
                } else {
                    ((TextInputLayout) findViewById(R.id.textFieldEmail)).setError("Only email or phone allowed as id.");
                    ((TextInputLayout) findViewById(R.id.textFieldEmail)).setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);

                }
            }
        });
    }


    public void postRegistrationData() {
        try {
            String password = ((TextInputEditText) findViewById(R.id.userPassword)).getText().toString().trim();
            String confirm_password = ((TextInputEditText) findViewById(R.id.userConfirmPassword)).getText().toString().trim();

            if (new InputInformationValidation().passwordPatternValidation(password) && new InputInformationValidation().userIDAndPasswordSimilarity(user_id, password)) {

                if (password.equals(confirm_password) && user_id != null && user_id_type != null) {
                    Log.d(TAG, "user_id: " + user_id);
                    Log.d(TAG, "user_id_type: " + user_id_type);
                    Log.d(TAG, "password: " + password);
                    Log.d(TAG, "confirm_password: " + confirm_password);

                    registration(user_id, user_id_type, password, confirm_password);
                } else {
                    ((TextInputLayout) findViewById(R.id.textFieldPassword2)).setError("Confirm password mismatch!");
                    ((TextInputLayout) findViewById(R.id.textFieldPassword2)).setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                }


            } else {
                ((TextInputLayout) findViewById(R.id.textFieldPassword1)).setError("1. Password length (min 8 to max 30 chars).\n2. At least one uppercase letter & number.\n[Don't use your email or phone number as password.]");
                ((TextInputLayout) findViewById(R.id.textFieldPassword1)).setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            }

        } catch (Exception e) {
            Log.e(TAG, "postRegistrationData: pattern Matching Error ", e);
        }

    }

    private void registration(@NonNull String user_id, @NonNull String user_id_type, @NonNull String password, @NonNull String confirm_password) {

        // below line is for displaying our progress bar.
        viewLoadingAnimation.showLoading(true);

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
        RetrofitAPI.Registration retrofitAPI = retrofit.create(RetrofitAPI.Registration.class);

        // passing data from our text fields to our modal class.
//        UserLoginDataModel modal = new UserLoginDataModel(email,phone,password);
        UserRegistrationDataModel modal = new UserRegistrationDataModel(user_id, user_id_type, password, confirm_password);

        // calling a method to create a post and passing our modal class.
        Call<UserRegistrationResponse> call = retrofitAPI.createPostBodyForUserRegistration(modal);

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

                Log.d(TAG, "responseCode: " + response.code());

                if (response.body() != null && response.isSuccessful()) {

                    Boolean Status = responseFromAPI.getStatus();
                    String Message = responseFromAPI.getMessage();


                    Log.d(TAG, "onResponse: successful: ");
                    // this method is called when we get response from our api.
                    Toast.makeText(Registration.this, "Insert OTP", Toast.LENGTH_SHORT).show();

                    //todo take action on success
                    Intent intent = new Intent(Registration.this, VerificationAccount.class);
                    intent.putExtra("OTP", Message);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("user_id_type", user_id_type);

                    viewLoadingAnimation.showLoading(false);
                    startActivity(intent);
                    finish();

                } else {
                    Log.d(TAG, "onResponse: Not successful: ");
                    viewLoadingAnimation.showLoading(false);
                    // this method is called when we get response from our api.
                    Toast.makeText(Registration.this, "Something is wrong! try again.", Toast.LENGTH_SHORT).show();
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
    }

}