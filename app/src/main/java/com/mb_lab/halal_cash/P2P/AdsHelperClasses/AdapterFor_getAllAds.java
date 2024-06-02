package com.mb_lab.halal_cash.P2P.AdsHelperClasses;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.mb_lab.halal_cash.DataModels.UserAdsResponse;
import com.mb_lab.halal_cash.Enums.AdsType;
import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
import com.mb_lab.halal_cash.P2P.BuySellActions.Buy_Sell_Activity;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

import java.util.List;

public class AdapterFor_getAllAds extends RecyclerView.Adapter<AdapterFor_getAllAds.ViewHolder> {
    private static final String TAG = "AdapterFor_getAllAds";

    private static List<UserAdsResponse.Data> mDataSet;
    private static Context context;

    public AdapterFor_getAllAds(Context c, List<UserAdsResponse.Data> dataSet) {
        mDataSet = dataSet;
        context = c;

    }

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName, tradersAndCompletionRate, unitPriceInBDT, advertise_total_amount, orderLimitation, actionButton;
        private final ImageView profileImage, verificationStatus;
        LinearLayout layout;

        public ViewHolder(View v) {
            super(v);

            layout = (LinearLayout) v.findViewById(R.id.listItem);
            profileImage = (ImageView) v.findViewById(R.id.profileImage);
            verificationStatus = (ImageView) v.findViewById(R.id.verificationStatus);

            userName = (TextView) v.findViewById(R.id.userName);
            tradersAndCompletionRate = (TextView) v.findViewById(R.id.tradersAndCompletionRate);
            unitPriceInBDT = (TextView) v.findViewById(R.id.unitPriceInBDT);
            advertise_total_amount = (TextView) v.findViewById(R.id.advertise_total_amount);
            orderLimitation = (TextView) v.findViewById(R.id.orderLimitation);
            actionButton = (TextView) v.findViewById(R.id.actionButton);

            // Define click listener for the ViewHolder's View.
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");

                    if (new UserSessionManager(context.getApplicationContext()).getUserType().toLowerCase().trim().equals("agent")) {
                        try {
                            Intent intent = new Intent(context.getApplicationContext(), Buy_Sell_Activity.class);
                            intent.putExtra("data", mDataSet.get(getAdapterPosition()));

                            context.startActivity(intent);

                        } catch (Exception ignored) {
                            Log.i(TAG, "onClick: ignored :" + ignored);

                        }
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Apply for agent verification!!", Toast.LENGTH_LONG).show();
                    }


                }
            });


        }

//        public TextView getTextView() {
//            return textView;
//        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)


    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.p2p_list_item_view, viewGroup, false);
        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Set background color based on position
        if (position % 2 == 0) {
            // Even position
            viewHolder.layout.setBackgroundColor(0xFF151C2C);
        } else {
            // Odd position
            viewHolder.layout.setBackgroundColor(0xFF21252E);
        }

        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        viewHolder.userName.setText(mDataSet.get(position).getName());
        viewHolder.tradersAndCompletionRate.setText("0 Traders   |   Completion 0.0%");
        viewHolder.unitPriceInBDT.setText("TK. " + String.format("%.2f", Float.parseFloat(mDataSet.get(position).getUser_price().trim())));
        float advertised_total  = 0;
        String advertised_total_amount_in_gold=null;
        try {
//            advertised_total = Float.parseFloat(mDataSet.get(position).getAdvertise_total_amount().trim()) / Float.parseFloat(mDataSet.get(position).getUser_price().trim());
            advertised_total =  Float.parseFloat(mDataSet.get(position).getAdvertise_total_amount().trim());
            advertised_total_amount_in_gold =  String.format("%.2f",advertised_total);
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: convertion failed: ", e);
        }
        viewHolder.advertise_total_amount.setText(String.format("Gold Amount %s grm" ,advertised_total_amount_in_gold));
        try {

            viewHolder.orderLimitation.setText("Limit  Tk. " + String.format("%.2f", Float.parseFloat(mDataSet.get(position).getOrder_limit_min().trim())) + " - Tk. " + String.format("%.2f", Float.parseFloat(mDataSet.get(position).getOrder_limit_max().trim())));
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: failed Order Range:", e);
        }


        int AdType = Integer.parseInt(mDataSet.get(position).getAd_type().trim());
        if (AdType == AdsType.BUY.getValue()) {
            viewHolder.actionButton.setText(new ReturnEnumInString().getEnumToString(AdsType.SELL.getValue()));

            viewHolder.actionButton.setBackgroundTintList(ColorStateList.valueOf(0xFFF3475C));
        } else {
            viewHolder.actionButton.setText(new ReturnEnumInString().getEnumToString(AdsType.BUY.getValue()));
            viewHolder.actionButton.setBackgroundTintList(ColorStateList.valueOf(0xFF2EBC87));
        }

        //todo


    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


