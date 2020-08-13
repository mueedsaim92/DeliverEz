package com.deliverez.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.deliverez.com.R;
import com.deliverez.com.activity.ChangePasswordActivity;
import com.deliverez.com.activity.DashboardActivity;
import com.deliverez.com.activity.MyProfileActivity;
import com.deliverez.com.activity.WebViewActivity;
import com.deliverez.com.tools.Methods;

/**
 * Created by new on 08-12-2017.
 */

public class MoreFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private View parent_view;
    ConstraintLayout myProfileCL,changePasswordCL,faqCL,termAndCondCL,supportAndHelpCL;
    ImageView faqIV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent_view = inflater.inflate(R.layout.frag_more, container, false);

        myProfileCL = parent_view.findViewById(R.id.myProfileCL);
        changePasswordCL = parent_view.findViewById(R.id.changePasswordCL);
        faqCL = parent_view.findViewById(R.id.faqCL);
        termAndCondCL = parent_view.findViewById(R.id.termAndCondCL);
        supportAndHelpCL = parent_view.findViewById(R.id.supportAndHelpCL);
        faqIV = parent_view.findViewById(R.id.faqIV);

        faqIV.setColorFilter(getResources().getColor(R.color.darkTransparent), PorterDuff.Mode.SRC_ATOP);


        myProfileCL.setOnClickListener(this);
        changePasswordCL.setOnClickListener(this);
        faqCL.setOnClickListener(this);
        supportAndHelpCL.setOnClickListener(this);
        termAndCondCL.setOnClickListener(this);

        return parent_view;
    }


    @Override
    public void onClick(View view) {
        Intent intent=null;
        switch (view.getId()) {

            case R.id.myProfileCL:
               // if(Methods.isInternetConnected(getActivity(),true)){
                    intent = new Intent(getActivity(), MyProfileActivity.class);
                    startActivity(intent);
               // }
                break;

            case R.id.changePasswordCL:
              //  if(Methods.isInternetConnected(getActivity(),true)){
                    intent = new Intent(getActivity(), ChangePasswordActivity.class);
                    startActivity(intent);
               // }
                break;

            case R.id.faqCL:
                if(Methods.isInternetConnected(getActivity(),true)){
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "https://www.google.com/");
                    intent.putExtra("title", "FAQ");
                    startActivity(intent);
                }
                break;

            case R.id.termAndCondCL:
                if(Methods.isInternetConnected(getActivity(),true)){
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "https://www.journaldev.com");
                    intent.putExtra("title", getResources().getString(R.string.t_and_c));
                    startActivity(intent);
                }
                break;

            case R.id.supportAndHelpCL:
                if(Methods.isInternetConnected(getActivity(),true)){
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "https://www.journaldev.com");
                    intent.putExtra("title", "Support And Help");
                    startActivity(intent);
                }
                break;



        }
    }
}