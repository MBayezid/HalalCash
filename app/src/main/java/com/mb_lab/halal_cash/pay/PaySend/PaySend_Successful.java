package com.mb_lab.halal_cash.pay.PaySend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.UserPaySendConfirmationResponse;
import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
import com.mb_lab.halal_cash.Util.CopyTextToClipBoard;
import com.mb_lab.halal_cash.pay.PayActivity;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaySend_Successful extends AppCompatActivity {
    private final static String TAG = "PaySend_Successful";

    ViewLoadingAnimation viewLoadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay_send_successful);

        viewLoadingAnimation = new ViewLoadingAnimation(PaySend_Successful.this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewLoadingAnimation = new ViewLoadingAnimation(PaySend_Successful.this);
        try {
//            viewLoadingAnimation.showLoading(true);
//            ((ImageView)findViewById(R.id.imageView16)).setForegroundTintMode(PorterDuff.Mode.ADD);
            ((ImageView) findViewById(R.id.imageView16)).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.textPaySucc)).setText("Payment Processing..");
            findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
            findViewById(R.id.btnDone).setVisibility(View.INVISIBLE);
            findViewById(R.id.textViewContinue).setVisibility(View.INVISIBLE);


            Bundle bundle = getIntent().getExtras();
            Log.d(TAG, "onCreate: trying getting Previously added data.. ");
            String receiver_pay_id = bundle.getString("receiver_pay_id");
            int transaction_type = bundle.getInt("transaction_type");
            int asset_type = bundle.getInt("asset_type");
            float amount = bundle.getFloat("amount");
            String pay_pin = bundle.getString("pay_pin");
            String note = bundle.getString("note");

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("receiver_pay_id", receiver_pay_id);
            hashMap.put("transaction_type", String.valueOf(transaction_type));
            hashMap.put("asset_type", String.valueOf(asset_type));
            hashMap.put("amount", String.valueOf(amount));
            hashMap.put("pay_pin", pay_pin);
            if (note != null)
                hashMap.put("note", note);
            else hashMap.put("note", "");


            Log.d(TAG, "onCreate: receiver_pay_id:" + receiver_pay_id);
            Log.d(TAG, "onCreate: transaction_type:" + transaction_type);

            sendData(hashMap);

        } catch (Exception e) {

//            viewLoadingAnimation.showLoading(false);
            Log.e(TAG, "onCreate: Failed to get Data from intent..", e);
            finish();
        }


        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaySend_Successful.this, PayActivity.class));
                finish();
            }
        });
        findViewById(R.id.textViewContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaySend_Successful.this, PaySend.class));
                finishAfterTransition();
            }
        });


        findViewById(R.id.receverPayId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CopyTextToClipBoard(PaySend_Successful.this, "Pay Id", ((TextView) findViewById(R.id.textView37)).getText().toString().trim());
            }
        });
        findViewById(R.id.orderID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CopyTextToClipBoard(PaySend_Successful.this, "Order Id", ((TextView) findViewById(R.id.textView38)).getText().toString().trim());
            }
        });
        findViewById(R.id.payPin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CopyTextToClipBoard(PaySend_Successful.this, "Pay Pin", ((TextView) findViewById(R.id.textView36)).getText().toString().trim());
            }
        });
    }

    private void sendData(HashMap<String, String> hashMap) {

        viewLoadingAnimation.showLoading(true);

        Log.d(TAG, "sendData: started..");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.SendPaySendTransInfoAtOnce sendPaySendTransInfoAtOnce = retrofit.create(RetrofitAPI.SendPaySendTransInfoAtOnce.class);
        Call<UserPaySendConfirmationResponse> asynCall = sendPaySendTransInfoAtOnce.createSendPaySendTransInfoAtOnce("Bearer " + new UserSessionManager(PaySend_Successful.this).getToken(), hashMap);

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<UserPaySendConfirmationResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserPaySendConfirmationResponse> call, @NonNull Response<UserPaySendConfirmationResponse> response) {
                Log.d(TAG, "onResponse: ");
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: response Successful..");


                    ((ImageView) findViewById(R.id.imageView16)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.textPaySucc)).setText("Payment Successful");
                    findViewById(R.id.btnBack).setVisibility(View.VISIBLE);
                    findViewById(R.id.btnDone).setVisibility(View.VISIBLE);
                    findViewById(R.id.textViewContinue).setVisibility(View.VISIBLE);
                    Log.d(TAG, "onResponse: " + response.body().getMessage());

                    ((TextView) findViewById(R.id.textView37)).setText(response.body().getData().getReceiver_pay_id() + "(Pay ID)" + "\n" + "");
                    ((TextView) findViewById(R.id.textView38)).setText(response.body().getData().getTrans_id());//todo OrderID copiable
                    ((TextView) findViewById(R.id.textView31)).setText("Funding Wallet");//todo make enum for Wallet types
                    ((TextView) findViewById(R.id.paidWith)).setText(response.body().getData().getAmount() + " " + new ReturnEnumInString().getEnumToString(Integer.parseInt(response.body().getData().getAsset_type())));
                    ((TextView) findViewById(R.id.goldPrice)).setText("Unknown gm Gold");//todo update gold with real gold price Data
                    ((TextView) findViewById(R.id.textView36)).setText(String.valueOf(response.body().getData().getTransaction_pin()));//todo make pin copiable

                    viewLoadingAnimation.showLoading(false);
                } else {
                    Toast.makeText(PaySend_Successful.this, "Encountered an error...", Toast.LENGTH_SHORT).show();
                    viewLoadingAnimation.showLoading(false);
                    finishAfterTransition();


                }



            }

            @Override
            public void onFailure(Call<UserPaySendConfirmationResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(PaySend_Successful.this, "Something went wrong!\nCheck internet connection.", Toast.LENGTH_SHORT).show();
                viewLoadingAnimation.showLoading(false);
                finish();
//                ((EditText) findViewById(R.id.editText)).setError("Network Error.");

            }
        });
    }

}