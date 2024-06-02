package com.mb_lab.halal_cash;


import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.mb_lab.halal_cash.Constants;
import com.mb_lab.halal_cash.DataModels.GetDepositAgentListResponse;
import com.mb_lab.halal_cash.DataModels.GetGoldPriceResponse;
import com.mb_lab.halal_cash.DataModels.GetOwnCreatedAdsResponse;
import com.mb_lab.halal_cash.DataModels.GetReceiverInformationResponse;
import com.mb_lab.halal_cash.DataModels.HomeNotificationResponse;
import com.mb_lab.halal_cash.DataModels.SimpleResponse;
import com.mb_lab.halal_cash.DataModels.UserAdsResponse;
import com.mb_lab.halal_cash.DataModels.UserLoginDataModel;
import com.mb_lab.halal_cash.DataModels.UserLoginResponse;
import com.mb_lab.halal_cash.DataModels.UserOrderHistoryResponse;
import com.mb_lab.halal_cash.DataModels.UserPaySendConfirmationResponse;
import com.mb_lab.halal_cash.DataModels.UserRegistrationDataModel;
import com.mb_lab.halal_cash.DataModels.UserRegistrationResponse;
import com.mb_lab.halal_cash.DataModels.UserTransactionHistoryResponse;
import com.mb_lab.halal_cash.DataModels.UserWalletResponse;


