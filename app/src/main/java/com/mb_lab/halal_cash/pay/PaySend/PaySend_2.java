package com.mb_lab.halal_cash.pay.PaySend;

import static com.mb_lab.halal_cash.Enums.AssetTypeEnums.BDT;
import static com.mb_lab.halal_cash.Enums.AssetTypeEnums.GOLD;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.PayPin.CheckPayPinStatus;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.GetReceiverInformationResponse;
import com.mb_lab.halal_cash.DataModels.SimpleResponse;
import com.mb_lab.halal_cash.DataModels.UserLoginResponse;
import com.mb_lab.halal_cash.Enums.AssetTypeEnums;
import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
import com.mb_lab.halal_cash.Enums.TransactionTypeEnums;
import com.mb_lab.halal_cash.PayPin.PayPin0;
import com.mb_lab.halal_cash.PayPin.ResetPayPin0;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserCurrentGoldPrice;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.Util.CopyTextToClipBoard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaySend_2 extends AppCompatActivity {
    private static final String TAG = "PaySend_2";
    UserSessionManager userSessionManager;

    GetReceiverInformationResponse userLoginResponse;

    //    String amount,String currency,String equivalentPriceInGold,String receiverPayId,String receiverName,String wallerType;
    String userIdType, receiverPayId, receiverName;
    String note = null;
    int assetType;
    float sendingAmount = 0;
    private float goldUnitPriceLow = 0;

    TextInputEditText textInputEditTextTotalAmount;
    TextInputEditText textInputEditTextEquevalentAmount;
    TextInputEditText focusedTextInputEditText;
    ViewLoadingAnimation viewLoadingAnimation;

    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        finish();
        return super.getOnBackInvokedDispatcher();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay_send2);

        viewLoadingAnimation = new ViewLoadingAnimation(PaySend_2.this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            v.setBackgroundColor(getColor(R.color.black));

            return insets;
        });


        assetType = AssetTypeEnums.BDT.getValue();
        userSessionManager = new UserSessionManager(PaySend_2.this);
        userLoginResponse = new GetReceiverInformationResponse();

        try {//check if intent from PaySend Activity
            userLoginResponse = (GetReceiverInformationResponse) getIntent().getSerializableExtra("obj");
            userIdType = getIntent().getStringExtra(UserSessionManager.KEY_ACCOUNT_TYPE);

            receiverName = userLoginResponse.getData().getName().toString();
            receiverPayId = userLoginResponse.getData().getPay_id().toString();

        } catch (Exception e) {
            getOnBackPressedDispatcher().onBackPressed();
        }

        try {//check if intent from QrCode Activity //syntax: userId/currencyType/amount/note

            String[] info = getIntent().getStringExtra("info").split("/");
            Log.d(TAG, "onCreate: length: " + info.length);
            Log.d(TAG, "onCreate: userId :" + info[0]);
            assetType = Integer.parseInt(info[1].trim());
            Log.d(TAG, "onCreate: currencyType :" + assetType);
            sendingAmount = Float.parseFloat(info[2].trim());
            Log.d(TAG, "onCreate: amount :" + sendingAmount);
            note = info[3].trim();
            Log.d(TAG, "onCreate: note :" + note);

            ((TextView) findViewById(R.id.currencyType)).setText(new ReturnEnumInString().getEnumToString(assetType));
            ((TextView) findViewById(R.id.editText)).setText(String.valueOf(sendingAmount));
            switch (assetType) {
                case 100:
                    ((ImageView) findViewById(R.id.currencyIcon)).setImageDrawable(getDrawable(R.drawable.bdt_icon));

                    break;
                case 101:
                    ((ImageView) findViewById(R.id.currencyIcon)).setImageDrawable(getDrawable(R.drawable.logo_halal_cash));
                    break;
            }
            ((TextView) findViewById(R.id.takeNote)).setText(note);

            onContinue();


        } catch (Exception e) {
            Log.e(TAG, "onCreate: Error: no Qr code data found", e);
        }

        try {
            goldUnitPriceLow = Float.parseFloat(new UserCurrentGoldPrice(PaySend_2.this).getGoldUnitPriceLow());
            Log.d(TAG, "onCreate: goldUnitPriceLow: " + goldUnitPriceLow);
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }


        textInputEditTextTotalAmount = (TextInputEditText) findViewById(R.id.editText);
        textInputEditTextEquevalentAmount = (TextInputEditText) findViewById(R.id.editTxtEquevalentAmount);


        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // EditText has gained focus
                    // Perform actions for when the EditText gains focus
                    Log.d(TAG, "onCreate:Focused() " + v.getId());
                    ((EditText) v).setText("");
                    inputChangeListiner((TextInputEditText) v);

                } else {
                    // EditText has lost focus
                    // Perform actions for when the EditText loses focus
                }
            }
        };

        // Set the focus change listener for each EditText
        textInputEditTextTotalAmount.setOnFocusChangeListener(onFocusChangeListener);
        textInputEditTextEquevalentAmount.setOnFocusChangeListener(onFocusChangeListener);

