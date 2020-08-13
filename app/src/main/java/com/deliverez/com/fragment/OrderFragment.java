package com.deliverez.com.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deliverez.com.R;
import com.deliverez.com.adapter.ProductsAdapter;
import com.deliverez.com.models.GameName;

import java.util.ArrayList;

/**
 * Created by new on 08-12-2017.
 */

public class OrderFragment extends Fragment{

    private Context mContext;
    private View parent_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent_view = inflater.inflate(R.layout.frag_order, container, false);
        parent_view.setBackgroundColor(Color.WHITE);


        return parent_view;
    }



}