import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RetrofitAPI {
    // as we are making a post request to post a data
    // so we are annotating it with post/get
    // and along with that we are passing a parameter as users
    interface Login {
        @POST(Constants.LOGIN_URL)
        Call<UserLoginResponse> createPostBodyForUserLogin(@Body UserLoginDataModel userLoginDataModel);
    }

    interface RefreshToken {
        @POST(Constants.REFRESH_TOKEN_URL)
        Call<UserLoginResponse> createPostBodyForRefreshToken(@Header("Authorization") String TOKEN, @Query("token") String token);
    }

    interface Registration {
        @POST(Constants.REGISTRATION_URL)
        Call<UserRegistrationResponse> createPostBodyForUserRegistration(@Body UserRegistrationDataModel userRegistrationDataModel);
    }

    interface AccountVerification {
        @POST(Constants.ACCOUNT_AUTHENTICATION_URL)
        Call<UserRegistrationResponse> createPostBodyForUserAccountVerification(@Body UserRegistrationDataModel userRegistrationDataModel);
    }

    interface WalletInformation {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @GET(Constants.WALLET_URL)
        Call<UserWalletResponse> createGetHeaderForUserWalletInformation(@Header("Authorization") String TOKEN, @Query("token") String token);
    }

    interface ValidReceiverInformation {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @GET(Constants.GET_RECEIVER_URL)
        Call<GetReceiverInformationResponse> createGetValidReceiverInformation(@Header("Authorization") String TOKEN, @Query("type") String idType, @Query("value") String id);
    }

    interface CheckSendingBalance {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @GET(Constants.GET_CHECK_SENDING_BALANCE_URL)
        Call<UserLoginResponse> createGetCheckSendingBalance(@Header("Authorization") String TOKEN, @Query("asset_type") String asset_type, @Query("amount") String amount);
    }

    interface CheckPayPinStatus {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @GET(Constants.GET_CHECK_PAY_PIN_STATUS_URL)
        Call<SimpleResponse> createGetCheckPayPinStatus(@Header("Authorization") String TOKEN);
    }

    interface SetPayPin {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @POST(Constants.POST_SET_PAY_PIN_URL)
        Call<SimpleResponse> createGetSetPayPin(@Header("Authorization") String TOKEN, @Query("pay_pin") String payPin);
    }

    interface SendPaySendTransInfoAtOnce {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @POST(Constants.POST_SEND_STORE_URL)
        Call<UserPaySendConfirmationResponse> createSendPaySendTransInfoAtOnce(@Header("Authorization") String TOKEN, @QueryMap HashMap<String, String> queryMap);
    }

    interface GetUserTransactionHistory {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @GET(Constants.GET_TRANSACTION_HISTORY_URL + "/{path}")
        Call<UserTransactionHistoryResponse> createGetTransactionHistory(@Header("Authorization") String TOKEN, @Path("path") String path, @Query("start_date") @Nullable String startDate, @Query("end_date") @Nullable String endDate);
    }

    interface GetUnlockReceivedTransaction {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @GET(Constants.GET_UNLOCK_RECEIVED_TRANSACTION_URL)
        Call<JsonObject> createGetTryUnlockTransaction(@Header("Authorization") String TOKEN, @Query("trans_id") String trans_id, @Query("pin") String pin);
    }

    interface GetImageFromUrl {
        @GET
        Call<ResponseBody> getBitmap(@Url String fileUrl);
    }

    interface GetCurrentGoldPriceUrl {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @GET(Constants.GET_CURRENT_GOLD_PRICE_URL)
        Call<GetGoldPriceResponse> createGetCurrentGoldPrice(@Header("Authorization") String TOKEN);
    }

    interface GetAllAds {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @GET(Constants.GET_ALL_P2P_ADS_URL)
        Call<UserAdsResponse> createGetAllAdsList(@Header("Authorization") String TOKEN);
//        Call<UserAdsResponse> getAllAdsList(@Header("Authorization") String TOKEN, @Path("path") String fileUrl);
    }

    interface GetOwnCreatedAds {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @GET(Constants.GET_P2P_CREATED_ADS_URL)
        Call<GetOwnCreatedAdsResponse> createGetAllOwnCreatedAdsList(@Header("Authorization") String TOKEN);
//        Call<UserAdsResponse> getAllAdsList(@Header("Authorization") String TOKEN, @Path("path") String fileUrl);
    }

    interface PostCreateAdRequest {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @POST(Constants.POST_P2P_CREATE_ADS_REQUEST_URL)
        Call<SimpleResponse> createPostCreateAdRequest(@Header("Authorization") String TOKEN,
                                                       @Query("ad_type") String ad_type,
                                                       @Query("asset_type") String asset_type,
                                                       @Query("user_price") String user_price,
                                                       @Query("price_type") String price_type,
                                                       @Query("payable_with") String payable_with,
                                                       @Query("advertise_total_amount") String advertise_total_amount,
                                                       @Query("order_limit_max") String order_limit_max,
                                                       @Query("order_limit_min") String order_limit_min);
    }


    interface UpdateUserProfileName {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @POST(Constants.POST_UPDATE_USER_PROFILE_NAME_URL)
        Call<ResponseBody> createPostRequestForNameUpdate(@Header("Authorization") String TOKEN, @Query("name") String name);
    }

    interface UpdateUserProfilePicture {
        @Multipart
        @POST(Constants.POST_UPDATE_USER_PROFILE_IMAGE_URL)
        Call<ResponseBody> uploadImage(@Header("Authorization") String authorization, @Part MultipartBody.Part image);
    }


    interface PostBuySellAds {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @POST(Constants.POST_BUY_SELL_ADS_URL + "/{adsType}/{adsId}")
        Call<ResponseBody> createPostForBuySellAds(@Header("Authorization") String TOKEN, @Path("adsType") String adsType, @Path("adsId") int adsId,@Query("pay_pin") int pay_pin, @Query("payable_amount") String payable_amount);
    }

    interface GetOrderHistory {
        @Headers({"Content-Type: application/json;charset=UTF-8"})
        @GET(Constants.GET_ALL_ADS_TRANSACTIONS)
        Call<UserOrderHistoryResponse> createGetAllAdsOrderHistory(@Header("Authorization") String TOKEN);
    }

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET(Constants.GET_DEPOSIT_AGENT_LIST_URL)
    Call<GetDepositAgentListResponse> createGetDepositAgentList(@Header("Authorization") String TOKEN);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST(Constants.POST_RESET_PASSWORD_1_REQUEST_URL)
    Call<SimpleResponse> createPostForResetPassword_1_Request(@Query("user_id_type") String user_id_type, @Query("user_id") String user_id);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST(Constants.POST_RESET_PASSWORD_2_VERIFY_OTP_URL)
    Call<SimpleResponse> createPostForResetPassword_2_VerifyOtp(@Query("user_id") String user_id, @Query("user_id_type") String user_id_type, @Query("code") String code);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST(Constants.POST_RESET_PASSWORD_3_SET_PASSWORD_URL)
    Call<SimpleResponse> createPostForResetPassword_3_setPassword(@Query("user_id") String user_id, @Query("user_id_type") String user_id_type, @Query("password") String password);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET(Constants.GET_ALL_HOME_NOTIFICATION_URL)
    Call<HomeNotificationResponse> createGetAllHomeNotification(@Header("Authorization") String TOKEN);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST(Constants.POST_UPDATE_USER_ID_1_REQUEST_URL)
    Call<SimpleResponse> createPostForUpdateUserID_1(@Header("Authorization") String TOKEN, @Query("user_id_type") String user_id_type, @Query("user_id") String user_id);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST(Constants.POST_UPDATE_USER_ID_2_VERIFY_URL)
    Call<SimpleResponse> createPostForUpdateUserID_2(@Header("Authorization") String TOKEN, @Query("user_id") String user_id, @Query("user_id_type") String user_id_type, @Query("code") String code);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST(Constants.POST_UPDATE_USER_ID_3_UPDATE_URL)
    Call<SimpleResponse> createPostForUpdateUserID_3(@Header("Authorization") String TOKEN, @Query("user_id") String user_id, @Query("user_id_type") String user_id_type, @Query("new_user_id") String new_user_id);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST(Constants.POST_DELETE_USER_ACCOUNT_PERMANENTLY_URL)
    Call<SimpleResponse> createPostForDeleteAccount(@Header("Authorization") String TOKEN);


}
