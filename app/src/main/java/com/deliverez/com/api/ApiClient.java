package com.deliverez.com.api;

import com.deliverez.com.init.DeliverezApplication;
import com.deliverez.com.api.ResponseInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bytesbrick on 10/10/16.
 */

public class ApiClient {

    private static final boolean production = true;//BuildConfig.DEBUG;

    //For Live....
     //public static final String BASE_URL = production ? "http://api.dobiznow.co/" : "http://api.dobiznow.co/";

    //For Testing...
    //public static final String BASE_URL = "https://www.ecwelectronics.com/api/bookie/general/";
    public static final String BASE_URL = "http://142.93.219.205/v1.0/";

    public static boolean isProduction() {
        return production;
    }

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            //OkHttpClient.Builder client = new OkHttpClient.Builder();
            OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60 * 5, TimeUnit.SECONDS)
                    .readTimeout(60 * 5, TimeUnit.SECONDS)
                    .writeTimeout(60 * 5, TimeUnit.SECONDS);
            // okHttpClient.interceptors().add(new AddCookiesInterceptor());
            //okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());


            okHttpClient.addInterceptor(new ResponseInterceptor(DeliverezApplication.getContext()));

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(loggingInterceptor);


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


        }
        return retrofit;
    }


}