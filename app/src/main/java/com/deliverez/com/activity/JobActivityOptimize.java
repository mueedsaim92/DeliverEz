package com.deliverez.com.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.deliverez.com.R;


public class JobActivityOptimize {

    public static int getTabTextIcon(String tabType){
        int image=-1;
        switch (tabType) {
            case "Products":
                image = R.drawable.new_job;
                break;
            case "Order":
                image = R.drawable.report;
                break;
            case "More":
                image = R.drawable.account;
                break;
        }
        return image;
    }

    public static String getTabTextName(String tabType){
        switch (tabType) {
            case "Products":
                tabType = "Products";
                break;
            case "Order":
                tabType = "Order";
                break;
            case "More":
                tabType = "More";
                break;
        }
        return tabType;
    }

    public static int getTabPosition(String tabType){
        int position=0;
        switch (tabType) {
            case "Products":
                position=0;
                break;
            case "Order":
                position=1;
                break;
            case "More":
                position=2;
                break;
        }
        return position;
    }


    public static void selectedTab(ImageView selectedIV, TextView selectedTV, int selectedImg, Context context){
        selectedIV.setImageDrawable(ContextCompat.getDrawable(context, selectedImg));
        selectedIV.setColorFilter(context.getResources().getColor(R.color.appTheme), PorterDuff.Mode.SRC_ATOP);
       // selectedTV.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
        selectedTV.setTextColor(ContextCompat.getColor(context,R.color.appTheme));
    }
    public static void unSelectedTab(ImageView selectedIV, TextView selectedTV,int unSelectedImg,Context context){
        selectedIV.setImageDrawable(ContextCompat.getDrawable(context, unSelectedImg));
        selectedIV.setColorFilter(context.getResources().getColor(R.color.greyLight), PorterDuff.Mode.SRC_ATOP);
        selectedTV.setTextColor(ContextCompat.getColor(context,R.color.greyLight));
    }



}
