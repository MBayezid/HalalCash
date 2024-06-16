package com.mb_lab.halal_cash.home.homeProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetUserInformation;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.CustomeDialogs.ViewLoadingAnimation;
import com.mb_lab.halal_cash.DataModels.SimpleResponse;
import com.mb_lab.halal_cash.NID_Verification.VerificationActivityMain;
import com.mb_lab.halal_cash.R;
import com.mb_lab.halal_cash.RetrofitAPI;
import com.mb_lab.halal_cash.SessionManagers.UserSessionManager;
import com.mb_lab.halal_cash.SplashActivity;
import com.mb_lab.halal_cash.ApiCalls.CommonApiRequest.GetBitmapFromUrl;
import com.mb_lab.halal_cash.Util.MailSupport;
import com.mb_lab.halal_cash.Util.ResetUserInfo;
import com.mb_lab.halal_cash.home.homeProfile.security.HomeProfile_SecurityActivity;
import com.mb_lab.halal_cash.pay.PaySend.PaySend_2;
import com.mb_lab.halal_cash.pay.payGift.PayGiftActivity;
import com.mb_lab.halal_cash.policy.PrivacyPolicy;
import com.mb_lab.mbviewlib.ImageViewManagment.RoundImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeProfile extends AppCompatActivity {
    private HashMap<String, String> userHashMap;
    private final String TAG = "HomeProfile";
    UserSessionManager userSessionManager;
    Retrofit retrofit = null;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_CODE = 101;
    ViewLoadingAnimation viewLoadingAnimation;

    Uri selectedImageUri = null;
    Dialog dialog;
    Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_profile);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.gray));


        userSessionManager = new UserSessionManager(HomeProfile.this);
        userHashMap = userSessionManager.getAllUserInfo();

        viewLoadingAnimation = new ViewLoadingAnimation(HomeProfile.this);


        ((TextView) findViewById(R.id.textView13)).setText(new UserSessionManager(HomeProfile.this).getAllUserInfo().get(userSessionManager.KEY_NAME));

        Log.d(TAG, "onCreate: pay_id " + userHashMap.get(userSessionManager.KEY_PAY_ID));

        ((TextView) findViewById(R.id.textView133)).setText("Pay ID: " + userHashMap.get(userSessionManager.KEY_PAY_ID));

        if (userHashMap.get(userSessionManager.KEY_NAME) != null)
            ((TextView) findViewById(R.id.textView13)).setText(userHashMap.get(userSessionManager.KEY_NAME));

//        if (userHashMap.get(userSessionManager.KEY_VERIFIED) == "true")
//            ((TextView) findViewById(R.id.textView14)).setText("");

        ((TextView) findViewById(R.id.textView14)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeProfile.this, "Upload NID / Passport with your selfie to get verified.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeProfile.this, VerificationActivityMain.class));
            }
        });

        ((TextView) findViewById(R.id.textView144)).setText(userHashMap.get(userSessionManager.KEY_ACCOUNT_TYPE));

        findViewById(R.id.linearLayout13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeProfile.this, PayGiftActivity.class));

            }
        });
        findViewById(R.id.linearLayoutPrivacyPolicy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeProfile.this, PrivacyPolicy.class));
            }
        });
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.linearLayout16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWorningAlartDialogForUser();

            }
        });
        findViewById(R.id.linearLayout11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeProfile.this, HomeProfile_SecurityActivity.class));
            }
        });
        findViewById(R.id.imageView9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    new MailSupport(HomeProfile.this);
                } catch (Exception e) {
                    Log.e(TAG, "onClick: ", e);
                }
            }
        });
        findViewById(R.id.linearLayout15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLoadingAnimation.showLoading(true);
                new ResetUserInfo(HomeProfile.this);
                startActivity(new Intent(HomeProfile.this, SplashActivity.class));
                viewLoadingAnimation.showLoading(false);
                finishAfterTransition();
            }
        });

        findViewById(R.id.editProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploaderDialog();
//                https://halalcash.net/storage/26/IMG-20240406-WA0002.jpg
            }
        });


        new GetBitmapFromUrl(HomeProfile.this, TAG).preLoadImageFromUrlAndLoad(userSessionManager.getImageUrl(), findViewById(R.id.imageView11));

        findViewById(R.id.imageView11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new GetBitmapFromUrl(HomeProfile.this, TAG).getImageFromURL(userSessionManager.getImageUrl(), findViewById(R.id.imageView11));
                    Log.d(TAG, "onClick:getImageUrl:  " + userSessionManager.getImageUrl());

                } catch (Exception e) {
                    Log.e(TAG, "onClick: failed to update Home profile..", e);
                }

            }
        });


    }

    private void createWorningAlartDialogForUser() {

        BottomSheetDialog dialog = new BottomSheetDialog( this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog_for_user_account_deletion);

        dialog.findViewById(R.id.btn_confirm_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(10000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        // Update UI (e.g., display remaining time)
                        // ...
                        ((TextView)dialog.findViewById(R.id.message)).setText("This will take effect after "+(millisUntilFinished/1000)+" secs." );
                    }

                    public void onFinish() {
                        // Timer finished, perform any necessary actions
                        // ...
//                    viewLoadingAnimation.showLoading(true);
//                    requestAccountDeletion();
                        ((TextView)dialog.findViewById(R.id.message)).setText("Your Account will be deleted shortly.");
                    }
                }.start();
            }
        });

            // Start a 10-second countdown timer


        dialog.create();
        dialog.show();

    }


    private void uploaderDialog() {
        dialog = new Dialog(HomeProfile.this);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.dialog_edit_home_profile_info);

