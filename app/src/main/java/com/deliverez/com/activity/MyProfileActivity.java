package com.deliverez.com.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.deliverez.com.R;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MyProfileActivity.class.getSimpleName();
    Toolbar mToolbar;
    EditText nameET, shopNameET, shopAddressET, mobileET, emailET;
    ImageView editProfileIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        editProfileIV = findViewById(R.id.editProfileIV);
        nameET = findViewById(R.id.nameET);
        shopNameET = findViewById(R.id.shopNameET);
        shopAddressET = findViewById(R.id.shopAddressET);
        mobileET = findViewById(R.id.mobileET);
        emailET = findViewById(R.id.emailET);

        nameET.setEnabled(false);
        shopNameET.setEnabled(false);
        shopAddressET.setEnabled(false);
        mobileET.setEnabled(false);
        emailET.setEnabled(false);

        editProfileIV.setOnClickListener(this);

        customToolbar("My Profile");
    }


    private void customToolbar(String title) {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setTitle(title);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.appWhite));

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editProfileIV:
                Intent intent = new Intent(getApplicationContext(),EditProfileActivity.class);
                startActivity(intent);

                break;
        }
    }
}