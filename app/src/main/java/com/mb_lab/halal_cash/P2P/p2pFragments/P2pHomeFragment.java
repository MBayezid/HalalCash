package com.mb_lab.halal_cash.P2P.p2pFragments;

import android.content.Context;
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
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.UserAdsResponse;
import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
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

public class P2pHomeFragment extends Fragment {
    UserSessionManager userSessionManager;
    private RecyclerView recyclerView;
    private AdapterFor_getAllAds adapter;
    private static final String TAG = "P2pHomeFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private TabLayout tabLayout;
    private String AdType = "Buy";
    private ViewLoadingAnimation viewLoadingAnimation;
    private static Context context;

    public P2pHomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSessionManager = new UserSessionManager(requireContext());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        updateUi(userSessionManager.getUserType().toLowerCase().trim(), view);


        view.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        tabLayout = view.findViewById(R.id.tabLayout);
        // Set custom tab indicator
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Log.d(TAG, "onTabSelected: 1 Buy");
                        AdType = "Buy";
                        getAllAdsList(AdType);
                        break;
                    case 1:
                        Log.d(TAG, "onTabSelected: 2 Sell");
                        AdType = "Sell";
                        getAllAdsList(AdType);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        // Setup swipe to refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getAllAdsList(AdType);

            }

        });
        getAllAdsList(AdType);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.p2p_home_fragment, container, false);

        viewLoadingAnimation = new ViewLoadingAnimation(context);

        return view;
    }

    private void updateUi(String AccountType, View view) {
        Log.d(TAG, "updateUi: UserType : " + AccountType);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //        view.findViewById(R.id.p2p_bottomNavigationView).setVisibility(View.GONE);
                if (AccountType.equals("agent")) {
                    view.findViewById(R.id.bellNotification).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.orderHistory).setVisibility(View.VISIBLE);


                } else {
                    view.findViewById(R.id.bellNotification).setVisibility(View.GONE);
                    view.findViewById(R.id.orderHistory).setVisibility(View.GONE);
                }
            }
        }).start();
    }

    private void getAllAdsList(String path) {
        viewLoadingAnimation.showLoading(true);
//        swipeRefreshLayout.setRefreshing(true);
//        new updateUiAnimation().setLoadingStart(PaySend_Successful.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
        android.util.Log.d(TAG, "sendData: started..");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.GetAllAds getUserTransactionHistory = retrofit.create(RetrofitAPI.GetAllAds.class);
        Call<UserAdsResponse> asynCall = getUserTransactionHistory.createGetAllAdsList("Bearer " + userSessionManager.getToken());

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<UserAdsResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserAdsResponse> call, @NonNull Response<UserAdsResponse> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful() && !Objects.requireNonNull(response.body()).getData().isEmpty()) {
                    Log.d(TAG, "onResponse: response Successful..");

                    Log.d(TAG, "onResponse: response Count: " + response.body().getData().size());

                    viewLoadingAnimation.showLoading(false);
                    Log.d(TAG, "onCreateView: DataSize:" + response.body().getData().size());
                    List<UserAdsResponse.Data> data = new ArrayList<UserAdsResponse.Data>();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (new ReturnEnumInString().getEnumToString(Integer.parseInt(response.body().getData().get(i).getAd_type())) != AdType) {
                            data.add(response.body().getData().get(i));
                        }

                    }


                    adapter = new AdapterFor_getAllAds(context, data);

                    recyclerView.setAdapter(adapter);
                    viewLoadingAnimation.showLoading(false);
//                    swipeRefreshLayout.setRefreshing(false);

                } else {
                    ;
                    // Hide the refreshing indicator
//                    swipeRefreshLayout.setRefreshing(false);
                    viewLoadingAnimation.showLoading(false);


                }


            }

            @Override
            public void onFailure(Call<UserAdsResponse> call, Throwable t) {
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
//                swipeRefreshLayout.setRefreshing(false);
                viewLoadingAnimation.showLoading(false);
//                finish();

//                ((EditText) findViewById(R.id.editText)).setError("Network Error.");

            }

        });

    }
}
