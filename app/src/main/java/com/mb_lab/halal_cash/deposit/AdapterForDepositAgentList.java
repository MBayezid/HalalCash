package com.mb_lab.halal_cash.deposit;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mb_lab.halal_cash.DataModels.GetDepositAgentListResponse;
import com.mb_lab.halal_cash.DataModels.UserOrderHistoryResponse;
import com.mb_lab.halal_cash.DataModels.UserTransactionHistoryResponse;
import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
import com.mb_lab.halal_cash.Enums.TransactionStatusEnums;
import com.mb_lab.halal_cash.Enums.TransactionTypeEnums;
import com.mb_lab.halal_cash.P2P.AdsHelperClasses.AdapterForOrderHistory;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.Util.ConverterUtil;
import com.mb_lab.halal_cash.Util.MailSupport;

import java.util.List;

public class AdapterForDepositAgentList extends RecyclerView.Adapter<AdapterForDepositAgentList.ViewHolder> {
    private static final String TAG = "AdapterForDepositAgentList";

    private static List<GetDepositAgentListResponse.Data> mDataSet;

    public AdapterForDepositAgentList(List<GetDepositAgentListResponse.Data> data) {
        mDataSet = data;
//        Log.d(TAG, "AdapterForOrderHistory: " + mDataSet.get(0).getAd_trans_id());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, number, payment_method, btnContinue;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.number);
            payment_method = (TextView) itemView.findViewById(R.id.payment_method);
            btnContinue = (TextView) itemView.findViewById(R.id.btnContinue);

            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogPickAgent(v.getContext(), mDataSet.get(getAdapterPosition()));
                }
            });


        }
    }

    @Override
    public AdapterForDepositAgentList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deposit_agent_list_item_view, parent, false);
        return new AdapterForDepositAgentList.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterForDepositAgentList.ViewHolder holder, int position) {
        holder.name.setText(mDataSet.get(position).getName());
        holder.number.setText(mDataSet.get(position).getNumber());
        holder.payment_method.setText(mDataSet.get(position).getPayment_method());


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    private static void DialogPickAgent(Context context, GetDepositAgentListResponse.Data data) {

        Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_pick_agent);
//        TODO AsserTypeEnum must return Currency in String
//        Log.d(TAG, "Trans ID " + data.getTrans_id());


        ((TextView) dialog.findViewById(R.id.name)).setText(data.getName());
        ((TextView) dialog.findViewById(R.id.number)).setText(data.getNumber());
        ((TextView) dialog.findViewById(R.id.payment_method)).setText(data.getPayment_method());


        dialog.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // TODO: 3/19/2024 make unlock send money API
                Log.d(TAG, "onClick: make unlock send money API ");
                try {
                    String Amount = ((EditText) dialog.findViewById(R.id.depositAmount)).getText().toString().trim();
                    String TransactionId = ((EditText) dialog.findViewById(R.id.depositTrxNumber)).getText().toString().trim();
                    if (Amount.isEmpty() || TransactionId.isEmpty()){
                        ((EditText) dialog.findViewById(R.id.depositAmount)).setError("Enter Valid Amount");
                        ((EditText) dialog.findViewById(R.id.depositTrxNumber)).setError("Enter Valid Trans Id.");
                    }else {
                        new MailSupport(context,data.getName(),data.getNumber(),data.getPayment_method(),Amount,TransactionId,new UserSessionManager(context).getUserPayId());
                    }



                } catch (Exception e) {
                    Log.e(TAG, "onClick: e: ", e);
                    ((EditText) dialog.findViewById(R.id.depositAmount)).setError("Enter Valid Amount");
                    ((EditText) dialog.findViewById(R.id.depositTrxNumber)).setError("Enter Valid Trans Id.");
                }


            }
        });

        dialog.create();
        dialog.show();
    }
}
