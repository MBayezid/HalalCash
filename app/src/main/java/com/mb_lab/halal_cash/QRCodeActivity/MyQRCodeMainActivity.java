package com.mb_lab.halal_cash.QRCodeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mb_lab.halal_cash.Enums.ReturnEnumInString;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.mbviewlib.QRCodeUtility.encode.QRCodeEncoder;

public class MyQRCodeMainActivity extends AppCompatActivity {
    String info = null;
    ImageView ivQRCode;
    final String TAG = "MyQRCodeMainActivity";
    private QRCodeEncoder qrCodeEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_qrcode_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setBackground(getDrawable(R.color.black));
            v.setPadding(systemBars.left + 20, systemBars.top + 10, systemBars.right + 20, systemBars.bottom + 10);
            return insets;
        });
        qrCodeEncoder = new QRCodeEncoder(this);
        ivQRCode = (ImageView) findViewById(R.id.iv_qrcode_bg);

        info = new UserSessionManager(MyQRCodeMainActivity.this).getUserPayId();
        try {
            String data = getIntent().getStringExtra("info");
            if (data != null && !data.isEmpty()) {
                Log.d(TAG, "onCreate: Not null: " + data);
                info = info + "/" + data;
                qrCodeEncoder.createQrCode2ImageView(info, ivQRCode, R.drawable.gold);
                String[] splitData=info.trim().split("/");
                try {

                ((TextView)findViewById(R.id.textView55)).setText(splitData[2]+" "+new ReturnEnumInString().getEnumToString(Integer.parseInt(splitData[1])));
                    ((TextView)findViewById(R.id.textView55)).setTextSize(25);

                }catch (Exception e){
                    Log.e(TAG, "onCreate: ", e);
                }
            } else {
                Log.d(TAG, "onCreate: null");
//                ivQRCode.setImageDrawable(null);
                qrCodeEncoder.createQrCode2ImageView(info, ivQRCode, R.drawable.gold);
            }
        } catch (Exception e) {

        }


        findViewById(R.id.textView55).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyQRCodeMainActivity.this, AddInfoToQRCode.class));
                finish();
            }
        });
    }
}