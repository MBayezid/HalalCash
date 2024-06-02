package com.mb_lab.halal_cash.QRCodeActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.DataModels.GetReceiverInformationResponse;
import com.mb_lab.halal_cash.Enums.UserIdTypeEnums;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.pay.PaySend.PaySend_2;
import com.mb_lab.mbviewlib.QRCodeUtility.view.QRCodeScannerView;
import com.mb_lab.mbviewlib.QRCodeUtility.view.QRCoverView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScanQRCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private QRCodeScannerView mScannerView;
    private QRCoverView mCoverView;
//    private PointsOverlayView mCoverView;

    private static final String TAG = "ScanQRCodeActivity";

    private final int PERMISSION_REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        initView();

    }

    private void initView() {


        findViewById(R.id.myQrCode).setOnClickListener(this);
        findViewById(R.id.uploadQrCode).setOnClickListener(this);
        findViewById(R.id.backBtn).setOnClickListener(this);

        mScannerView = (QRCodeScannerView) findViewById(R.id.scanner_view);

        mCoverView = (QRCoverView) findViewById(R.id.cover_view);
//        mCoverView = (PointsOverlayView) findViewById(R.id.cover_view);

        mCoverView.setCoverViewScanner(256, 256);//提交修改UI
        mCoverView.setCoverViewOutsideColor(R.color.transparent_black);//提交修改UI
        //自动聚焦间隔2s
        mScannerView.setAutofocusInterval(2000L);
        //扫描结果监听处理
        mScannerView.setOnQRCodeReadListener(new QRCodeScannerView.OnQRCodeScannerListener() {

            @Override
            public void onDecodeFinish(String text, PointF[] points) {
                Log.d(TAG, "扫描结果 result -> " + text); //扫描到的内容
                //【可选】判断二维码是否在扫描框中
                judgeResult(text, points);
            }
        });
        //相机权限监听(如果你有相关的权限类，可以不实现该接口)
        mScannerView.setOnCheckCameraPermissionListener(new QRCodeScannerView.OnCheckCameraPermissionListener() {
            @Override
            public boolean onCheckCameraPermission() {
                if (ContextCompat.checkSelfPermission(ScanQRCodeActivity.this, android.Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    return true;
                } else {
                    ActivityCompat.requestPermissions(ScanQRCodeActivity.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    return false;
                }
            }
        });
        //开启后置摄像头
        mScannerView.setBackCamera();
    }

    private void judgeResult(String result, PointF[] points) {
        //接下来是处理二维码是否在扫描框中的逻辑
        RectF finderRect = mCoverView.getViewFinderRect();
        Log.d("tag", "points.length = " + points.length);
        boolean isContain = true;
        //依次判断扫描结果的每个point是否都在扫描框内
        for (int i = 0, length = points.length; i < length; i++) {
            if (!finderRect.contains(points[i].x, points[i].y)) {
                isContain = false;  //只要有一个不在，说明二维码不完全在扫描框中
                break;
            }
        }
        if (isContain) {
            Log.d(TAG, "judgeResult: Total Data: " + result);
            String payId = result.split("/")[0].trim();
            Log.d(TAG, "judgeResult: PayId: " + payId);
            getValidReceiverInformation(UserIdTypeEnums.PAYID.getValue(), payId, result);

//            Intent intent = new Intent(this, ResultActivity.class).putExtra("result", result);
//            startActivity(intent);
        } else {
            Log.d(TAG, "扫描失败！请将二维码图片摆放在正确的扫描区域中...");
        }
    }

    /**
     * 权限请求回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mScannerView.grantCameraPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.backBtn) {
            finish();
        } else if (id == R.id.uploadQrCode) {

        } else if (id == R.id.uploadQrCode) {

        }
    }


    private void getValidReceiverInformation(String user_id_type, String value, String result) {

//todo        new updateUiAnimation().setLoadingStart(PaySend.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
        mScannerView.stopCamera();
        Log.d(TAG, "getValidReceiverInformation: Info: " + user_id_type + " / " + value);
        Log.d(TAG, "getValidReceiverInformation called successful....... ");
        // below line is for displaying our progress bar.
//        loadingPB.setVisibility(View.VISIBLE);

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.ValidReceiverInformation validReceiverInformation = retrofit.create(RetrofitAPI.ValidReceiverInformation.class);
        Call<GetReceiverInformationResponse> asynCall = validReceiverInformation.createGetValidReceiverInformation("Bearer " + new UserSessionManager(ScanQRCodeActivity.this).getToken(), user_id_type, value);

        // on below line we are executing our method.
        asynCall.enqueue(new Callback<GetReceiverInformationResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetReceiverInformationResponse> call, @NonNull Response<GetReceiverInformationResponse> response) {
                Log.d(TAG, "onResponse: getting User information..");
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: response Successful..");
                    GetReceiverInformationResponse userLoginResponse = response.body();
                    Log.d(TAG, "onResponse: response Code ..:" + response.code());
                    if (userLoginResponse != null && response.isSuccessful()) {
                        try {
//                            Log.d(TAG, "onResponse: user Data=> " + userLoginResponse.getData().getId());
                            String receiverId = String.valueOf(userLoginResponse.getData().getId());
                            String receiverName = userLoginResponse.getData().getName();
                            String receiverPayId = userLoginResponse.getData().getPay_id();

                            Log.d(TAG, "onResponse: User Data => " + receiverId + " " + receiverName + " " + receiverPayId);
                            Intent intent = new Intent(ScanQRCodeActivity.this, PaySend_2.class);

                            intent.putExtra(new UserSessionManager(ScanQRCodeActivity.this).KEY_ACCOUNT_TYPE, user_id_type);
                            intent.putExtra("obj", userLoginResponse);
                            intent.putExtra("info", result);

//                   todo         new updateUiAnimation().setLoadingEnd(ScanQRCodeActivity.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));

                            startActivity(intent);
                            finish();


                        } catch (Exception e) {
                            Toast.makeText(ScanQRCodeActivity.this, "Invalid User Id", Toast.LENGTH_LONG).show();
                            mScannerView.startCamera();
                            Log.d(TAG, "onResponse: Caught Erropr: ." + e);

//                     todo       new updateUiAnimation().setLoadingEnd(ScanQRCodeActivity.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
//                            finish();
                        }
                    } else {
                        Toast.makeText(ScanQRCodeActivity.this, "Invalid User Id", Toast.LENGTH_LONG).show();
                        mScannerView.startCamera();
//                  todo      new updateUiAnimation().setLoadingEnd(ScanQRCodeActivity.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
                    }
                } else {
                    Log.d(TAG, "onResponse: response Error..: " + response.code());
//                    ((EditText) findViewById(R.id.textView2111)).setError("User Not Found.");
                    Toast.makeText(ScanQRCodeActivity.this, "User Not Found.", Toast.LENGTH_LONG).show();
                    mScannerView.startCamera();
//                 todo   new updateUiAnimation().setLoadingEnd(ScanQRCodeActivity.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));
                }



            }

            @Override
            public void onFailure(Call<GetReceiverInformationResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                asynCall.cancel();
                Toast.makeText(ScanQRCodeActivity.this, "Something went wrong!\ncheck internet connection.", Toast.LENGTH_SHORT).show();
                mScannerView.startCamera();
//               todo new updateUiAnimation().setLoadingEnd(ScanQRCodeActivity.this, findViewById(R.id.textViewContinue), findViewById(R.id.progressBar), findViewById(R.id.main));

            }
        });
    }
}
