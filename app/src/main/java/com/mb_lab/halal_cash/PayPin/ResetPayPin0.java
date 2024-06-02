package com.mb_lab.halal_cash.PayPin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.PayPin.ResetPayPin;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.SimpleResponse;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetPayPin0 extends AppCompatActivity {
    private EditText editText1, editText2, editText3, editText4, editText5, editText11, editText22, editText33, editText44, editText55;

    private EditText[] editTexts;
    private final static String TAG = "ResetPayPin0";
    private PinTextWatcher pinTextWatcher;
    ViewLoadingAnimation viewLoadingAnimation;
    ResetPayPin resetPayPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_pay_pin_0);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            v.setBackgroundColor(getColor(R.color.black));
            return insets;
        });
        viewLoadingAnimation = new ViewLoadingAnimation(ResetPayPin0.this);
        editText1 = (EditText) findViewById(R.id.editPin1);
        editText2 = (EditText) findViewById(R.id.editPin2);
        editText3 = (EditText) findViewById(R.id.editPin3);
        editText4 = (EditText) findViewById(R.id.editPin4);
        editText5 = (EditText) findViewById(R.id.editPin5);
        editText11 = (EditText) findViewById(R.id.editPin11);
        editText22 = (EditText) findViewById(R.id.editPin22);
        editText33 = (EditText) findViewById(R.id.editPin33);
        editText44 = (EditText) findViewById(R.id.editPin44);
        editText55 = (EditText) findViewById(R.id.editPin55);

        editTexts = new EditText[]{editText1, editText2, editText3, editText4, editText5, editText11, editText22, editText33, editText44, editText55};
//
//        pinTextWatcher = new PinTextWatcher(editTexts);

        editText1.addTextChangedListener(new ResetPayPin0.PinTextWatcher(0));
        editText2.addTextChangedListener(new ResetPayPin0.PinTextWatcher(1));
        editText3.addTextChangedListener(new ResetPayPin0.PinTextWatcher(2));
        editText4.addTextChangedListener(new ResetPayPin0.PinTextWatcher(3));
        editText5.addTextChangedListener(new ResetPayPin0.PinTextWatcher(4));
        editText11.addTextChangedListener(new ResetPayPin0.PinTextWatcher(5));
        editText22.addTextChangedListener(new ResetPayPin0.PinTextWatcher(6));
        editText33.addTextChangedListener(new ResetPayPin0.PinTextWatcher(7));
        editText44.addTextChangedListener(new ResetPayPin0.PinTextWatcher(8));
        editText55.addTextChangedListener(new ResetPayPin0.PinTextWatcher(9));
//
        editText1.setOnKeyListener(new ResetPayPin0.PinOnKeyListener(0));
        editText2.setOnKeyListener(new ResetPayPin0.PinOnKeyListener(1));
        editText3.setOnKeyListener(new ResetPayPin0.PinOnKeyListener(2));
        editText4.setOnKeyListener(new ResetPayPin0.PinOnKeyListener(3));
        editText5.setOnKeyListener(new ResetPayPin0.PinOnKeyListener(4));
        editText11.setOnKeyListener(new ResetPayPin0.PinOnKeyListener(5));
        editText22.setOnKeyListener(new ResetPayPin0.PinOnKeyListener(6));
        editText33.setOnKeyListener(new ResetPayPin0.PinOnKeyListener(7));
        editText44.setOnKeyListener(new ResetPayPin0.PinOnKeyListener(8));
        editText55.setOnKeyListener(new ResetPayPin0.PinOnKeyListener(9));


        resetPayPin = new ResetPayPin(ResetPayPin0.this, TAG);
        findViewById(R.id.textViewContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PayPin0, PayPin1;

                try {
                    PayPin0 = editText1.getText().toString()
                            + "" + editText2.getText().toString()
                            + "" + editText3.getText().toString()
                            + "" + editText4.getText().toString()
                            + "" + editText5.getText().toString();

                    PayPin1 = editText11.getText().toString()
                            + "" + editText22.getText().toString()
                            + "" + editText33.getText().toString()
                            + "" + editText44.getText().toString()
                            + "" + editText55.getText().toString();

                    Log.d(TAG, "onClick: payPin 0: " + PayPin0);
                    Log.d(TAG, "onClick: payPin 1: " + PayPin1);
                    Log.d(TAG, "onClick: payPin 0 & 1 len: " + PayPin0.length() + " " + PayPin1.length());

                    if (PayPin0.equals(PayPin1) && PayPin0.length() == 5) {
                        // TODO: 3/14/2024
                        Log.d(TAG, "onClick: Calling setPin Function...");
//                        setPayPin(PayPin0);
                        resetPayPin.call(new ResetPayPin.OnPayPinReset() {
                            @Override
                            public void onSuccess(boolean isSuccess) {
                                if (isSuccess) {
                                    // Handle success
                                    Toast.makeText(ResetPayPin0.this, "Pay Pin Changed successfully.", Toast.LENGTH_LONG).show();
                                    finish();


                                } else {
                                    // Handle failure
                                    Toast.makeText(ResetPayPin0.this, "Pay Pin not Changed successfully!!.", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        }, PayPin0);

                    } else {
                        Toast.makeText(ResetPayPin0.this, "Pay Pin Not Matched!.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ResetPayPin0.this, "Enter Pay Pin Properly!.", Toast.LENGTH_LONG).show();
                }


            }
        });
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

    }

    private void setPayPin(String PayPin) {

//        new updateUiAnimation().setLoadingStart(ResetPayPin0.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
        viewLoadingAnimation.showLoading(true);
        Log.d(TAG, "setPayPin: Started...");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.SetPayPin validReceiverInformation = retrofit.create(RetrofitAPI.SetPayPin.class);
        Call<SimpleResponse> asynCall = validReceiverInformation.createGetSetPayPin("Bearer " + new UserSessionManager(ResetPayPin0.this).getToken(), PayPin);

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(@NonNull Call<SimpleResponse> call, @NonNull Response<SimpleResponse> response) {
                Log.d(TAG, "onResponse: ");
                if (response.isSuccessful() && response.code() == 200) {
                    Log.d(TAG, "onResponse: response Successful..");


//                    new updateUiAnimation().setLoadingEnd(ResetPayPin0.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
                    viewLoadingAnimation.showLoading(false);
                    // 3/14/2024  Pay Pin Successfully Recorded
                    Toast.makeText(ResetPayPin0.this, "Pay Pin Successfully Recorded.", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(PaySend_2.this,PayPin0.class));
                    finish();


                } else {
                    // 3/14/2024  Pay Pin not Recorded.
//                    new updateUiAnimation().setLoadingEnd(ResetPayPin0.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
                    viewLoadingAnimation.showLoading(false);
                    Toast.makeText(ResetPayPin0.this, "Pay Pin not Recorded.", Toast.LENGTH_LONG).show();
                    Toast.makeText(ResetPayPin0.this, "Something went wrong!\nContact Support...", Toast.LENGTH_LONG).show();
                    finish();
                }


            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
//                new updateUiAnimation().setLoadingEnd(ResetPayPin0.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
                Toast.makeText(ResetPayPin0.this, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
//                ((EditText) findViewById(R.id.editText)).setError("Network Error.");

            }
        });
    }

    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        //    private EditText[] editTexts;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

//    PinTextWatcher(EditText[] editTexts) {
//        this.editTexts = editTexts;
//
//    }

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;
//        private EditText[] editTexts;

//        PinOnKeyListener(EditText[] editTexts) {
//            this.editTexts = editTexts;
//        }

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }

    }
}