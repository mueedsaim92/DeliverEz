package com.deliverez.com.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deliverez.com.R;
import com.deliverez.com.adapter.CategorysAdapter;
import com.deliverez.com.models.GameName;
import com.deliverez.com.tools.DeliverezConstants;
import com.deliverez.com.tools.Methods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ForgotPasswordActivity.class.getSimpleName();

    Toolbar toolbar;
    EditText emailET;
    Button sendBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailET = findViewById(R.id.emailET);
        sendBT = findViewById(R.id.sendBT);
        sendBT.setOnClickListener(this);

        customToolbar();
    }


    public void customToolbar() {
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        this.toolbar.setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.sendBT:
                if (emailET.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "" + "Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "" + "Send Successfully..", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}