//        // Now you have the focused EditText in the focusedEditText variable
//        if (focusedTextInputEditText != null) {
//            // Do something with the focused EditText
//            inputChangeListiner(focusedTextInputEditText);
//        } else {
//            // None of the EditText views are focused
//        }


//            textInputEditTextEquevalentAmount.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d(TAG, "beforeTextChanged: textInputEditTextEquevalentAmount");
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d(TAG, "onTextChanged: textInputEditTextEquevalentAmount");
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                Log.d(TAG, "afterTextChanged: textInputEditTextEquevalentAmount");
////                if (assetType == GOLD.getValue()) {
////                    float equevalentAmount = 0.0F;
////                    try {
////                        Log.d(TAG, "afterTextChanged: sendingAmount: " + equevalentAmount);
////                        equevalentAmount = Float.parseFloat(s.toString());
////
////                    } catch (Exception e) {
////                        Log.e(TAG, "afterTextChanged: ", e);
////                    }
////
////                    Log.d(TAG, "afterTextChanged: sendingAmount: " + equevalentAmount);
////                    textInputEditTextTotalAmount.setFocusable(false);
////                   textInputEditTextTotalAmount.setText(String.format("= %s Taka  ", (equevalentAmount / goldUnitPriceLow)));
////                }
//
//            }
//
//        });
//        if (assetType == GOLD.getValue()) {
//            textInputEditTextEquevalentAmount.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    textInputEditTextEquevalentAmount.setFocusable(true);
//                }
//            });
//        } else {
//            textInputEditTextEquevalentAmount.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    textInputEditTextEquevalentAmount.setFocusable(false);
//                }
//            });
//        }


        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        findViewById(R.id.linearLayout16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectCurrencyDialog(assetType);
            }
        });

        ((TextView) findViewById(R.id.takeNote)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeNoteDialog();
                if (note != null)
                    ((TextView) findViewById(R.id.takeNote)).setText(note);
            }
        });

        findViewById(R.id.textViewContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinue();
            }
        });
    }

    private void inputChangeListiner(TextInputEditText textInputEditText) {

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (assetType == BDT.getValue()) {
                    if (textInputEditText.getId() == R.id.editText) {
                        try {
                            sendingAmount = Float.parseFloat(s.toString());
                            Log.d(TAG, "afterTextChanged: sendingAmount: " + sendingAmount);
                            textInputEditTextEquevalentAmount.setText(String.format("= %s Taka  ", sendingAmount));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }
                    } else if (textInputEditText.getId() == R.id.editTxtEquevalentAmount) {
                        try {
                            float sendingAmountInBDT = Float.parseFloat(s.toString());
                            sendingAmount = sendingAmountInBDT;
                            Log.d(TAG, "afterTextChanged: sendingAmountTemp: " + sendingAmountInBDT);
                            textInputEditTextTotalAmount.setText(String.format("%.2f", sendingAmount));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }

                    }

                } else if (assetType == GOLD.getValue()) {
                    if (textInputEditText.getId() == R.id.editText) {
                        try {
                            sendingAmount = Float.parseFloat(s.toString());
                            Log.d(TAG, "afterTextChanged: sendingAmount: " + sendingAmount);
                            textInputEditTextEquevalentAmount.setHint(String.format("= %s Taka  ", sendingAmount * goldUnitPriceLow));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }
                    } else if (textInputEditText.getId() == R.id.editTxtEquevalentAmount) {
                        try {
                            float sendingAmountInBDT = Float.parseFloat(s.toString());
                            sendingAmount = (sendingAmountInBDT / goldUnitPriceLow);
                            Log.d(TAG, "afterTextChanged: sendingAmountTemp: " + sendingAmountInBDT);
                            textInputEditTextTotalAmount.setText(String.format("%.2f", sendingAmount));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }
                    }
                } else {
                    Log.d(TAG, "afterTextChanged: requre logic...");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (assetType == BDT.getValue()) {
                    if (textInputEditText.getId() == R.id.editText) {
                        try {
                            sendingAmount = Float.parseFloat(s.toString());
                            Log.d(TAG, "afterTextChanged: sendingAmount: " + sendingAmount);
                            textInputEditTextEquevalentAmount.setText(String.format("= %s Taka  ", sendingAmount));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }
                    } else if (textInputEditText.getId() == R.id.editTxtEquevalentAmount) {
                        try {
                            float sendingAmountInBDT = Float.parseFloat(s.toString());
                            sendingAmount = sendingAmountInBDT;
                            Log.d(TAG, "afterTextChanged: sendingAmountTemp: " + sendingAmountInBDT);
                            textInputEditTextTotalAmount.setText(String.format("%.2f", sendingAmount));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }

                    }

                } else if (assetType == GOLD.getValue()) {
                    if (textInputEditText.getId() == R.id.editText) {
                        try {
                            sendingAmount = Float.parseFloat(s.toString());
                            Log.d(TAG, "afterTextChanged: sendingAmount: " + sendingAmount);
                            textInputEditTextEquevalentAmount.setHint(String.format("= %s Taka  ", sendingAmount * goldUnitPriceLow));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }
                    } else if (textInputEditText.getId() == R.id.editTxtEquevalentAmount) {
                        try {
                            float sendingAmountInBDT = Float.parseFloat(s.toString());
                            sendingAmount = (sendingAmountInBDT / goldUnitPriceLow);
                            Log.d(TAG, "afterTextChanged: sendingAmountTemp: " + sendingAmountInBDT);
                            textInputEditTextTotalAmount.setText(String.format("%.2f", sendingAmount));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }
                    }
                } else {
                    Log.d(TAG, "afterTextChanged: requre logic...");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (assetType == BDT.getValue()) {
                    if (textInputEditText.getId() == R.id.editText) {
                        try {
                            sendingAmount = Float.parseFloat(s.toString());
                            Log.d(TAG, "afterTextChanged: sendingAmount: " + sendingAmount);
                            textInputEditTextEquevalentAmount.setText(String.format("= %s Taka  ", sendingAmount));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }
                    } else if (textInputEditText.getId() == R.id.editTxtEquevalentAmount) {
                        try {
                            float sendingAmountInBDT = Float.parseFloat(s.toString());
                            sendingAmount = sendingAmountInBDT;
                            Log.d(TAG, "afterTextChanged: sendingAmountTemp: " + sendingAmountInBDT);
                            textInputEditTextTotalAmount.setText(String.format("%.2f", sendingAmount));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }

                    }

                } else if (assetType == GOLD.getValue()) {
                    if (textInputEditText.getId() == R.id.editText) {
                        try {
                            sendingAmount = Float.parseFloat(s.toString());
                            Log.d(TAG, "afterTextChanged: sendingAmount: " + sendingAmount);
                            textInputEditTextEquevalentAmount.setHint(String.format("= %s Taka  ", sendingAmount * goldUnitPriceLow));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }
                    } else if (textInputEditText.getId() == R.id.editTxtEquevalentAmount) {
                        try {
                            float sendingAmountInBDT = Float.parseFloat(s.toString());
                            sendingAmount = (sendingAmountInBDT / goldUnitPriceLow);
                            Log.d(TAG, "afterTextChanged: sendingAmountTemp: " + sendingAmountInBDT);
                            textInputEditTextTotalAmount.setText(String.format("%.2f", sendingAmount));

                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: ", e);
                        }
                    }
                } else {
                    Log.d(TAG, "afterTextChanged: requre logic...");
                }


            }
        });
    }

    public void onContinue() {
        String s = ((EditText) findViewById(R.id.editText)).getText().toString();
        try {
            sendingAmount = Float.parseFloat(s);
            if (sendingAmount > 0.0 && assetType == BDT.getValue()) {
                //todo
                getSendingBalanceStatus(assetType, sendingAmount);

            } else if (sendingAmount > 0.0 && assetType == GOLD.getValue()) {
                //todo
                getSendingBalanceStatus(assetType, sendingAmount);

            } else {
                ((EditText) findViewById(R.id.editText)).setError("Insert valid Amount.");
            }

        } catch (Exception e) {
            ((EditText) findViewById(R.id.editText)).setError("Error Amount.");
        }


    }

    public void selectCurrencyDialog(int assetTypeEnums) {
        BottomSheetDialog dialog = new BottomSheetDialog(PaySend_2.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.currency_selection_options);


        switch (assetTypeEnums) {
            case 100:
                dialog.findViewById(R.id.linearLayout18).setBackgroundTintMode(PorterDuff.Mode.ADD);

                break;
            case 101:
                dialog.findViewById(R.id.linearLayout19).setBackgroundTintMode(PorterDuff.Mode.ADD);
                break;
        }


        dialog.findViewById(R.id.linearLayout18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.findViewById(R.id.linearLayout18).setBackgroundTintMode(PorterDuff.Mode.ADD);
                assetType = BDT.getValue();
                ((TextView) findViewById(R.id.currencyType)).setText("BDT");
                ((ImageView) findViewById(R.id.currencyIcon)).setImageDrawable(getDrawable(R.drawable.bdt_icon));
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.linearLayout19).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.findViewById(R.id.linearLayout19).setBackgroundTintMode(PorterDuff.Mode.ADD);
                assetType = GOLD.getValue();
                ((TextView) findViewById(R.id.currencyType)).setText("GOLD");
                ((ImageView) findViewById(R.id.currencyIcon)).setImageDrawable(getDrawable(R.drawable.logo_halal_cash));
                dialog.dismiss();
            }
        });


        dialog.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.findViewById(R.id.btnBack1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        dialog.create();
        dialog.show();

    }

    public void takeNoteDialog() {
//        String note=null;

        BottomSheetDialog dialog = new BottomSheetDialog(PaySend_2.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_take_note);
        if (note != null)
            ((EditText) dialog.findViewById(R.id.editTxtNote)).setText(note);

        ((EditText) dialog.findViewById(R.id.editTxtNote)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                note = s.toString();

                ((TextView) dialog.findViewById(R.id.noteLimit)).setText((note.length()) + "/100");

            }

            @Override
            public void afterTextChanged(Editable s) {
//                ((TextView) dialog.findViewById(R.id.noteLimite)).setText(count + "/100");
            }

        });


        dialog.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                note=null;
