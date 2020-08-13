package com.deliverez.com.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.deliverez.com.R;
import com.deliverez.com.api.ApiClient;
import com.deliverez.com.api.ApiInterface;
import com.deliverez.com.apiRequests.LoginRequest;
import com.deliverez.com.apiResponse.LoginResponse;
import com.deliverez.com.tools.DeliverezConstants;
import com.deliverez.com.tools.Methods;
import com.deliverez.com.tools.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG=LoginActivity.class.getSimpleName();
    LinearLayout signUpLL;
    TextView forgotPasswordTV;
    EditText mobileET, passwordET;
    Button loginBT;
    private ProgressDialog mProgressDialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        mobileET = findViewById(R.id.mobileET);
        passwordET = findViewById(R.id.passwordET);
        forgotPasswordTV = findViewById(R.id.forgotPasswordTV);
        signUpLL = findViewById(R.id.signUpLL);
        loginBT = findViewById(R.id.loginBT);

        forgotPasswordTV.setOnClickListener(this);
        signUpLL.setOnClickListener(this);
        loginBT.setOnClickListener(this);



        if (checkAndRequestPermissions()) {
            //Toast.makeText(getActivity(), "User Granted....", Toast.LENGTH_SHORT).show();
        }

       /* String forgotPassword = "Forgot Password? ";
        String clickHere = "Click here";
        SpannableString content = new SpannableString(clickHere);
        content.setSpan(new UnderlineSpan(), 0, clickHere.length(), 0);
        forgotPasswordTV.setText(forgotPassword + content);*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.loginBT:
                if (mobileET.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "" + "Enter valid number", Toast.LENGTH_SHORT).show();
                } else if (passwordET.getText().toString().length() < 5) {
                    Toast.makeText(getApplicationContext(), "" + "Password will be max 6 character", Toast.LENGTH_SHORT).show();
                } else {
                    moveToDashboard();
                   // makeLogin(mobileET.getText().toString(),passwordET.getText().toString());
                }

                break;

            case R.id.signUpLL:
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                break;

            case R.id.forgotPasswordTV:
                Intent forgotPass = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(forgotPass);
                break;

        }
    }


    private void makeLogin(String mobile, String password) {
        HashMap<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");

        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhoneNumber(mobile);
        loginRequest.setPassword(password);
        loginRequest.setDeviceToken(DeliverezConstants.getDeviceId());
        loginRequest.setDeviceType("Android");

        if (Methods.isInternetConnected(getApplicationContext(), true)) {
            mProgressDialog = Methods.setUpProgressDialog(this, "Please wait...", false);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<LoginResponse> call = apiService.login(header,loginRequest);
            call.enqueue(new retrofit2.Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }
                    LoginResponse loginResponse = response.body();
                    Log.d("RESPONSE >>> ", "" + loginResponse);
                    try {
                        if (loginResponse != null && loginResponse.getStatus() == 200) {
                            moveToDashboard();
                            sessionManager.setUserLoggedIn(true);
                            sessionManager.setToken(loginResponse.getUserdata().getToken());
                            Toast.makeText(LoginActivity.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(getApplicationContext(), "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    } catch (Exception ex) {
                        Log.e(TAG, ex.getLocalizedMessage(), ex);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }
                    Log.e(TAG, "exception>>>" + t);
                }

            });


        }

    }

    public void moveToDashboard() {
        Intent loginIntent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        return true;
    }
}