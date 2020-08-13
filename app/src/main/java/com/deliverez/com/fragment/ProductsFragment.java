package com.deliverez.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.deliverez.com.R;
import com.deliverez.com.activity.SubCategoryActivity;
import com.deliverez.com.adapter.ProductsAdapter;
import com.deliverez.com.models.GameName;
import com.deliverez.com.tools.DeliverezConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by new on 08-12-2017.
 */

public class ProductsFragment extends Fragment implements ProductsAdapter.ActionListener {

    private Context mContext;
    private View parent_view;
    ProductsAdapter productsAdapter;
    RecyclerView productsRV;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent_view = inflater.inflate(R.layout.frag_products, container, false);
        parent_view.setBackgroundColor(Color.WHITE);

        productsRV = parent_view.findViewById(R.id.productsRV);
        mContext = getActivity();
        return parent_view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<GameName> list = prepareData();
        initAdapter(list);
    }

    public void initAdapter(ArrayList<GameName> list) {
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        productsRV.setLayoutManager(mLayoutManager);
        productsAdapter = new ProductsAdapter(getActivity(), list);
        productsRV.setAdapter(productsAdapter);
        productsAdapter.setActionListener(this);
    }



    @Override
    public void actionAfterSelectGame(GameName gameName) {
        Intent intent = new Intent(getActivity(), SubCategoryActivity.class);
        intent.putExtra("Title",gameName.getGameName());
        startActivity(intent);
    }

    private ArrayList<GameName> prepareData() {
        ArrayList<GameName> arrayList = new ArrayList<>();
        for (int i = 0; i < DeliverezConstants.categoryListNames.length; i++) {
            GameName postModel = new GameName();
            postModel.setGameName(DeliverezConstants.categoryListNames[i]);
            postModel.setImage(DeliverezConstants.categoryImgs[i]);
            arrayList.add(postModel);
        }
        return arrayList;
    }


}