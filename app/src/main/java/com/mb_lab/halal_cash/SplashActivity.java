package com.mb_lab.halal_cash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mb_lab.halal_cash.DataModels.UserLoginResponse;
import com.mb_lab.halal_cash.Registration_Verification.Registration;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.home.Home;
import com.mb_lab.halal_cash.login.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {
    private final String TAG = "SplashActivity";
    Handler handler;
    UserSessionManager userSessionManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userSessionManager = new UserSessionManager(SplashActivity.this);


        setContentView(R.layout.splash_activity);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    String token = userSessionManager.getToken();
                    Log.d(TAG, "run: Token: " + token);
                    if (token != null) {
                        postData(token);
                    } else {
                        setContentView(R.layout.sign_up_onboarding_view);
                    }

                } catch (Exception e) {
                    Log.e(TAG, "run: ", e);
                    setContentView(R.layout.sign_up_onboarding_view);
                }

            }
        }, 500);


    }
    private void postData( String token) {

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
        RetrofitAPI.RefreshToken retrofitAPI = retrofit.create(RetrofitAPI.RefreshToken.class);

        // passing data from our text fields to our modal class.
//        UserLoginDataModel modal = new UserLoginDataModel(email,phone,password);
//        UserLoginDataModel modal = new UserLoginDataModel(user_id, user_id_type, password);

        // calling a method to create a post and passing our modal class.
        Call<UserLoginResponse> call = retrofitAPI.createPostBodyForRefreshToken("Bearer " +token,token);

        // on below line we are executing our method.
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                Log.d(TAG, "onResponse: successful: ");

                UserLoginResponse responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
//                assert responseFromAPI != null;
                int responseCode = response.code();

                if (responseFromAPI != null) {
                    if (responseCode == 201) {
                        Log.d(TAG, "onResponse: Status Code: " + responseCode);

                        String Message = responseFromAPI.getMessage();
                        String Token = responseFromAPI.getToken();
                        String Id = String.valueOf(responseFromAPI.getUser().getId());
                        String Name = responseFromAPI.getUser().getName();
                        String Email = responseFromAPI.getUser().getEmail();
                        String Phone = String.valueOf(responseFromAPI.getUser().getPhone());
                        String PayId = String.valueOf(responseFromAPI.getUser().getPay_id());
                        Boolean Verified =  responseFromAPI.getUser().getVerified();
                        Boolean is_authenticated =  responseFromAPI.getUser().getIs_authenticated();
                        String AccountType = responseFromAPI.getUser().getAccount_type();
                        String ImageUrl = responseFromAPI.getUser().getImage();

                        Log.d(TAG, "onResponse: Token Updated. ");

                        UserSessionManager userSessionManager = new UserSessionManager(SplashActivity.this);

                        userSessionManager.updateUserAllInfo(Token, Id, Name, Email, Phone, PayId, Verified,is_authenticated,AccountType,ImageUrl);


                        // this method is called when we get response from our api.
                        Toast.makeText(SplashActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        //todo update login information
                        Intent intent = new Intent(SplashActivity.this, Home.class);
                        Log.d(TAG, "onResponse: Token: " + Token);
                        Log.d(TAG, "onResponse: User Id: " + Id);

                        startActivity(new Intent(SplashActivity.this, Home.class));
                        finishAffinity();

                    } else if (responseCode == 200 || responseCode == 422) {
                        Log.d(TAG, "onResponse: Status Code: " + responseCode);
//                        String Message = responseFromAPI.getMessage();
//                        Toast.makeText(SplashActivity.this, Message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SplashActivity.this, Login.class));
                        finishAffinity();

                    }
                } else {
                    Log.d(TAG, "onResponse: Not successful: ");
                    // this method is called when we get response from our api.
                    startActivity(new Intent(SplashActivity.this, Login.class));
                    finishAffinity();
                }



            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                startActivity(new Intent(SplashActivity.this, Login.class));
                finishAffinity();
//                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
    public void goToLogin(View view) {
        startActivity(new Intent(SplashActivity.this, Login.class));
        finishAffinity();
    }

    public void goTORegistration(View view) {
        startActivity(new Intent(SplashActivity.this, Registration.class));
        finishAffinity();
    }
}