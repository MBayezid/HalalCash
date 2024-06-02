package com.mb_lab.halal_cash.P2P.CreateAds;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserCurrentGoldPrice;
import com.mb_lab.halal_cash.SessionManagers.UserWalletManager;
import com.mb_lab.halal_cash.Util.ConverterUtil;


public class CreateNormalAdsFragmentTwo extends Fragment {
    private SharedViewModel viewModel;
    private final String TAG = "CreateNormalAdsFragmentTwo";
    private float balanceBdt;
    private float balanceGold;
    private EditText editTextTotalAmount;
    private float userUnitPrice = 0.0F;
    private float totalAmount = 0.0F;
    private float OrderLimitMin = 0.0F;
    private float OrderLimitMax = 0.0F;
    boolean error = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.create_normal_add_fragment_2, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        editTextTotalAmount = (EditText) view.findViewById(R.id.editText3);

        try {
            userUnitPrice = Float.parseFloat(viewModel.getSharedData().getValue().get("user_price"));
            balanceBdt = Float.parseFloat(new UserWalletManager(requireActivity()).getBdt());
            balanceGold = Float.parseFloat(new UserWalletManager(requireActivity()).getGold());
            ((TextView) view.findViewById(R.id.textView_available_balance)).setText("Available BDT " + balanceBdt);
        } catch (Exception e) {
            ((TextView) view.findViewById(R.id.textView_available_balance)).setText("Available BDT 00.00");
            Log.e(TAG, "onCreateView: ", e);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        editTextTotalAmount = (EditText) view.findViewById(R.id.editText3);

        editTextTotalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    totalAmount = Float.parseFloat(s.toString().trim());
                    OrderLimitMax = totalAmount * userUnitPrice;
                    if (totalAmount > 0) {
                        ((TextView) view.findViewById(R.id.textView_orderLimit_max)).setHint(new ConverterUtil(TAG).convertNumberToString(OrderLimitMax));
                    } else if (totalAmount <= 0) {
                        ((TextView) view.findViewById(R.id.textView_orderLimit_max)).setHint(new ConverterUtil(TAG).convertNumberToString(OrderLimitMax));
                        editTextTotalAmount.setError(totalAmount + " is invalid.\ninsert proper amount.");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "beforeTextChanged: ", e);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    totalAmount = Float.parseFloat(s.toString().trim());
                    OrderLimitMax = totalAmount * userUnitPrice;
                    if (totalAmount > 0) {
                        ((TextView) view.findViewById(R.id.textView_orderLimit_max)).setHint(new ConverterUtil(TAG).convertNumberToString(OrderLimitMax));
                    } else if (totalAmount <= 0) {
                        ((TextView) view.findViewById(R.id.textView_orderLimit_max)).setHint(new ConverterUtil(TAG).convertNumberToString(OrderLimitMax));
                        editTextTotalAmount.setError(totalAmount + " is invalid.\ninsert proper amount.");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "beforeTextChanged: ", e);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    totalAmount = Float.parseFloat(s.toString().trim());
                    OrderLimitMax = totalAmount * userUnitPrice;
                    if (totalAmount > 0) {
//                        editTextTotalAmount.setText(totalAmount+" gm");
                        ((TextView) view.findViewById(R.id.textView_orderLimit_max)).setHint(new ConverterUtil(TAG).convertNumberToString(OrderLimitMax) + " BDT");
                    } else if (totalAmount <= 0) {
//                        editTextTotalAmount.setText(totalAmount+" gm");
                        ((TextView) view.findViewById(R.id.textView_orderLimit_max)).setHint(new ConverterUtil(TAG).convertNumberToString(OrderLimitMax) + " BDT");
                        editTextTotalAmount.setError(totalAmount + " is invalid.\ninsert proper amount.");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "beforeTextChanged: ", e);
                }
            }
        });


        view.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
//                    totalAmount = Float.parseFloat(editTextTotalAmount.getText().toString().trim());
                    if (totalAmount > 0.0) {
                        error = true;
                        ((EditText) view.findViewById(R.id.editText3)).setText(String.format("%.2f", totalAmount));
                        viewModel.setSharedData("advertise_total_amount", String.format("%.2f", totalAmount));
                        Log.d(TAG, "onClick: updated : advertise_total_amount");
                    } else {
                        error = false;
                        ((EditText) view.findViewById(R.id.editText3)).setError("Insert more then zero!");
                        ((EditText) view.findViewById(R.id.editText3)).setText(null);

                    }

                    OrderLimitMin = Float.parseFloat(new ConverterUtil(TAG).convertStringToString_2f(((EditText) view.findViewById(R.id.textView_orderLimit_min)).getText().toString().trim()));
                    if (OrderLimitMin > 0) {
                        error = true;
                        ((EditText) view.findViewById(R.id.textView_orderLimit_min)).setText(String.valueOf(OrderLimitMin));
                        viewModel.setSharedData("order_limit_min", String.valueOf(OrderLimitMin));
                        Log.d(TAG, "onClick: updated : order_limit_min");
                    } else {
                        error = false;
                        ((EditText) view.findViewById(R.id.textView_orderLimit_min)).setError("Insert more then zero!");
                        ((EditText) view.findViewById(R.id.textView_orderLimit_min)).setText(null);

                    }

                    if (((EditText) view.findViewById(R.id.textView_orderLimit_max)).getText().toString().trim().isEmpty()) {
                        OrderLimitMax = Float.parseFloat(new ConverterUtil(TAG).convertStringToString_2f(((EditText) view.findViewById(R.id.textView_orderLimit_max)).getHint().toString().trim().split(" ")[0]));
                    } else {
                        OrderLimitMax = Float.parseFloat(new ConverterUtil(TAG).convertStringToString_2f(((EditText) view.findViewById(R.id.textView_orderLimit_max)).getText().toString().trim()));
                    }
                    if (OrderLimitMax >= OrderLimitMin && OrderLimitMax >= userUnitPrice) {
                        error = true;
                        ((EditText) view.findViewById(R.id.textView_orderLimit_max)).setText(String.valueOf(OrderLimitMax));
                        viewModel.setSharedData("order_limit_max", String.valueOf(OrderLimitMax));
                        Log.d(TAG, "onClick: updated : order_limit_max");
                    } else {
                        error = false;
                        ((EditText) view.findViewById(R.id.textView_orderLimit_max)).setError("Insert more then minimum limit!");
                        ((EditText) view.findViewById(R.id.textView_orderLimit_max)).setText(null);

                    }
                } catch (Exception e) {
                    error = false;
                    Log.e(TAG, "onClick: ", e);
                }

                if (error) {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                    R.anim.slide_out_right, R.anim.slide_in_left)
                            .addToBackStack(null)
                            .replace(R.id.fragment_container, new CreateNormalAdsFragmentThree())
                            .commit();
                    Log.d(TAG, "onClick: Successfully committed..");
                } else Log.d(TAG, "onClick: Error committed..");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");


    }
}