//        new GetBitmapFromUrl(HomeProfile.this, TAG).preLoadImageFromUrlAndLoad(userSessionManager.getImageUrl(), dialog.findViewById(R.id.imageViewProfile));

        Glide.with(HomeProfile.this)
                .load(userSessionManager.getImageUrl()).centerCrop()
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into((ImageView) dialog.findViewById(R.id.imageViewProfile));

        dialog.findViewById(R.id.imageViewProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (ContextCompat.checkSelfPermission(HomeProfile.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            // Permission is not granted, request it
                            ActivityCompat.requestPermissions(HomeProfile.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                        } else {
                            // Permission is already granted, proceed with file access
                            openGallery();
                        }


                    }
                }).start();
            }
        });

        ((TextInputEditText) dialog.findViewById(R.id.editTextName)).setText(userHashMap.get(userSessionManager.KEY_NAME));

        dialog.findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    retrofit = CreateRetrofitClient();
                    String newName = ((TextInputEditText) dialog.findViewById(R.id.editTextName)).getText().toString().trim();
                    String oldName = userHashMap.get(userSessionManager.KEY_NAME).toString().trim();
                    if (selectedImageUri != null) {
                        try {
//                            File file = uriToFile(selectedImageUri);

//                            new GetUserInformation(HomeProfile.this, TAG);
                            updateImage(uriToFile(selectedImageUri));
//                            dialog.setCancelable(false);
//                            viewLoadingAnimation.showLoading(false);
                        } catch (Exception e) {
                            Log.e(TAG, "onClick: ", e);
                        }
                    } else if (!newName.isEmpty() && !newName.equals(oldName)) {
                        retrofit = CreateRetrofitClient();
                        updateName(newName);


                    }


                } catch (Exception e) {
                    Log.e(TAG, "onClick: ", e);
                }

            }
        });

        dialog.create();
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with file access
                openGallery();
            } else {
                // Permission denied, handle accordingly (e.g., show a message or request again)
            }
        }
    }

    private Retrofit CreateRetrofitClient() {
        if (retrofit == null) {
            // builder and passing our base url
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    // as we are sending data in json format so
                    // we have to add Gson converter factory
                    .addConverterFactory(GsonConverterFactory.create())
                    // at last we are building our retrofit builder.
                    .build();
            return retrofit;
        }
        return retrofit;

    }

    private void requestAccountDeletion() {

        Log.d(TAG, "account deletion: Started..");
        Retrofit retrofit = CreateRetrofitClient();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // passing data from our text fields to our modal class.


        // calling a method to create a post and passing our modal class.
        Call<SimpleResponse> call = retrofitAPI.createPostForDeleteAccount("Bearer " + new UserSessionManager(HomeProfile.this).getToken());

        // on below line we are executing our method.
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
//                Log.d(TAG, "onResponse: successful: ");
                Log.d(TAG, "onResponse: responseCode: " + response.code());

                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Account deletion successful: ");
                    new ResetUserInfo(HomeProfile.this);
                    viewLoadingAnimation.showLoading(false);
                    startActivity(new Intent(HomeProfile.this, SplashActivity.class));
                } else {
                    Log.d(TAG, "onResponse:   Account deletion Not successful: ");

                    // this method is called when we get response from our api.
                    Toast.makeText(HomeProfile.this, "Check your credentials and. \n Try again.", Toast.LENGTH_SHORT).show();
                    viewLoadingAnimation.showLoading(false);

                }


            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(HomeProfile.this, "Server connection failed.\nTry again later.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateImage(File file) {
        viewLoadingAnimation.showLoading(true);

        Log.d(TAG, "updateImage: Started..");


        Log.d(TAG, "updateImage: converting URI into file");


        // Create a request body with the file and its MIME type
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        // Create a MultipartBody.Part from the file request body
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.UpdateUserProfilePicture apiService = retrofit.create(RetrofitAPI.UpdateUserProfilePicture.class);

        // Call the uploadImage method with the Authorization header and the image part
        Call<ResponseBody> call = apiService.uploadImage("Bearer " + userSessionManager.getToken(), imagePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Handle response
                if (response.isSuccessful()) {
                    Toast.makeText(HomeProfile.this, "Profile picture updated. ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: updating Profile Image successful");


                    updateUi(true);

                } else {
                    Log.d(TAG, "onResponse: Profile picture NOT  updated");
                    updateUi(false);

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onResponse: Profile picture update failed...");
                // Handle failure
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(HomeProfile.this, "Server not connected.\ntry again later.", Toast.LENGTH_LONG).show();
                updateUi(false);
            }
        });
    }

    private void updateName(String name) {
        viewLoadingAnimation.showLoading(true);
        Log.d(TAG, "updateName: Started..");
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI.UpdateUserProfileName retrofitAPI = retrofit.create(RetrofitAPI.UpdateUserProfileName.class);

        // passing data from our text fields to our modal class.


        // calling a method to create a post and passing our modal class.
        Call<ResponseBody> call = retrofitAPI.createPostRequestForNameUpdate("Bearer " + new UserSessionManager(HomeProfile.this).getToken(), name);

        // on below line we are executing our method.
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: successful: ");
                Log.d(TAG, "onResponse: responseCode: " + response.code());

                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Name updated successful: ");

                    updateUi(true);

                } else {
                    Log.d(TAG, "onResponse:  Name updated Not successful: ");

                    // this method is called when we get response from our api.
                    Toast.makeText(HomeProfile.this, "Check your credentials and. \n Try again.", Toast.LENGTH_SHORT).show();
                    updateUi(false);

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(HomeProfile.this, "Server connection failed.\nTry again later.", Toast.LENGTH_SHORT).show();
                updateUi(false);
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            // Load the image as a Bitmap
//            bitmap = loadImageFromUri(selectedImageUri);

            if (selectedImageUri != null) {
                // Set the Bitmap to the ImageView
                ((RoundImageView) dialog.findViewById(R.id.imageViewProfile)).setImageURI(selectedImageUri);
                Toast.makeText(this, "Image Ready to upload. " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Image Not selected!!", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private Bitmap loadImageFromUri(Uri uri) {
        try {
            // Open an input stream from the URI
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                // Decode the input stream into a Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                // Close the input stream
                inputStream.close();
                return bitmap;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private File uriToFile(Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = HomeProfile.this.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(column_index);
            cursor.close();
        }
        if (filePath != null) {
            return new File(filePath);
        }
        return null;
    }

    private void updateUi(boolean state) {
        if (state) {
            new GetUserInformation(HomeProfile.this, TAG);
        }
        viewLoadingAnimation.showLoading(false);
        dialog.dismiss();


    }
}