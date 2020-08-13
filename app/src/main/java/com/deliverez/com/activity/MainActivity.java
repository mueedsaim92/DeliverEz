package com.deliverez.com.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.deliverez.com.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_products);
    }
}