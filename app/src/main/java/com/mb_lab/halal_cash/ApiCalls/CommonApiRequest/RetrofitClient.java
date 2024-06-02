package com.mb_lab.halal_cash.ApiCalls.CommonApiRequest;

import android.content.Context;

import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    protected Retrofit retrofit = null;
    protected Context context;
    protected String TOKEN;
    protected String TAG;

    protected RetrofitClient(Context context, String TAG) {
        this.TAG = TAG;
        this.context = context;
        TOKEN = new UserSessionManager(context).getToken();
        // on below line we are creating a retrofit
        // builder and passing our base url
        retrofit = getClient();
    }

    private Retrofit getClient() {
        if (retrofit == null) {
            // builder and passing our base url
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    // as we are sending data in json format so
                    // we have to add Gson converter factory
                    .addConverterFactory(GsonConverterFactory.create())
                    // at last we are building our retrofit builder.
                    .build();
            return retrofit;
        }
        return retrofit;

    }

}
