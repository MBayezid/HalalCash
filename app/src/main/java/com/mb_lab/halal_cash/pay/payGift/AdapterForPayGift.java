package com.mb_lab.halal_cash.pay.payGift;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mb_lab.halal_cash.DataModels.HomeNotificationResponse;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.home.homeNotification.AdapterForHomeNotification;
import com.mb_lab.mbviewlib.ImageViewManagment.RoundImageView;

import java.util.List;

public class AdapterForPayGift extends RecyclerView.Adapter<AdapterForPayGift.ViewHolder> {
    private static final String TAG = "AdapterForPayGift";

    private static List<HomeNotificationResponse.Data> mDataSet;

    public AdapterForPayGift(List<HomeNotificationResponse.Data> data) {
        mDataSet = data;
//        Log.d(TAG, "AdapterForOrderHistory: " + mDataSet.get(0).getAd_trans_id());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, description, actionButton;
        private final RoundImageView roundImageView;
        private final ConstraintLayout item_holder;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textView86);
            description = (TextView) itemView.findViewById(R.id.textView85);
            actionButton = (TextView) itemView.findViewById(R.id.textView87);
            roundImageView = itemView.findViewById(R.id.roundImageView);
            item_holder = itemView.findViewById(R.id.item_holder);

//            actionButton.setVisibility(View.GONE);


        }
    }

    @Override
    public AdapterForPayGift.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_notification_item_view, parent, false);
        return new AdapterForPayGift.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterForPayGift.ViewHolder holder, int position) {

        holder.title.setText(mDataSet.get(position).getTitle());
        holder.description.setText(mDataSet.get(position).getDescription());
        holder.actionButton.setText("Read more");
        // Set background color based on position
        if (position % 2 == 0) {
            // Even position

            holder.item_holder.setBackgroundTintList(ColorStateList.valueOf(0xFF151C2C));

        } else {
            // Odd position
            holder.item_holder.setBackgroundTintList(ColorStateList.valueOf(0xFF21252E));
        }


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
