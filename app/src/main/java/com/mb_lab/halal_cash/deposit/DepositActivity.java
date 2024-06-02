package com.mb_lab.halal_cash.deposit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;

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
import com.mb_lab.halal_cash.DataModels.GetDepositAgentListResponse;
import com.mb_lab.halal_cash.DataModels.GetOwnCreatedAdsResponse;
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

public class DepositActivity extends AppCompatActivity {
    UserSessionManager userSessionManager;
    private RecyclerView recyclerView;
    private AdapterForDepositAgentList adapter;
    private static final String TAG = "DepositActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout emptyAdsList;
    private TabLayout tabLayout;
    ViewLoadingAnimation viewLoadingAnimation;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deposit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userSessionManager = new UserSessionManager(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewLoadingAnimation = new ViewLoadingAnimation(this);

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        // Setup swipe to refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getDepositAgentsList();

            }

        });
        getDepositAgentsList();
    }

    private void getDepositAgentsList() {
//        swipeRefreshLayout.setRefreshing(true);
        viewLoadingAnimation.showLoading(true);

        android.util.Log.d(TAG, "sendData: started..");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI getDepositAgentList = retrofit.create(RetrofitAPI.class);
        Call<GetDepositAgentListResponse> asynCall = getDepositAgentList.createGetDepositAgentList("Bearer " + userSessionManager.getToken());

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<GetDepositAgentListResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetDepositAgentListResponse> call, @NonNull Response<GetDepositAgentListResponse> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful() && !Objects.requireNonNull(response.body()).getData().isEmpty()) {
                    Log.d(TAG, "onResponse: response Successful..");

                    Log.d(TAG, "onResponse: response Count: " + response.body().getData().size());


                    Log.d(TAG, "onCreateView: DataSize:" + response.body().getData().size());

//                    swipeRefreshLayout.setVisibility(View.VISIBLE);
//                    emptyAdsList.setVisibility(View.GONE);


                    adapter = new AdapterForDepositAgentList(response.body().getData());

                    recyclerView.setAdapter(adapter);

//                    swipeRefreshLayout.setRefreshing(false);
                    viewLoadingAnimation.showLoading(false);

                } else {
                    // Hide the refreshing indicator
                    viewLoadingAnimation.showLoading(false);

                }



            }

            @Override
            public void onFailure(Call<GetDepositAgentListResponse> call, Throwable t) {
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