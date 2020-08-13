package com.deliverez.com.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.deliverez.com.R;
import com.deliverez.com.fragment.MoreFragment;
import com.deliverez.com.fragment.OrderFragment;
import com.deliverez.com.fragment.ProductsFragment;
import com.google.android.material.tabs.TabLayout;

public class DashboardActivity extends AppCompatActivity {
    private final static String TAG= DashboardActivity.class.getSimpleName();

    private TabLayout tabLayout;
    private ImageView completedTabIV, moreTabIV, productTabIV, orderTabIV, syncTabIV;
    private TextView completedTV, moreTabTV, productsTabTV, orderTabTV, syncTV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tabLayout = findViewById(R.id.tabLayout);
        intializeTabLayout();
        productsTabSelected(R.drawable.new_job_hovr, R.color.colorAccent, "Products");


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelectedUnSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabSelectedUnSelected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tabSelectedUnSelected(tab);
            }
        });




    }


    public void updateSelectedTab(int selectedImg, int selectedColor, String type) {
        if (type.equalsIgnoreCase("Products")) {
            JobActivityOptimize.selectedTab(productTabIV, productsTabTV, selectedImg, getApplicationContext());
        } else {
            JobActivityOptimize.unSelectedTab(productTabIV, productsTabTV, R.drawable.new_job, getApplicationContext());
        }

        if (type.equalsIgnoreCase("Order")) {
            JobActivityOptimize.selectedTab(orderTabIV, orderTabTV, selectedImg, getApplicationContext());
        } else {
            JobActivityOptimize.unSelectedTab(orderTabIV, orderTabTV, R.drawable.report, getApplicationContext());
        }


        if (type.equalsIgnoreCase("More")) {
            JobActivityOptimize.selectedTab(moreTabIV, moreTabTV, selectedImg, getApplicationContext());
        } else {
            JobActivityOptimize.unSelectedTab(moreTabIV, moreTabTV, R.drawable.account, getApplicationContext());
        }

    }

    private void intializeTabLayout() {
        View newJobTab = View.inflate(this, R.layout.tabtextwithicon, null);
        productTabIV = newJobTab.findViewById(R.id.homescreen_icon);
        productsTabTV = newJobTab.findViewById(R.id.homescreen_text);
        addTabIconText(productTabIV, productsTabTV, newJobTab, "Products");

        View myJobsTab = View.inflate(this, R.layout.tabtextwithicon, null);
        orderTabIV = myJobsTab.findViewById(R.id.homescreen_icon);
        orderTabTV = myJobsTab.findViewById(R.id.homescreen_text);
        addTabIconText(orderTabIV, orderTabTV, myJobsTab, "Order");

        View accountTab = View.inflate(this, R.layout.tabtextwithicon, null);
        moreTabIV = accountTab.findViewById(R.id.homescreen_icon);
        moreTabTV = accountTab.findViewById(R.id.homescreen_text);
        addTabIconText(moreTabIV, moreTabTV, accountTab, "More");
    }

    private void addTabIconText(ImageView tabIV, TextView tabTV, View tabView, String tabType) {
        tabIV.setImageResource(JobActivityOptimize.getTabTextIcon(tabType));
        tabTV.setText(JobActivityOptimize.getTabTextName(tabType));
        tabTV.setTextColor(ContextCompat.getColor(DashboardActivity.this, R.color.greyLight));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabView), JobActivityOptimize.getTabPosition(tabType));
    }

    private void openFragment(Fragment fragment, String comeFrom) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commitAllowingStateLoss();
        Bundle bundle = new Bundle();
        bundle.putString("ComeFrom", comeFrom);
        fragment.setArguments(bundle);
       // SharedPreferenceHelper.setLastTab(getApplicationContext(), comeFrom);
    }


    public void tabSelectedUnSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            productsTabSelected(R.drawable.new_job_hovr, R.color.colorAccent, "Products");
        } else if (tab.getPosition() == 1) {
            orderTabSelected(R.drawable.reporthover, R.color.colorAccent, "Order");
        }  else if (tab.getPosition() == 2) {
            moreTabSelected(R.drawable.accounthover, R.color.colorAccent, "More");
        }
    }


    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
        } else {
            Toast.makeText(this, "Press back again to Exit app.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2 * 1000);
        }
    }


    public void productsTabSelected(int selectedImg, int selectedColor, String fragType) {
        updateSelectedTab(selectedImg, selectedColor, fragType);
        ProductsFragment productsFragment = new ProductsFragment();
        openFragment(productsFragment, fragType);
    }

    public void orderTabSelected(int selectedImg, int selectedColor, String fragType) {
        updateSelectedTab(selectedImg, selectedColor, fragType);
        OrderFragment orderFragment = new OrderFragment();
        openFragment(orderFragment, fragType);
    }


    public void moreTabSelected(int selectedImg, int selectedColor, String fragType) {
        updateSelectedTab(selectedImg, selectedColor, fragType);
        MoreFragment moreFragment = new MoreFragment();
        openFragment(moreFragment, fragType);
    }




}