//                ((TextView) dialog.findViewById(R.id.takeNote)).setText(note);
                dialog.dismiss();
//                note=null;

            }
        });
        dialog.findViewById(R.id.textViewAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (note != null && !note.isEmpty())
                    ((TextView) findViewById(R.id.takeNote)).setText(note);
                dialog.dismiss();
            }
        });

        dialog.create();
        dialog.show();


    }

    private void getSendingBalanceStatus(int asset_type, float amount) {

        viewLoadingAnimation.showLoading(true);

        Log.d(TAG, "getValidAccountBalanceInfo called successful....... ");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.CheckSendingBalance validReceiverInformation = retrofit.create(RetrofitAPI.CheckSendingBalance.class);
        Call<UserLoginResponse> asynCall = validReceiverInformation.createGetCheckSendingBalance("Bearer " + userSessionManager.getToken(), String.valueOf(asset_type), String.valueOf(amount));
        Log.d(TAG, "getSendingBalanceStatus: AssetType" + String.valueOf(asset_type) + " Amount: " + String.valueOf(amount));
        // on below line we are executing our method.
        asynCall.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserLoginResponse> call, @NonNull Response<UserLoginResponse> response) {
                Log.d(TAG, "onResponse: ");
                if (response.isSuccessful() && response.code() == 200) {
                    Log.d(TAG, "onResponse: response Successful..");

//                        TODO valid Amount
                    viewLoadingAnimation.showLoading(false);
                    //takeNote
                    viewSendingDetails(String.valueOf(amount), asset_type, "0.001 gm GOLD", receiverPayId, receiverName, "Funding Wallet");

                    //                        Toast.makeText(PaySend_2.this, userLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Log.d(TAG, "onResponse: response Error. isSuccessful: " + response.isSuccessful());
                    viewLoadingAnimation.showLoading(false);
                    ((EditText) findViewById(R.id.editText)).setError("Invalid Amount. ");
                }



            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                viewLoadingAnimation.showLoading(false);
                Toast.makeText(PaySend_2.this, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
                ((EditText) findViewById(R.id.editText)).setError("Network Error.");

            }
        });
    }

    public void viewSendingDetails(String amount, int currency, String equivalentPriceInGold, String receiverPayId, String receiverName, String wallerType) {

        BottomSheetDialog dialog = new BottomSheetDialog(PaySend_2.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_detail_view_before_pay_send);
//        TODO AsserTypeEnum must return Currency in String

        if (currency == BDT.getValue()) {
            ((TextView) dialog.findViewById(R.id.data_1)).setText(amount + " " + new ReturnEnumInString().getEnumToString(currency));
            ((TextView) dialog.findViewById(R.id.data_2)).setText(String.format("%.2f", Float.parseFloat(amount) / goldUnitPriceLow) + " gm in GOLD");

        } else if (currency == GOLD.getValue()) {
            ((TextView) dialog.findViewById(R.id.data_1)).setText(amount + "gm " + new ReturnEnumInString().getEnumToString(currency));
            ((TextView) dialog.findViewById(R.id.data_2)).setText(String.format("%s in BDT", Float.parseFloat(amount) * goldUnitPriceLow));
        }
        ((TextView) dialog.findViewById(R.id.data_3)).setText(receiverPayId);

        ((TextView) dialog.findViewById(R.id.data_4)).setText(receiverName);

        ((TextView) dialog.findViewById(R.id.data_5)).setText(new ReturnEnumInString().getEnumToString(currency));
        ((TextView) dialog.findViewById(R.id.data_6)).setText(wallerType);


        dialog.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.payId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CopyTextToClipBoard(PaySend_2.this, "Pay ID", ((TextView) dialog.findViewById(R.id.data_3)).getText().toString().trim());
            }
        });
        dialog.findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.textViewContinue).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewLoadingAnimation = new ViewLoadingAnimation(PaySend_2.this);
