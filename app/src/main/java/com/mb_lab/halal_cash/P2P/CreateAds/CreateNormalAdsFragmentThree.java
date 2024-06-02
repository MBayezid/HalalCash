package com.mb_lab.halal_cash.P2P.CreateAds;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.SimpleResponse;
import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
import com.mb_lab.halal_cash.P2P.P2PActivity;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateNormalAdsFragmentThree extends Fragment {
    private SharedViewModel viewModel;
    String ad_type, asset_type, user_price, price_type, payable_with, advertise_total_amount, order_limit_min, order_limit_max;
    boolean error = true;
    private final String TAG = "CreateNormalAdsFragmentThree";
    ViewLoadingAnimation viewLoadingAnimation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_normal_add_fragment_3, container, false);
        viewLoadingAnimation = new ViewLoadingAnimation(getActivity());
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //         Observe data changes in ViewModel
        viewModel.getSharedData().observe(getViewLifecycleOwner(), new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> hashMap) {
                // Handle data change
                ad_type = hashMap.get("ad_type");
                Log.d(TAG, "onChanged:ad_type " + ad_type);

                asset_type = hashMap.get("asset_type");
                Log.d(TAG, "onChanged:asset_type " + asset_type);

                user_price = hashMap.get("user_price");
                Log.d(TAG, "onChanged:user_price " + user_price);

                price_type = hashMap.get("price_type");
                Log.d(TAG, "onChanged:price_type " + price_type);

                payable_with = hashMap.get("payable_with");
                Log.d(TAG, "onChanged:payable_with " + payable_with);

                advertise_total_amount = hashMap.get("advertise_total_amount");
                Log.d(TAG, "onChanged:advertise_total_amount " + advertise_total_amount);

                order_limit_min = hashMap.get("order_limit_min");
                Log.d(TAG, "onChanged:order_limit_min " + order_limit_min);

                order_limit_max = hashMap.get("order_limit_max");
                Log.d(TAG, "onChanged:order_limit_max " + order_limit_max);

                try {
                    ((TextView) view.findViewById(R.id.textView15)).setText(new ReturnEnumInString().getEnumToString(Integer.parseInt(ad_type)) + " - Tk. ");
                    ((TextView) view.findViewById(R.id.textView133)).setText(user_price);
                    ((TextView) view.findViewById(R.id.data_1)).setText(advertise_total_amount + " gm Gold");
                    ((TextView) view.findViewById(R.id.data_2)).setText("Funding Wallet");
                    ((TextView) view.findViewById(R.id.data_3)).setText(order_limit_min + " - " + order_limit_max + " BDT");

                } catch (Exception e) {
                    error = false;
                    Log.e(TAG, "onViewCreated: error", e);
                }

            }
        });


        view.findViewById(R.id.btnContinue).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                error = true;
                Log.d(TAG, "onLongClick: pressing..");
                postAdRequest(ad_type, asset_type, user_price, price_type, payable_with, advertise_total_amount, order_limit_min, order_limit_max);
                return true;
            }

            @Override
            public boolean onLongClickUseDefaultHapticFeedback(@NonNull View v) {
                return View.OnLongClickListener.super.onLongClickUseDefaultHapticFeedback(v);
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
                Log.d(TAG, "finalize: " + error);
                if (error) {

                    requireActivity().finish();
                    //todo
                    Log.d(TAG, "onClick: Successfully commited..");
                } else Log.d(TAG, "onClick: Error commited..");
            }
        });
    }

    private void postAdRequest(String ad_type, String asset_type, String user_price, String price_type, String payable_with, String advertise_total_amount, String order_limit_min, String order_limit_max) {

        viewLoadingAnimation.showLoading(true);

        Log.d(TAG, "onChanged:ad_type " + ad_type);


        Log.d(TAG, "onChanged:asset_type " + asset_type);

        Log.d(TAG, "onChanged:user_price " + user_price);

        Log.d(TAG, "onChanged:price_type " + price_type);

        Log.d(TAG, "onChanged:payable_with " + payable_with);

        Log.d(TAG, "onChanged:advertise_total_amount " + advertise_total_amount);

        Log.d(TAG, "onChanged:order_limit_min " + order_limit_min);

        Log.d(TAG, "onChanged:order_limit_max " + order_limit_max);

        Log.d(TAG, "onChanged:TOKEN " + new UserSessionManager(getActivity()).getToken());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI.PostCreateAdRequest retrofitAPI = retrofit.create(RetrofitAPI.PostCreateAdRequest.class);

        Call<SimpleResponse> call = retrofitAPI.createPostCreateAdRequest("Bearer " + new UserSessionManager(getActivity()).getToken(),
                ad_type,
                asset_type,
                user_price,
                price_type,
                payable_with,
                advertise_total_amount,
                order_limit_max,
                order_limit_min);


        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {


                Log.d(TAG, "onResponse: Status Code: " + response.code());
                if (response.isSuccessful()) {

//                    Log.d(TAG, "onResponse: successful: " + response.body().getMessage());
                    Log.d(TAG, "onResponse: successful: ");
                    Toast.makeText(getActivity(), "Ads Requested for Approval.", Toast.LENGTH_LONG).show();
                    viewLoadingAnimation.showLoading(false);
                    startActivity(new Intent(getActivity(), P2PActivity.class));
                    getActivity().finish();
                } else {
//                    Log.d(TAG, "onResponse: Not successful: "+response.body().getMessage());
                    Log.d(TAG, "onResponse: Not successful: ");
                    Toast.makeText(getActivity(), "Ads Request Not Send..\nTry again.", Toast.LENGTH_LONG).show();
                    viewLoadingAnimation.showLoading(false);
                }


            }


            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                viewLoadingAnimation.showLoading(false);
//                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}
