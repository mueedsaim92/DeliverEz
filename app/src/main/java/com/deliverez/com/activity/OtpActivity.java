package com.deliverez.com.activity;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.deliverez.com.BuildConfig;
import com.deliverez.com.R;
import com.deliverez.com.api.ApiClient;
import com.deliverez.com.api.ApiInterface;
import com.deliverez.com.apiResponse.OtpResponse;
import com.deliverez.com.tools.Methods;
import com.deliverez.com.tools.SessionManager;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {
    private static final String TAG = OtpActivity.class.getSimpleName();

    Toolbar toolbar;
    TextView enterOtpTextTV,timerTV,notRecieveMsgTV;
    Button verifyAndUpdateBT,resendBT;
    EditText otpET;

    private ProgressDialog mProgressDialog;
    SimpleDateFormat df = new SimpleDateFormat("mm:ss 'sec'");
    static final long INTERVAL = 1000;
    static final long DEFAULT_TIMEOUT = 1000 * 60 * 2;
    private CountDownTimer otpTimer;
    String mobileStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mobileStr = getIntent().getStringExtra("MOBILE");

        customToolbar("OTP");

        enterOtpTextTV =findViewById(R.id.enterOtpTextTV);
        timerTV =findViewById(R.id.timerTV);
        notRecieveMsgTV =findViewById(R.id.notRecieveMsgTV);
        verifyAndUpdateBT =findViewById(R.id.verifyAndUpdateBT);
        resendBT =findViewById(R.id.resendBT);
        otpET =findViewById(R.id.otpET);

        notRecieveMsgTV.setOnClickListener(this);
        verifyAndUpdateBT.setOnClickListener(this);
        resendBT.setOnClickListener(this);


        sendOTP(mobileStr);
    }


    public void customToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.appWhite));

        Drawable mBackArrow;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBackArrow = getResources().getDrawable(R.drawable.arrow_back, null);
        } else {
            mBackArrow = getResources().getDrawable(R.drawable.arrow_back);
        }
        mBackArrow.setColorFilter(getResources().getColor(R.color.appWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(mBackArrow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void sendOTP(String mobileStr) {
        timerStart();
        String s = "OTP is sent to your mobile " + "<b>" + mobileStr + "</b>" + "." + " Please enter one time password to proceed.";
        enterOtpTextTV.setText(Html.fromHtml(s));
        enterOtpTextTV.setVisibility(View.VISIBLE);
    }
    
   /* private void sendOTP(String mobileStr) {
        timerStart();
        String actionStr = "register";
        String s = "OTP is sent to your mobile " + "<b>" + mobileStr + "</b>" + "." + " Please enter one time password to proceed.";
        enterOtpTextTV.setText(Html.fromHtml(s));
        enterOtpTextTV.setVisibility(View.VISIBLE);

        RequestBody mobileRB = RequestBody.create(MediaType.parse("multipart/form-data"), mobileStr);
        RequestBody actionRB = RequestBody.create(MediaType.parse("multipart/form-data"), actionStr);

        if (Methods.isInternetConnected(getApplicationContext(), true)) {
            mProgressDialog = Methods.setUpProgressDialog(getApplicationContext(), "Please wait...", false);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<OtpResponse> call = apiService.sendOTP(mobileRB, actionRB);

            call.enqueue(new retrofit2.Callback<OtpResponse>() {
                @Override
                public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }
                    OtpResponse otpResponse = response.body();
                    Log.e("SEND OTP RESPONSE--->", "" + otpResponse);

                    try {
                        if (otpResponse != null && otpResponse.getStatus() == 0) {
                           // Toast.makeText(this, "" + otpResponse.getPayload(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (otpResponse != null && otpResponse.getStatus() == 1) {
                            Methods.hideSoftKeyboard(OtpActivity.this);
                        }

                    } catch (Exception ex) {
                        Log.e(TAG, ex.getLocalizedMessage(), ex);
                    }
                }

                @Override
                public void onFailure(Call<OtpResponse> call, Throwable t) {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }

                    Log.e(TAG, "exception>>>" + t);
                }

            });

        }
    }*/

    public void checkOTP() {
        String otpString = otpET.getText().toString().trim();
        if (!validateOTP(otpString)) {
            return;
        }
        startPayment();
        //verifyOTP(otpString, token, mobile, action, deviceID, gcmID, deviceToken);
    }




    public void timerStart() {
        if (otpTimer != null) {
            otpTimer.cancel();
            otpTimer = null;
        }

        otpTimer = new CountDownTimer(120000, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                String countDownTimer = convertSecondToHHMMString(millisUntilFinished / INTERVAL);
                timerTV.setText(countDownTimer);
            }

            @Override
            public void onFinish() {
                timerTV.setVisibility(View.GONE);
                //firsTimeOTPCounter = true;
                notRecieveMsgTV.setVisibility(View.VISIBLE);
                resendBT.setVisibility(View.VISIBLE);
                verifyAndUpdateBT.setVisibility(View.GONE);
                otpET.setHint("Enter OTP");
                resendBT.setEnabled(true);
            }
        };


        otpTimer.start();
        timerTV.setText("00:00");
        timerTV.setVisibility(View.VISIBLE);
        notRecieveMsgTV.setVisibility(View.GONE);

        resendBT.setVisibility(View.GONE);
        verifyAndUpdateBT.setVisibility(View.VISIBLE);
        resendBT.setEnabled(false);

    }


    private String convertSecondToHHMMString(long secondTime) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        df.setTimeZone(tz);
        String time = df.format(new Date(secondTime * INTERVAL));
        return time;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.verifyAndUpdateBT:
                checkOTP();
                break;

            case R.id.resendBT:
                sendOTP(mobileStr);
                break;

        }
    }


    private boolean validateOTP(String otp) {
        if (otp.isEmpty()) {
            Toast.makeText(this, "Enter Valid OTP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void startPayment() {
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");

            //String payment = editTextPayment.getText().toString();
            String payment = "1";

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "sikander@gkmit.co");
            preFill.put("contact", "9680224241");

            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment successfully done! " , Toast.LENGTH_SHORT).show();
        Intent dash = new Intent(getApplicationContext(),DashboardActivity.class);
        startActivity(dash);
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }


}