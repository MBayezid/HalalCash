package com.mb_lab.halal_cash.ApiCalls.CommonApiRequest;

import android.content.Context;
import android.util.Log;

import com.mb_lab.halal_cash.DataModels.UserLoginResponse;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserInformation extends RetrofitClient {
    public GetUserInformation(Context context, String TAG) {

        super(context, TAG);
        try {
            postData();
        } catch (Exception e) {
            Log.e(TAG, "GetUserInformation: ", e);

        }

    }

    private void postData() {

        RetrofitAPI.RefreshToken retrofitAPI = retrofit.create(RetrofitAPI.RefreshToken.class);

        // passing data from our text fields to our modal class.
//        UserLoginDataModel modal = new UserLoginDataModel(email,phone,password);
//        UserLoginDataModel modal = new UserLoginDataModel(user_id, user_id_type, password);

        // calling a method to create a post and passing our modal class.
        Call<UserLoginResponse> call = retrofitAPI.createPostBodyForRefreshToken("Bearer " + TOKEN, TOKEN);

        // on below line we are executing our method.
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                Log.d(TAG, "onResponse: successful: ");

                UserLoginResponse responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
//                assert responseFromAPI != null;


                Log.d(TAG, "onResponse: Status Code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {

                    String Message = responseFromAPI.getMessage();
                    String Token = responseFromAPI.getToken();
                    String Id = String.valueOf(responseFromAPI.getUser().getId());
                    String Name = responseFromAPI.getUser().getName();
                    String Email = responseFromAPI.getUser().getEmail();
                    String Phone = String.valueOf(responseFromAPI.getUser().getPhone());
                    String PayId = String.valueOf(responseFromAPI.getUser().getPay_id());
                    Boolean Verified = responseFromAPI.getUser().getVerified();
                    Boolean is_authenticated = responseFromAPI.getUser().getIs_authenticated();
                    String AccountType = responseFromAPI.getUser().getAccount_type();
                    String ImageUrl = responseFromAPI.getUser().getImage();

                    Log.d(TAG, "onResponse: Token Updated. ");

                    UserSessionManager userSessionManager = new UserSessionManager(context);

                    userSessionManager.updateUserAllInfo(Token, Id, Name, Email, Phone, PayId, Verified, is_authenticated, AccountType, ImageUrl);
                    Log.d(TAG, "onResponse: User All Data Updated. ");


                    // this method is called when we get response from our api.
//                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();


                } else {
                    Log.d(TAG, "onResponse: Status Code: " + response.code());

                }



            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
//                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}
