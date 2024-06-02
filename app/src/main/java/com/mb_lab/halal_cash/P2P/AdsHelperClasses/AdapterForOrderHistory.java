package com.mb_lab.halal_cash.P2P.AdsHelperClasses;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mb_lab.halal_cash.DataModels.GetOwnCreatedAdsResponse;
import com.mb_lab.halal_cash.DataModels.UserOrderHistoryResponse;
import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.Util.ConverterUtil;

import java.util.List;

public class AdapterForOrderHistory extends RecyclerView.Adapter<AdapterForOrderHistory.ViewHolder> {
    private static final String TAG = "AdapterForOrderHistory";

    private static List<UserOrderHistoryResponse.Data> mDataSet;

    public AdapterForOrderHistory(List<UserOrderHistoryResponse.Data> data) {
        mDataSet = data;
//        Log.d(TAG, "AdapterForOrderHistory: " + mDataSet.get(0).getAd_trans_id());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView user_price, ads_unique_num,date,textView1, textView2, textView3, textView4, textView5, textView6 ;

        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.textView57);
            textView2 = (TextView) itemView.findViewById(R.id.textView64);
            textView3 = (TextView) itemView.findViewById(R.id.textView65);
            textView4 = (TextView) itemView.findViewById(R.id.textView66);
            textView5 = (TextView) itemView.findViewById(R.id.textView67);
            textView6 = (TextView) itemView.findViewById(R.id.textTotalAm);
            user_price = (TextView) itemView.findViewById(R.id.user_price);
            date = (TextView) itemView.findViewById(R.id.date);
            ads_unique_num = (TextView) itemView.findViewById(R.id.ads_unique_num);
//            textView8 = (TextView) itemView.findViewById(R.id.textView);


        }
    }

    @Override
    public AdapterForOrderHistory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.p2p_ads_order_transaction_history_item_view, parent, false);
        return new AdapterForOrderHistory.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterForOrderHistory.ViewHolder holder, int position) {
        holder.textView1.setText(new ReturnEnumInString().getEnumToString(Integer.parseInt(mDataSet.get(position).getAd_type())));
//        holder.textView2.setText(new ReturnEnumInString().getEnumToString(Integer.parseInt(mDataSet.get(position).getAsset_type())));


//        holder.textView5.setText(String.format("TK. %s", new ConverterUtil(TAG).convertStringToString_2f(mDataSet.get(position).getUser_price())));
        holder.textView6.setText(String.format("%s gm Gold", new ConverterUtil(TAG).convertStringToString_2f(mDataSet.get(position).getReceivable_amount())));
        holder.user_price.setText(String.format("%s BDT", new ConverterUtil(TAG).convertStringToString_2f(mDataSet.get(position).getUser_price())));
        holder.date.setText( mDataSet.get(position).getDate());
        holder.ads_unique_num.setText( mDataSet.get(position).getAds_unique_num());


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
