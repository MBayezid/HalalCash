package com.mb_lab.halal_cash.P2P.BuySellActions;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.Enums.TransactionTypeEnums;
import com.mb_lab.halal_cash.PayPin.PayPin0;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.pay.PaySend.PaySend_Successful;

public class ConfirmPayPin extends AppCompatActivity {
    private EditText editText1, editText2, editText3, editText4, editText5;
    private EditText[] editTexts;
    private final static String TAG = "ConfirmPayPin";
    private  PinTextWatcher pinTextWatcher;
    ViewLoadingAnimation viewLoadingAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay_pin_0);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            v.setBackgroundColor(getColor(R.color.black));
            return insets;
        });
        viewLoadingAnimation = new ViewLoadingAnimation(ConfirmPayPin.this);

        editText1 = (EditText) findViewById(R.id.editPin1);
        editText2 = (EditText) findViewById(R.id.editPin2);
        editText3 = (EditText) findViewById(R.id.editPin3);
        editText4 = (EditText) findViewById(R.id.editPin4);
        editText5 = (EditText) findViewById(R.id.editPin5);

        editTexts = new EditText[]{editText1, editText2, editText3, editText4, editText5};
//
//        pinTextWatcher = new PinTextWatcher(editTexts);

        editText1.addTextChangedListener(new ConfirmPayPin.PinTextWatcher(0));
        editText2.addTextChangedListener(new ConfirmPayPin.PinTextWatcher(1));
        editText3.addTextChangedListener(new ConfirmPayPin.PinTextWatcher(2));
        editText4.addTextChangedListener(new ConfirmPayPin.PinTextWatcher(3));
        editText5.addTextChangedListener(new ConfirmPayPin.PinTextWatcher(4));
//
        editText1.setOnKeyListener(new ConfirmPayPin.PinOnKeyListener(0));
        editText2.setOnKeyListener(new ConfirmPayPin.PinOnKeyListener(1));
        editText3.setOnKeyListener(new ConfirmPayPin.PinOnKeyListener(2));
        editText4.setOnKeyListener(new ConfirmPayPin.PinOnKeyListener(3));
        editText5.setOnKeyListener(new ConfirmPayPin.PinOnKeyListener(4));

        //        checkPayPinStatus();
        findViewById(R.id.textViewContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String PayPin0 = editText1.getText().toString()
                            + "" + editText2.getText().toString()
                            + "" + editText3.getText().toString()
                            + "" + editText4.getText().toString()
                            + "" + editText5.getText().toString();
                    if (PayPin0.length() == 5) {
                        onPinConfirmation(PayPin0);

                    } else
                        Toast.makeText(ConfirmPayPin.this, "Insert Valid Pay Pin!!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(ConfirmPayPin.this, "Insert Valid Pay Pin!!", Toast.LENGTH_LONG).show();
                }

            }
        });

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

    }
    public void onPinConfirmation(String PayPin) {
        try {
            Bundle bundle = getIntent().getExtras();
            String adsType = bundle.getString("adsType");
            if (adsType!=null) {
                Log.d(TAG, "onPinConfirmation: PayPin passed..");
                Intent intent = new Intent(ConfirmPayPin.this, Buy_Sell_Successfull_Activity.class);

                bundle.putString("pay_pin", PayPin);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();


            } else {
                Toast.makeText(ConfirmPayPin.this, "Try again later.", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onPinConfirmation: NO transaction_type matched.. ");
                finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "onPinConfirmation: ", e);
            Toast.makeText(ConfirmPayPin.this, "Try again later.", Toast.LENGTH_LONG).show();
            finish();
        }
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