package com.deliverez.com.activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.deliverez.com.BuildConfig;
import com.deliverez.com.R;
import com.deliverez.com.api.ApiClient;
import com.deliverez.com.api.ApiInterface;
import com.deliverez.com.tools.Methods;
import com.deliverez.com.tools.SessionManager;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);

        //checkInternet();
        passScreen();
    }

    public void checkInternet() {
        if (!Methods.isInternetConnected(this, false)) {
            passScreen();
        } else {
            getCurrentVersion();
        }
    }

    private void getCurrentVersion() {
        if (!Methods.isInternetConnected(this, true)) {
            return;
        }
        HashMap<String, Object> body = new HashMap<>();
        body.put("AppType", 2);

        HashMap<String, Object> header = new HashMap<>();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getVersion(body, header).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    if (response.code() == 200) {
                        JSONObject object = new JSONObject(response.body().toString());
                        String a = BuildConfig.VERSION_NAME.replace(".", "");
                        String b = object.getString("version").replace(".", "");
                        int appVersionCode = Integer.valueOf(a);
                        int playStoreVersionCode = Integer.valueOf(b);
                        if (appVersionCode < playStoreVersionCode) {
                            boolean isMandatory = object.getBoolean("isMandatory");
                            appUpdatePopup(isMandatory, object.getString("version"), SplashActivity.this);
                        } else passScreen();
                    } else {
                        Toast.makeText(SplashActivity.this, "Something went wrong. Try again in a few minutes.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Something went wrong. Try again in a few minutes.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void appUpdatePopup(boolean isMandatory, String playStoreVersionCode, final Context context) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.update_popup);

        TextView titleTV = dialog.findViewById(R.id.titleTV);
        TextView msgTV = dialog.findViewById(R.id.msgTV);
        Button cancelBT = dialog.findViewById(R.id.cancelBT);
        Button updateBT = dialog.findViewById(R.id.updateBT);

        cancelBT.setVisibility(View.VISIBLE);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        if (isMandatory == false) {
            cancelBT.setVisibility(View.VISIBLE);
            titleTV.setText("New Version");
            msgTV.setText("Valuer Support Tool version " + playStoreVersionCode + " is now available.");
        } else {
            cancelBT.setVisibility(View.GONE);
            titleTV.setText("A new version is available");
            msgTV.setText("There is a new version  " + playStoreVersionCode + " available for download! Please update the app by visiting Play Store.");
        }


        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passScreen();
                dialog.dismiss();
            }
        });

        updateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Methods.isInternetConnected(context, true)) return;
                String url = "https://play.google.com/store/apps/details?id=com.valocity.valuersupporttool.vstprod";
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }



   /* private void passScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            // }, 2000);
        }, 2000);
    }*/

    private void passScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*if (sessionManager.isUserLoggedIn()) {
                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }*/
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }


}