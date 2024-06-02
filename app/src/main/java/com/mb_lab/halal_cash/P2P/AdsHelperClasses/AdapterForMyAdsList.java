package com.mb_lab.halal_cash.P2P.AdsHelperClasses;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mb_lab.halal_cash.DataModels.GetOwnCreatedAdsResponse;
import com.mb_lab.halal_cash.DataModels.UserAdsResponse;
import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.Util.ConverterUtil;

import java.util.List;

public class AdapterForMyAdsList extends RecyclerView.Adapter<AdapterForMyAdsList.ViewHolder> {
    private static final String TAG = "AdapterForMyAdsList";

    private static List<GetOwnCreatedAdsResponse.Data> mDataSet;

    public AdapterForMyAdsList(List<GetOwnCreatedAdsResponse.Data> data) {
        mDataSet = data;
        Log.d(TAG, "AdapterForMyAdsList: " + mDataSet.get(0).getId());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7;

        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.textView57);
            textView2 = (TextView) itemView.findViewById(R.id.textView64);
            textView3 = (TextView) itemView.findViewById(R.id.textView65);
            textView4 = (TextView) itemView.findViewById(R.id.textView66);
            textView5 = (TextView) itemView.findViewById(R.id.textView67);
            textView6 = (TextView) itemView.findViewById(R.id.textTotalAm);
            textView7 = (TextView) itemView.findViewById(R.id.limit);
//            textView8 = (TextView) itemView.findViewById(R.id.textView);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.p2p_my_ads_list_item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView1.setText(new ReturnEnumInString().getEnumToString(Integer.parseInt(mDataSet.get(position).getAd_type())));
        holder.textView2.setText(new ReturnEnumInString().getEnumToString(Integer.parseInt(mDataSet.get(position).getAsset_type())));

        if (mDataSet.get(position).getPermission_status().equals("73")) {
            holder.textView4.setText("Online");

        } else if (mDataSet.get(position).getPermission_status().equals("75")) {
            holder.textView4.setText("Pending");
        } else {
            holder.textView4.setText("Denied");
        }

        holder.textView5.setText(String.format("TK. %s", new ConverterUtil(TAG).convertStringToString_2f(mDataSet.get(position).getUser_price())));
        holder.textView6.setText(String.format("%s gm Gold", new ConverterUtil(TAG).convertStringToString_2f(mDataSet.get(position).getAdvertise_total_amount())));
        holder.textView7.setText(String.format("%s - %s BDT", new ConverterUtil(TAG).convertStringToString_2f(mDataSet.get(position).getOrder_limit_min()), new ConverterUtil(TAG).convertStringToString_2f(mDataSet.get(position).getOrder_limit_max())));


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
