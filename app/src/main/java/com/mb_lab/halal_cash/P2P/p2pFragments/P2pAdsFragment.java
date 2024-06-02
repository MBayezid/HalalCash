package com.mb_lab.halal_cash.P2P.p2pFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.GetOwnCreatedAdsResponse;
import com.mb_lab.halal_cash.P2P.AdsHelperClasses.AdapterForMyAdsList;
import com.mb_lab.halal_cash.P2P.CreateAds.PostNormalAds;
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

public class P2pAdsFragment extends Fragment {

    UserSessionManager userSessionManager;
    private RecyclerView recyclerView;
    private AdapterForMyAdsList adapter;
    private static final String TAG = "P2pAdsFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout emptyAdsList;
    private TabLayout tabLayout;
    ViewLoadingAnimation viewLoadingAnimation;

    private Spinner spinner;

    public P2pAdsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        userSessionManager = new UserSessionManager(requireContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = view.findViewById(R.id.spinner);
// Sample data for the spinner
        String[] items = {"Status", "Online", "Pending", "Denied"};
        // Create an ArrayAdapter using the sample data and a custom layout for the dropdown items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item, items);

        // Set the custom adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        // Set item selected listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Show a toast message with the selected item
                Toast.makeText(getActivity(), "Showing : " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing when nothing is selected
            }
        });

        emptyAdsList = view.findViewById(R.id.emptyAdsList);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        view.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        view.findViewById(R.id.createAds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), PostNormalAds.class));
            }
        });
        view.findViewById(R.id.createAds2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), PostNormalAds.class));
            }
        });

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        // Setup swipe to refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getMyAdsList("");

            }

        });
        getMyAdsList("");

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLoadingAnimation = new ViewLoadingAnimation(getActivity());
        return inflater.inflate(R.layout.p2p_ads_fragment, container, false);
    }

    private void getMyAdsList(String path) {
//        swipeRefreshLayout.setRefreshing(true);
        viewLoadingAnimation.showLoading(true);
        swipeRefreshLayout.setVisibility(View.GONE);
        emptyAdsList.setVisibility(View.GONE);
        android.util.Log.d(TAG, "sendData: started..");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.GetOwnCreatedAds getUserTransactionHistory = retrofit.create(RetrofitAPI.GetOwnCreatedAds.class);
        Call<GetOwnCreatedAdsResponse> asynCall = getUserTransactionHistory.createGetAllOwnCreatedAdsList("Bearer " + userSessionManager.getToken());

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<GetOwnCreatedAdsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetOwnCreatedAdsResponse> call, @NonNull Response<GetOwnCreatedAdsResponse> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful() && !Objects.requireNonNull(response.body()).getData().isEmpty()) {
                    Log.d(TAG, "onResponse: response Successful..");

                    Log.d(TAG, "onResponse: response Count: " + response.body().getData().size());


                    Log.d(TAG, "onCreateView: DataSize:" + response.body().getData().size());
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    emptyAdsList.setVisibility(View.GONE);


                    adapter = new AdapterForMyAdsList(response.body().getData());

                    recyclerView.setAdapter(adapter);

//                    swipeRefreshLayout.setRefreshing(false);
                    viewLoadingAnimation.showLoading(false);

                } else {
                    // Hide the refreshing indicator
                    viewLoadingAnimation.showLoading(false);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    emptyAdsList.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onFailure(Call<GetOwnCreatedAdsResponse> call, Throwable t) {
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
