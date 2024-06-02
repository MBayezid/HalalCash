package com.mb_lab.halal_cash.pay.payFragments.PayHistoryFragmentSubClass;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.DataModels.UserTransactionHistoryResponse;
import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
import com.mb_lab.halal_cash.Enums.TransactionStatusEnums;
import com.mb_lab.halal_cash.Enums.TransactionTypeEnums;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomAdapterForPayHistory extends RecyclerView.Adapter<CustomAdapterForPayHistory.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private static List<UserTransactionHistoryResponse.Data> mDataSet;
    private static Context context;

    public CustomAdapterForPayHistory(List<UserTransactionHistoryResponse.Data> dataSet, Context context) {
        mDataSet = dataSet;
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView, textView1, textView2, textView3, textView11, textView33;
        private final ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textView);
            textView1 = (TextView) v.findViewById(R.id.textView1);
            textView2 = (TextView) v.findViewById(R.id.textView2);
            textView3 = (TextView) v.findViewById(R.id.textView3);
            textView11 = (TextView) v.findViewById(R.id.textView11);
            textView33 = (TextView) v.findViewById(R.id.textView33);

            imageView = (ImageView) v.findViewById(R.id.isLocked);


            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");


                    try {
                        Log.d(TAG, "Trans ID " + mDataSet.get(getAdapterPosition()).getTrans_id());
//
//                        int status = Integer.parseInt(mDataSet.get(getAdapterPosition()).getStatus());
//                        int transactionType = Integer.parseInt(mDataSet.get(getAdapterPosition()).getTransaction_type());
//                        String transactionId = mDataSet.get(getAdapterPosition()).getTrans_id();
//
//                        Log.d(TAG, "getTransaction_type " + new ReturnEnumInString().getEnumToString(transactionType));
//                        Log.d(TAG, "getTransaction_status " + new ReturnEnumInString().getEnumToString(status));

                        DialogUnlockTransaction(context, mDataSet.get(getAdapterPosition()));


                    } catch (Exception ignored) {
                        Log.i(TAG, "onClick: ignored :" + ignored);

                    }


                }
            });


        }

        public TextView getTextView() {
            return textView;
        }
    }


    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pay_history_row_item, viewGroup, false);
        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        int transType = Integer.parseInt(mDataSet.get(position).getTransaction_type().trim());
        String name = mDataSet.get(position).getName().trim();
        int currencyType = Integer.parseInt(mDataSet.get(position).getAsset_type().trim());

        viewHolder.textView.setText(new ReturnEnumInString().getEnumToString(transType));
        if (name.isEmpty())
            viewHolder.textView1.setText(name);
        else viewHolder.textView1.setText("Anonymous");

        if (transType == TransactionTypeEnums.SEND.getValue() || transType == TransactionTypeEnums.WITHDRAW.getValue() || transType == TransactionTypeEnums.TOPUP.getValue()) {
            viewHolder.textView11.setText(String.format("-%s %s", mDataSet.get(position).getAmount(), new ReturnEnumInString().getEnumToString(currencyType)));

        } else if (transType == TransactionTypeEnums.RECEIVED.getValue() && mDataSet.get(position).getStatus().equals(String.valueOf(TransactionStatusEnums.CLAIMED.getValue())) || transType == TransactionTypeEnums.GIFT.getValue()) {
            viewHolder.textView11.setText(String.format("+%s %s", mDataSet.get(position).getAmount(), new ReturnEnumInString().getEnumToString(currencyType)));

        } else if (transType == TransactionTypeEnums.REFUND.getValue() && mDataSet.get(position).getStatus().equals(String.valueOf(TransactionStatusEnums.REFUNDED.getValue())) || transType == TransactionTypeEnums.GIFT.getValue()) {
            viewHolder.textView11.setText(String.format("+%s %s", mDataSet.get(position).getAmount(), new ReturnEnumInString().getEnumToString(currencyType)));

        } else {
            viewHolder.textView11.setText(String.format("%s %s", mDataSet.get(position).getAmount(), new ReturnEnumInString().getEnumToString(currencyType)));
        }


        viewHolder.textView2.setText(mDataSet.get(position).getDate());


        int transStatus = Integer.parseInt(mDataSet.get(position).getStatus());
        viewHolder.textView33.setText(new ReturnEnumInString().getEnumToString(transStatus));
        if (transStatus == TransactionStatusEnums.PENDING.getValue()) {
            viewHolder.imageView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imageView.setVisibility(View.GONE);
        }

    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    private static void DialogUnlockTransaction(Context context, UserTransactionHistoryResponse.Data data) {

        Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_unlock_pending_transaction);
