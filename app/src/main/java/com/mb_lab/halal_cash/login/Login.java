package com.mb_lab.halal_cash.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.UserLoginDataModel;
import com.mb_lab.halal_cash.DataModels.UserLoginResponse;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.Registration_Verification.Registration;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.Util.InputInformationValidation;
import com.mb_lab.halal_cash.resetPassword.AccountRecovery;
import com.mb_lab.halal_cash.resetPassword.changePasswordConstants;
import com.mb_lab.halal_cash.home.Home;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Login extends AppCompatActivity {
    private EditText userId, userPassword;
    String user_id = null;
    String user_id_type = null;
    private final String TAG = "LoginActivity";
    private ViewLoadingAnimation viewLoadingAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userId = findViewById(R.id.userId);
        userPassword = findViewById(R.id.userPassword);

        viewLoadingAnimation = new ViewLoadingAnimation(Login.this);
        try {


            user_id = getIntent().getStringExtra("user_id");
            user_id_type = getIntent().getStringExtra("user_id_type");

//            userId.setText(user_id);
            if (user_id != null) {
                Toast.makeText(Login.this, "Insert Password.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Login.this, "Insert User Id and Password.", Toast.LENGTH_LONG).show();
            }


        } catch (Exception e) {
            Toast.makeText(Login.this, "Insert User Id and Password.", Toast.LENGTH_LONG).show();
        }


        findViewById(R.id.postLoginData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserIdString = ((EditText) findViewById(R.id.userId)).getText().toString();
                String UserPasswordString = ((EditText) findViewById(R.id.userPassword)).getText().toString();

                // check for valid email or contact and password
                Log.d(TAG, "tryLogin: userEmail: " + UserIdString);
                Log.d(TAG, "tryLogin: userPass: " + UserPasswordString);

                //todo check for user_id_type
                String user_id_type = new InputInformationValidation().getUserIdType(UserIdString);
                if (user_id_type != null) {

                    postData(UserIdString, user_id_type, UserPasswordString);
                }
            }
        });


        findViewById(R.id.forgotPassword).setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, AccountRecovery.class);
            intent.putExtra(changePasswordConstants.REQUEST_FOR, changePasswordConstants.REQUEST_FOR_FORGET_PASSWORD);
            startActivity(intent);
        });

        findViewById(R.id.goToRegistration).setOnClickListener(v -> startActivity(new Intent(Login.this, Registration.class)));


    }

    private void postData(@NonNull String user_id, @NonNull String user_id_type, @NonNull String password) {

        viewLoadingAnimation.showLoading(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.Login retrofitAPI = retrofit.create(RetrofitAPI.Login.class);

        // passing data from our text fields to our modal class.
//        UserLoginDataModel modal = new UserLoginDataModel(email,phone,password);
        UserLoginDataModel modal = new UserLoginDataModel(user_id, user_id_type, password);

        // calling a method to create a post and passing our modal class.
        Call<UserLoginResponse> call = retrofitAPI.createPostBodyForUserLogin(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse( Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                Log.d(TAG, "onResponse: successful: ");
//                UserLoginResponse responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.

                Log.d(TAG, "onResponse: Status Code: " + response.code());

                if (response.isSuccessful() || response.code() == 201){

//                    String Message = response.body().getMessage();

                    String Token =  response.body().getToken();
                    String Id = String.valueOf( response.body().getUser().getId());
                    String Name =  response.body().getUser().getName();
                    String Email =  response.body().getUser().getEmail();
                    String Phone = String.valueOf( response.body().getUser().getPhone());
                    String PayId = String.valueOf( response.body().getUser().getPay_id());
                    Boolean Verified =  response.body().getUser().getVerified();
                    Boolean is_authenticated =  response.body().getUser().getIs_authenticated();
                    String AccountType =  response.body().getUser().getAccount_type();
                    String ImageUrl =  response.body().getUser().getImage();

                    Log.d(TAG, "onResponse: Token Updated. ");

                    UserSessionManager userSessionManager = new UserSessionManager(Login.this);

                    userSessionManager.updateUserAllInfo(Token, Id, Name, Email, Phone, PayId, Verified, is_authenticated, AccountType, ImageUrl);


                    // this method is called when we get response from our api.
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    //todo update login information

                    Log.d(TAG, "onResponse: Token: " + Token);
                    Log.d(TAG, "onResponse: User Id: " + Id);
                    viewLoadingAnimation.showLoading(false);
                    startActivity(new Intent(Login.this, Home.class));
                    finish();


                } else {

                    Log.d(TAG, "onResponse: Not successful: ");

                    viewLoadingAnimation.showLoading(false);
                    // this method is called when we get response from our api.
                    Toast.makeText(Login.this, "Credentials not matched.\nTry again.", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(@NonNull Call<UserLoginResponse> call, @NonNull Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                viewLoadingAnimation.showLoading(false);
//                responseTV.setText("Error found is : " + t.getMessage());
                // this method is called when we get response from our api.
                Toast.makeText(Login.this, "Check your connection and.\nTry again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}