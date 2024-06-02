package com.mb_lab.halal_cash.home.homeNotification;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.GetOwnCreatedAdsResponse;
import com.mb_lab.halal_cash.DataModels.HomeNotificationResponse;
import com.mb_lab.halal_cash.P2P.AdsHelperClasses.AdapterForMyAdsList;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeNotificationActivity extends AppCompatActivity {
    UserSessionManager userSessionManager;
    private RecyclerView recyclerView;
    private AdapterForHomeNotification adapter;
    private static final String TAG = "HomeNotificationActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout emptyAdsList;
    private TabLayout tabLayout;
    ViewLoadingAnimation viewLoadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewLoadingAnimation = new ViewLoadingAnimation(HomeNotificationActivity.this);
        userSessionManager = new UserSessionManager(this);

        emptyAdsList = findViewById(R.id.emptyAdsList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeNotificationActivity.this));


        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        // Setup swipe to refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getHomeNotification();

            }

        });
        getHomeNotification();
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getHomeNotification() {
//        swipeRefreshLayout.setRefreshing(true);
        viewLoadingAnimation.showLoading(true);
        emptyAdsList.setVisibility(View.GONE);
        android.util.Log.d(TAG, "sendData: started..");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI getHomeNotification = retrofit.create(RetrofitAPI.class);
        Call<HomeNotificationResponse> asynCall = getHomeNotification.createGetAllHomeNotification("Bearer " + userSessionManager.getToken());

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<HomeNotificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<HomeNotificationResponse> call, @NonNull Response<HomeNotificationResponse> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful() && !Objects.requireNonNull(response.body()).getData().isEmpty()) {
                    Log.d(TAG, "onResponse: response Successful..");

                    Log.d(TAG, "onResponse: response Count: " + response.body().getData().size());


                    Log.d(TAG, "onCreateView: DataSize:" + response.body().getData().size());
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    emptyAdsList.setVisibility(View.GONE);
//
//
                    adapter = new AdapterForHomeNotification(response.body().getData());
//
                    recyclerView.setAdapter(adapter);


                    viewLoadingAnimation.showLoading(false);

                } else {
                    // Hide the refreshing indicator
                    viewLoadingAnimation.showLoading(false);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    emptyAdsList.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<HomeNotificationResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                android.util.Log.e(TAG, "onFailure: " + t.getMessage());

                // Hide the refreshing indicator
                viewLoadingAnimation.showLoading(false);
//                swipeRefreshLayout.setRefreshing(false);
//                finish();

//                ((EditText) findViewById(R.id.editText)).setError("Network Error.");

            }

        });

    }
}