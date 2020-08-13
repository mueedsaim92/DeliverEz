package com.deliverez.com.api;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by madan on 02-02-2016.
 */
public class ResponseInterceptor implements Interceptor {
    private Context context;

    public ResponseInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .method(original.method(), original.body());

            original = requestBuilder.build();
        /*RequestBody requestBody = original.body();
        try {
            final Buffer buffer = new Buffer();
            if (requestBody != null)
                requestBody.writeTo(buffer);
            Log.d("REQUEST", buffer.readUtf8());
        } catch (final IOException e) {
            Log.d("REQUEST", "NOT WORKED");
        }

        Request request = original.newBuilder()
                .header("Authorization", "key=" + Constants.SERVER_API_KEY)
                .header("Content-Type", "application/json")
                .method(original.method(), original.body())
                .build();
*/
            //Log.d("REQUEST", original.toString());

            Response response = chain.proceed(original);
            String responseString = new String(response.body().bytes());
            responseString = responseString.replaceFirst("^\\d+", "");
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), responseString))
                    .build();
        } catch (Exception e) {
            Log.e(ResponseInterceptor.class.getSimpleName(), e.getLocalizedMessage(), e);
            throw e;
        }

    }


}