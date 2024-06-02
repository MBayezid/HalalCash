package com.mb_lab.halal_cash.pay.payFragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.DataModels.UserTransactionHistoryResponse;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.pay.payFragments.PayHistoryFragmentSubClass.CustomAdapterForPayHistory;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayHistoryFragment extends Fragment {

    public static final String TAG = "PayHistoryFragment";
    private RecyclerView recyclerView;
    private CustomAdapterForPayHistory adapter;
    //    private com.mb_lab.api_request_demo.RecyclerViewExample1.CustomAdapter adapter;
    private List<String> dataList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TabLayout tabLayout;
    private TextView dateRang;
    private Context context;
    private String tabSelected = "";
    private String startDateStr = null;
    private String endDateStr = null;
    ImageView btnSelectDateRange;
    Calendar startCalendar, endCalendar;

    public PayHistoryFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Override this method to handle back press event
    public boolean onBackPressed() {
        // Implement your logic here
        // Return true if the back press event is consumed by the fragment, false otherwise
        return false;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateRang = view.findViewById(R.id.textView22);
        dateRang.setVisibility(View.GONE);

        btnSelectDateRange = view.findViewById(R.id.btnSelectDateRange);
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        btnSelectDateRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize start date picker dialog with the current date
                DatePickerDialog startDatePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                startCalendar.set(Calendar.YEAR, year);
                                startCalendar.set(Calendar.MONTH, monthOfYear);
                                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Initialize end date picker dialog with the start date
                                DatePickerDialog endDatePickerDialog = new DatePickerDialog(context,
                                        new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                endCalendar.set(Calendar.YEAR, year);
                                                endCalendar.set(Calendar.MONTH, monthOfYear);
                                                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                                // Handle the selected date range
                                                handleDateRange(startCalendar, endCalendar);
                                            }
                                        }, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

                                endDatePickerDialog.setTitle("Ending Date ");
                                // Show the end date picker dialog
                                endDatePickerDialog.show();
                            }
                        }, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

                // Show the start date picker dialog
                startDatePickerDialog.setTitle("Starting Date ");

                startDatePickerDialog.show();
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Log.d(TAG, "onTabSelected: 1 All");
                        tabSelected = "";
                        getTransactionHistory(tabSelected, startDateStr, endDateStr);
                        break;
                    case 1:
                        Log.d(TAG, "onTabSelected: 2 Paid");
                        tabSelected = "sends";
                        getTransactionHistory(tabSelected, startDateStr, endDateStr);
                        break;
                    case 2:
                        Log.d(TAG, "onTabSelected: 3 Receive");
                        tabSelected = "receives";
                        getTransactionHistory(tabSelected, startDateStr, endDateStr);


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

                startDateStr = null;
                endDateStr = null;
                dateRang.setVisibility(View.GONE);
                tabSelected="";
                getTransactionHistory(tabSelected, startDateStr, endDateStr);
                tabLayout.selectTab(tabLayout.getTabAt(0));


            }

        });

        getTransactionHistory(tabSelected, startDateStr, endDateStr);

        view.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.pay_history_fragment, container, false);
    }

    private void handleDateRange(Calendar startDate, Calendar endDate) {
        // Here, you can handle the selected start and end dates
        startDateStr = startDate.get(Calendar.YEAR) + "-" + (startDate.get(Calendar.MONTH) + 1) + "-" + startDate.get(Calendar.DAY_OF_MONTH);
        endDateStr = endDate.get(Calendar.YEAR) + "-" + (endDate.get(Calendar.MONTH) + 1) + "-" + endDate.get(Calendar.DAY_OF_MONTH);

        dateRang.setText("Filter From " + startDateStr + " to " + endDateStr);
        dateRang.setVisibility(View.VISIBLE);
        Toast.makeText(context, "Selected Date Range: " + startDateStr + " to " + endDateStr, Toast.LENGTH_SHORT).show();

        getTransactionHistory(tabSelected, startDateStr, endDateStr);
    }

    private void getTransactionHistory(String path,@Nullable String startingDate,@Nullable String endingDate) {
        recyclerView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
//        new updateUiAnimation().setLoadingStart(PaySend_Successful.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
        android.util.Log.d(TAG, "sendData: started..");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.GetUserTransactionHistory getUserTransactionHistory = retrofit.create(RetrofitAPI.GetUserTransactionHistory.class);
        Call<UserTransactionHistoryResponse> asynCall = getUserTransactionHistory.createGetTransactionHistory("Bearer " + new UserSessionManager(context).getToken(), path, startingDate, endingDate);

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<UserTransactionHistoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserTransactionHistoryResponse> call, @NonNull Response<UserTransactionHistoryResponse> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful() && response.code() == 200 && response.body() != null && !response.body().getData().isEmpty()) {
                    Log.d(TAG, "onResponse: response Successful..");

                    Log.d(TAG, "onResponse: response Count: " + response.body().getData().size());
                    Log.d(TAG, "onResponse: getAsset_type: " + response.body().getData().get(0).getAsset_type());
                    Log.d(TAG, "onResponse: getTransaction_type: " + response.body().getData().get(0).getTransaction_type());
                    Log.d(TAG, "onResponse: Asset_type: " + response.body().getData().get(0).getTransaction_type());
                    Log.d(TAG, "onResponse: getAmount: " + response.body().getData().get(0).getAmount());
                    Log.d(TAG, "onResponse: getStatus: " + response.body().getData().get(0).getStatus());
                    Log.d(TAG, "onResponse: getNote: " + response.body().getData().get(0).getNote());
                    Log.d(TAG, "onResponse: getDate: " + response.body().getData().get(0).getDate());

//                    response.body().getData();

                    Log.d(TAG, "onCreateView: DataSize:" + response.body().getData().size());

//                    dataSet.clear();
//                    dataSet.addAll(response.body().getData());

                    // Initialize and set the adapter
                    adapter = new CustomAdapterForPayHistory(response.body().getData(), context);
//                    adapter = new CustomAdapter(RecyclerViewActivity.this, dataSet);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    // Hide the refreshing indicator
                    swipeRefreshLayout.setRefreshing(false);

                } else {

                    recyclerView.setVisibility(View.GONE);
                    String message = "NO Transaction Found";
//                    // initialize snack bar
//                    Snackbar snackbar = Snackbar.make(findViewById(R.id.recyclerView), message, Snackbar.LENGTH_LONG).setTextColor(getColor(R.color.red_700));
//                    // initialize view
//                    View view = snackbar.getView();
//                    // show snack bar
//                    snackbar.show();
                    // Hide the refreshing indicator
                    swipeRefreshLayout.setRefreshing(false);
                }



            }

            @Override
            public void onFailure(Call<UserTransactionHistoryResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                android.util.Log.e(TAG, "onFailure: " + t.getMessage());

                // set message
//                String message = "Not Connected to Internet";
//                // initialize snack bar
//                Snackbar snackbar = Snackbar.make(findViewById(R.id.recyclerView), message, Snackbar.LENGTH_LONG).setTextColor(getColor(R.color.red_100));
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
