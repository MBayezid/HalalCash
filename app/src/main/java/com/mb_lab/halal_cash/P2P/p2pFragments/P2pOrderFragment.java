package com.mb_lab.halal_cash.P2P.p2pFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.DataModels.GetOwnCreatedAdsResponse;
import com.mb_lab.halal_cash.DataModels.UserAdsResponse;
import com.mb_lab.halal_cash.DataModels.UserOrderHistoryResponse;
import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
import com.mb_lab.halal_cash.P2P.AdsHelperClasses.AdapterForOrderHistory;
import com.mb_lab.halal_cash.P2P.AdsHelperClasses.AdapterFor_getAllAds;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class P2pOrderFragment extends Fragment {

    UserSessionManager userSessionManager;
    private RecyclerView recyclerView;
    private AdapterForOrderHistory adapter;
    private static final String TAG = "P2pOrderFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private TabLayout tabLayout;

    public P2pOrderFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        userSessionManager = new UserSessionManager(getActivity());

        view.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        // Setup swipe to refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getTransactionHistory();

            }

        });
        getTransactionHistory();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.p2p_order_fragment, container, false);
    }

    private void getTransactionHistory() {
        swipeRefreshLayout.setRefreshing(true);
//        new updateUiAnimation().setLoadingStart(PaySend_Successful.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
        android.util.Log.d(TAG, "sendData: started..");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.GetOrderHistory getUserOrderHistory = retrofit.create(RetrofitAPI.GetOrderHistory.class);

        Call<UserOrderHistoryResponse> asynCall = getUserOrderHistory.createGetAllAdsOrderHistory("Bearer " + userSessionManager.getToken());

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<UserOrderHistoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserOrderHistoryResponse> call, @NonNull Response<UserOrderHistoryResponse> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful() && !Objects.requireNonNull(response.body()).getData().isEmpty()) {
                    Log.d(TAG, "onResponse: response Successful..");

                    List<UserOrderHistoryResponse.Data> data = new ArrayList<UserOrderHistoryResponse.Data>();
                    data.clear();

                    data = response.body().getData();

                    Log.d(TAG, "onResponse: response Count: " + data.size());


                    Log.d(TAG, "onCreateView: DataSize:" + data.size());


                    adapter = new AdapterForOrderHistory(data);
//
                    recyclerView.setAdapter(adapter);
//
                    swipeRefreshLayout.setRefreshing(false);

                } else {

                    // Hide the refreshing indicator
                    swipeRefreshLayout.setRefreshing(false);
//                    swipeRefreshLayout.setVisibility(View.GONE);
                    //todo update ui for empty data
                }



            }

            @Override
            public void onFailure(Call<UserOrderHistoryResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                android.util.Log.e(TAG, "onFailure: " + t.getMessage());

                // set message
//                String message = "Connection Error";
//                // initialize snack bar
////                Snackbar snackbar = Snackbar.make(findViewById(R.id.recyclerView), message, Snackbar.LENGTH_LONG).setTextColor(getColor(R.color.red_100));
//                Snackbar snackbar = Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG);
//                // initialize view
//                View view = snackbar.getView();
//                // show snack bar
//                snackbar.show();
                // Hide the refreshing indicator
                swipeRefreshLayout.setRefreshing(false);
//                finish();

//                ((EditText) findViewById(R.id.editText)).setError("Network Error.");

            }

        });

    }
}
