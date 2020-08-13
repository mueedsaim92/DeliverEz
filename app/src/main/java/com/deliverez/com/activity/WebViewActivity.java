package com.deliverez.com.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.deliverez.com.R;
import com.deliverez.com.tools.Methods;

public class WebViewActivity extends AppCompatActivity {

    WebView webview;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");

        if (!Methods.valid(title))
            title = getResources().getString(R.string.web_view);

        customToolbar(title);


        this.webview = findViewById(R.id.webview);

        webview.setWebViewClient(new WebViewClient());
        /*webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setBuiltInZoomControls(true);*/

        //For circle progress...
        final ProgressDialog mProgress = ProgressDialog.show(WebViewActivity.this, "", "Please wait...", true);
        webview.setWebViewClient(new WebViewClient() {
            // ProgressDialog mProgress = null;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgress.show();
            }

            public void onPageFinished(WebView view, String url) {
                if(mProgress.isShowing()) {
                    // mProgress.hide();
                    mProgress.dismiss();
                }
            }
        });
        openURL(url);
    }

    private void customToolbar(String title){
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


    private void openURL(String url) {
        webview.loadUrl(url);
        webview.requestFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
