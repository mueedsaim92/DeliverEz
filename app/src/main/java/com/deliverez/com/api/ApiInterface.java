package com.deliverez.com.api;


import com.deliverez.com.apiRequests.LoginRequest;
import com.deliverez.com.apiResponse.CategoryResponse;
import com.deliverez.com.apiResponse.LoginResponse;
import com.deliverez.com.apiResponse.OtpResponse;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dell on 16/2/18.
 */

public interface ApiInterface {

      /* @POST("getAppVersionCheck/")
    Call<AppVersionResponse> getAppVersion(@HeaderMap HashMap<String, Object> headers, @Body VersionRequest body );*/

    @POST("user/login")
    Call<LoginResponse> login(@HeaderMap HashMap<String, Object> headers, @Body LoginRequest loginRequest);

    @GET("category")
    Call<CategoryResponse> getCategory(@HeaderMap HashMap<String, Object> headers);

    @GET("api/Version")
    Call<JsonObject> getVersion(@QueryMap HashMap<String, Object> body, @HeaderMap Map<String, Object> headers);


    @Headers({"sessionToken:0", "platform:android", "packageName:com.peybiz.pr", "isAuthRequired:false"})
    @Multipart
    @POST("data/user/sendOTP/")
    Call<OtpResponse> sendOTP(@Part("mobile") RequestBody mobile,
                              @Part("action") RequestBody action);



}


