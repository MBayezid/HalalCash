package com.mb_lab.halal_cash.QRCodeActivity;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mb_lab.halal_cash.Enums.AssetTypeEnums;
import com.mb_lab.halal_cash.R;

public class AddInfoToQRCode extends AppCompatActivity {
    int assetType;
    float sendingAmount = 0;
    String note = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_info_to_qrcode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.setCurrency).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCurrencyDialog(assetType);
            }
        });
        findViewById(R.id.textViewContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinue();
            }
        });

        assetType = AssetTypeEnums.BDT.getValue();
    }

    public void onContinue() {
        String s = ((EditText) findViewById(R.id.editText)).getText().toString();
        try {
            sendingAmount = Float.parseFloat(s);
            note = ((TextView) findViewById(R.id.takeNote)).getText().toString();
            Intent intent = new Intent(AddInfoToQRCode.this, MyQRCodeMainActivity.class);
            if ((int) sendingAmount > 0 && assetType == AssetTypeEnums.BDT.getValue()) {
                //todo
                intent.putExtra("info", String.valueOf(assetType + "/" + sendingAmount + "/" + note));
                startActivity(intent);
                finish();


            } else if (sendingAmount > 0.0 && assetType == AssetTypeEnums.GOLD.getValue()) {
                //todo
                intent.putExtra("info", assetType + "/" + sendingAmount + "/" + note);
                startActivity(intent);
                finish();
            } else {
                ((EditText) findViewById(R.id.editText)).setError("Insert valid Amount.");
            }
        } catch (Exception e) {
            ((EditText) findViewById(R.id.editText)).setError("Error Amount.");
        }


    }

    public void selectCurrencyDialog(int assetTypeEnums) {
        BottomSheetDialog dialog = new BottomSheetDialog(AddInfoToQRCode.this);
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
                assetType = AssetTypeEnums.BDT.getValue();
                ((TextView) findViewById(R.id.currencyType)).setText("BDT");
                ((TextView) findViewById(R.id.currencyTextSmall)).setText("BDT");
                ((ImageView) findViewById(R.id.currencyIcon)).setImageDrawable(getDrawable(R.drawable.bdt_icon));
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.linearLayout19).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.findViewById(R.id.linearLayout19).setBackgroundTintMode(PorterDuff.Mode.ADD);
                assetType = AssetTypeEnums.GOLD.getValue();
                ((TextView) findViewById(R.id.currencyType)).setText("GOLD");
                ((TextView) findViewById(R.id.currencyTextSmall)).setText("GOLD");
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
}