//    private static void unlockTransaction(Context context, String transactionId) {
//
//        Dialog dialog = new Dialog(context);
//        dialog.setCancelable(true);
//        dialog.setContentView(R.layout.dialog_unlock_pending_transaction);
////        TODO AsserTypeEnum must return Currency in String
//
//
//        dialog.findViewById(R.id.unlockBtn).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                // TODO: 3/19/2024 make unlock send money API
//                Log.d(TAG, "onClick: make unlock send money API ");
//                try {
//                    tryUnlockingTransaction(transactionId, ((EditText) dialog.findViewById(R.id.editText)).getText().toString());
//
//
//                } catch (Exception e) {
//                    Log.e(TAG, "onClick: e: ", e);
//                }
//
//
//            }
//        });
//
//        dialog.create();
//        dialog.show();
//    }
//
//    private static void tryUnlockingTransaction(String trans_id, String pin) {
//
////        new updateUiAnimation().setLoadingStart(PaySend_Successful.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
//        android.util.Log.d(TAG, "sendData: started..");
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        // below line is to create an instance for our retrofit api class.
//        RetrofitAPI.GetUnlockReceivedTransaction unlockReceivedTransaction = retrofit.create(RetrofitAPI.GetUnlockReceivedTransaction.class);
//        Call<JSONObject> asynCall = unlockReceivedTransaction.createGetTryUnlockTransaction("Bearer 28|o3TMSW4IrduQAOcgPJBDLCBDTwLpLih3DaNbd5xP357b0834", trans_id, pin);
//
//        // on below line we are executing our method.
//        asynCall.enqueue(new Callback<JSONObject>() {
//            @Override
//            public void onResponse(@NonNull Call<JSONObject> call, @NonNull Response<JSONObject> response) {
//                Log.d(TAG, "onResponse: ");
////                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
//                Log.d(TAG, "onResponse: code: " + response.code());
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "onResponse: Successfully");
////                    context.startActivity(new Intent(context,ReciclerViewActivity.class));
//                    try {
//
////                    Log.d(TAG, "onResponse: JsonObjectData: "+ response.body().getBoolean("status"));
////                    Log.d(TAG, "onResponse: JsonObjectData: "+ response.body().getString("message"));
////                    Log.d(TAG, "onResponse: JsonObjectData: "+ response.message());
//                        Log.d(TAG, "onResponse: JsonObjectData: " + response.raw().toString());
////                    Log.d(TAG, "onResponse: JsonObjectData: "+ response.body().getJSONObject("data").getString("receiver_pay_id"));
//                    } catch (Exception e) {
//                        Log.e(TAG, "onResponse: Error", e);
//                    }
//
//                } else {
////                    Toast.makeText(MainActivity.this, "Encountered an error...", Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "onResponse: Failed");
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<JSONObject> call, Throwable t) {
//                // setting text to our text view when
//                // we get error response from API.
//                android.util.Log.e(TAG, "onFailure: " + t.getMessage());
//                Log.d(TAG, "onResponse: Failed");
//
//
////                ((EditText) findViewById(R.id.editText)).setError("Network Error.");
//
//            }
//        });
//
//    }
}