//        TODO AsserTypeEnum must return Currency in String
        Log.d(TAG, "Trans ID " + data.getTrans_id());

        int status = Integer.parseInt(data.getStatus());
        int transactionType = Integer.parseInt(data.getTransaction_type());
        String transactionId = data.getTrans_id();

        if (transactionType == TransactionTypeEnums.RECEIVED.getValue() && status == TransactionStatusEnums.PENDING.getValue()) {
            Log.d(TAG, "onClick: Action " + "Show Unlock Transaction Dialog");
            dialog.findViewById(R.id.constraintLayout17).setVisibility(View.GONE);
            dialog.findViewById(R.id.constraintLayout18).setVisibility(View.VISIBLE);

        } else if (transactionType == TransactionTypeEnums.RECEIVED.getValue() && status == TransactionStatusEnums.CLAIMED.getValue()) {
            dialog.findViewById(R.id.constraintLayout17).setVisibility(View.GONE);
            dialog.findViewById(R.id.constraintLayout18).setVisibility(View.GONE);

        } else if (transactionType == TransactionTypeEnums.SEND.getValue() && status == TransactionStatusEnums.SENT.getValue()) {
            dialog.findViewById(R.id.constraintLayout17).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.constraintLayout18).setVisibility(View.GONE);


        } else {
            Log.d(TAG, "onClick: Action " + "Show Transaction Information only  Dialog");
            dialog.findViewById(R.id.constraintLayout17).setVisibility(View.GONE);
            dialog.findViewById(R.id.constraintLayout18).setVisibility(View.GONE);
        }

        ((TextView) dialog.findViewById(R.id.asset_type)).setText(data.getAsset_type());
        ((TextView) dialog.findViewById(R.id.transaction_type)).setText(data.getTransaction_type());
        ((TextView) dialog.findViewById(R.id.trans_id)).setText(data.getTrans_id());
        ((TextView) dialog.findViewById(R.id.amount)).setText(data.getAmount());
        ((TextView) dialog.findViewById(R.id.status)).setText(data.getStatus());
        ((TextView) dialog.findViewById(R.id.note)).setText(data.getNote());
        ((TextView) dialog.findViewById(R.id.date)).setText(data.getDate());
        ((TextView) dialog.findViewById(R.id.name)).setText(data.getName());
        ((TextView) dialog.findViewById(R.id.payId)).setText(data.getPayId());
        ((TextView) dialog.findViewById(R.id.userType)).setText(data.getUserType());
        ((TextView) dialog.findViewById(R.id.trans_pin)).setText(data.getPin());


        dialog.findViewById(R.id.unlockBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // TODO: 3/19/2024 make unlock send money API
                Log.d(TAG, "onClick: make unlock send money API ");
                try {
                    tryUnlockingTransaction(dialog, data.getTrans_id(), ((EditText) dialog.findViewById(R.id.editText)).getText().toString());


                } catch (Exception e) {
                    Log.e(TAG, "onClick: e: ", e);
                }


            }
        });

        dialog.create();
        dialog.show();
    }

    private static void tryUnlockingTransaction(Dialog dialog, String trans_id, String pin) {

//        new updateUiAnimation().setLoadingStart(PaySend_Successful.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
        android.util.Log.d(TAG, "sendData: started..");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.GetUnlockReceivedTransaction unlockReceivedTransaction = retrofit.create(RetrofitAPI.GetUnlockReceivedTransaction.class);
        Call<JsonObject> asynCall = unlockReceivedTransaction.createGetTryUnlockTransaction("Bearer " + new UserSessionManager(context).getToken(), trans_id, pin);

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.d(TAG, "onResponse: ");
                dialog.dismiss();
                Log.d(TAG, "onResponse: code: " + response.code());

                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: trans unlocked Successfully");
                    Toast.makeText(context, "successfully unlocked..", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(context, "Unlocking failed!", Toast.LENGTH_LONG).show();
//                    try {
//
//                        Log.d(TAG, "onResponse: raw " + response.raw());
//                        Log.d(TAG, "onResponse: body " + response.body());
//                        Log.d(TAG, "onResponse: body " + response.body().toString());
//                    } catch (Exception e) {
//                        Log.e(TAG, "onResponse: e", e);
//                    }

                    Log.d(TAG, "onResponse: trans unlocked Failed");
                }



            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                android.util.Log.e(TAG, "onFailure: " + t.getMessage());
                dialog.dismiss();
                Toast.makeText(context, "Unlocking failed!\nConnection error", Toast.LENGTH_LONG).show();


            }
        });

    }
}