//                CheckPayPinStatus checkPayPinStatus = new CheckPayPinStatus(PaySend_2.this, TAG);
//                checkPayPinStatus.call(new CheckPayPinStatus.OnCheckPayPinStatusListener() {
//                    @Override
//                    public void onSuccess(boolean isSuccess) {
//                        if (isSuccess) {
//                            // Handle success
//                            startActivity(new Intent(PaySend_2.this,PayPin0.class));
//
//                        } else {
//                            // Handle failure
//                          startActivity(new Intent(PaySend_2.this,ResetPayPin0.class));
//                        }
//                    }
//                });

//                confirmAds(adsType.toLowerCase(), Integer.parseInt(adId), payable_amount);

                checkPayPinStatus();

//                startActivity(new Intent(PaySend_2.this, PayPin0.class));
                dialog.dismiss();
            }
        });

        dialog.create();
        dialog.show();
    }

    private void checkPayPinStatus() {

        viewLoadingAnimation.showLoading(true);

        Log.d(TAG, "getValidAccountBalanceInfo called successful....... ");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.CheckPayPinStatus validReceiverInformation = retrofit.create(RetrofitAPI.CheckPayPinStatus.class);
        Call<SimpleResponse> asynCall = validReceiverInformation.createGetCheckPayPinStatus("Bearer " + new UserSessionManager(PaySend_2.this).getToken());

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(@NonNull Call<SimpleResponse> call, @NonNull Response<SimpleResponse> response) {
                Log.d(TAG, "onResponse: ");
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: response Successful..");

                    Toast.makeText(PaySend_2.this, "Insert your 5 digit Pay Pin.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PaySend_2.this, PayPin0.class);
                    intent.putExtra("receiver_pay_id", receiverPayId);
                    intent.putExtra("transaction_type", TransactionTypeEnums.SEND.getValue());
                    intent.putExtra("asset_type", assetType);
                    intent.putExtra("amount", sendingAmount);
                    intent.putExtra("note", note);
                    viewLoadingAnimation.showLoading(false);
                    startActivity(intent);
                } else {
//                    Log.d(TAG, "onResponse: response Error..: " + response.body().getMessage());
                    // TODO: 3/11/2024 make user set his pay pin for the first time
                    viewLoadingAnimation.showLoading(false);
                    Toast.makeText(PaySend_2.this, "Update your Pay Pin First.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(PaySend_2.this, ResetPayPin0.class));


                }



            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                viewLoadingAnimation.showLoading(false);
                Toast.makeText(PaySend_2.this, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
//                ((EditText) findViewById(R.id.editText)).setError("Network Error.");

            }
        });
    }


}