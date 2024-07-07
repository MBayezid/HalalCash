package com.mb_lab.halal_cash.ApiCalls.CommonApiRequest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBitmapFromUrl extends RetrofitClient {


    private String imageUrl = null;
    private Bitmap bitmap = null;

    public GetBitmapFromUrl(Context context, String TAG) {
        super(context, TAG);
    }

    public void preLoadImageFromUrlAndLoad(String imageUrl, View view) {

        // Clearing Glide's cache
        Glide.get(context).clearMemory();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();


        Glide.with(context)
                .load(imageUrl)
//                .load("https://images.creativefabrica.com/products/previews/2023/10/27/LH874No6w/2XLj7loRuN3Sa7nt65RxsyKSx7Y-mobile.jpg")
                .apply(new RequestOptions().centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(100, 100))
                .placeholder(R.drawable.profile_round_icon)// Adjust size as needed
                .error(R.drawable.profile_round_icon)// Adjust size as needed
                .into((ImageView) view);


    }

    private String processUrl(String imageUrl) {
        return imageUrl.toLowerCase().trim().substring(22);
    }

    public void getImageFromURL(String imageUrl, View view) {


        RetrofitAPI.GetImageFromUrl getImageFromUrl = retrofit.create(RetrofitAPI.GetImageFromUrl.class);

        Log.d(context.getPackageName(), "processUrl: " + this.imageUrl);
        try {

            imageUrl = processUrl(imageUrl);

        } catch (Exception e) {
            Log.d(context.getPackageName(), "GetBitmapFromUrl: image Loading failed");
        }
        // Initialize Retrofit service


        // Execute the request
        Call<ResponseBody> call = getImageFromUrl.getBitmap(imageUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("Download", "onResponse:  response.code() " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Download", "onResponse: Successful");
                    // Image download successful

                    byte[] imageString = new byte[0];
                    try {
                        imageString = response.body().bytes();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
//                    Log.d(context.getPackageName(), "onResponse: byte size: " + imageString.length);
//                    Log.d(context.getPackageName(), "onResponse: byte : " + imageString[8]);
                    // Convert image string to bitmap

                    Log.d(context.getPackageName(), "onResponse: Not Null");
                    bitmap = BitmapFactory.decodeByteArray(imageString, 0, imageString.length, null);


                    if (bitmap != null) {
                        Log.d(context.getPackageName(), "GetBitmapFromUrl: Successfullupdate userImage.........");
                        ((ImageView) view).setImageBitmap(bitmap);
                    } else
                        Log.d(context.getPackageName(), "GetBitmapFromUrl: Failed update userImage.........");
                    // Set bitmap to ImageView

                } else {
                    // Image download failed
                    Log.e("Download", "Image download failed");
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Request failed
                Log.e("Download", "Request failed: " + t.getMessage());
            }
        });

    }


}
