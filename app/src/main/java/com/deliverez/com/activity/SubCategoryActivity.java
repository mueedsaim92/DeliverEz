package com.deliverez.com.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deliverez.com.R;
import com.deliverez.com.adapter.CategorysAdapter;
import com.deliverez.com.adapter.SubCategorysAdapter;
import com.deliverez.com.models.Category;
import com.deliverez.com.tools.DeliverezConstants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class SubCategoryActivity extends AppCompatActivity implements View.OnClickListener, SubCategorysAdapter.SubCategoryListener, CategorysAdapter.ActionListener {

    Toolbar mToolbar;
    ArrayList<Category> grocerySubList;
    ArrayList<Category> vegitablesSubList;
    ArrayList<Category> dairySubList;
    ArrayList<Category> serviceSubList;
    ArrayList<Category> restaurantsSubList;

    RecyclerView productsRV, subCategoryRV;
    CategorysAdapter categoryAdapter;
    SubCategorysAdapter subCategorysAdapter;
    LinearLayout addProductsLL;
    TextView fabTV;
    FloatingActionButton fab;
    String catType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        addProductsLL = findViewById(R.id.addProductsLL);
        fabTV = findViewById(R.id.fabTV);
        productsRV = findViewById(R.id.productsRV);
        subCategoryRV = findViewById(R.id.subCategoryRV);
        fab = findViewById(R.id.fab);

        addProductsLL.setOnClickListener(this);

        catType = getIntent().getStringExtra("Title");
        String title = getIntent().getStringExtra("Title");
        customToolbar(title);

        vegitablesSubList = DeliverezConstants.prepareData(DeliverezConstants.vegiAndFruitSubCatNameList, DeliverezConstants.vegiAndFruitSubCatImgs);
        dairySubList = DeliverezConstants.prepareData(DeliverezConstants.dairySubCatNameList, DeliverezConstants.dairySubCatImgs);
        serviceSubList = DeliverezConstants.prepareData(DeliverezConstants.serviceNameList, DeliverezConstants.serviceSubCatImgs);
        restaurantsSubList = DeliverezConstants.prepareData(DeliverezConstants.restaurantSubItemsName, DeliverezConstants.restaurantSubItemsImgs);

        if (title.equalsIgnoreCase("Grocery")) {
            productsRV.setVisibility(View.GONE);
            //initAdapter(grocerySubList);
            subCategoryListAdapter(dairySubList);
        } else if (title.equalsIgnoreCase("Vegetables & Fruits")) {
            productsRV.setVisibility(View.VISIBLE);
            subCategoryHorizontalAdapter(vegitablesSubList);
            subCategoryListAdapter(vegitablesSubList);
        } else if (title.equalsIgnoreCase("Dairy")) {
            productsRV.setVisibility(View.VISIBLE);
            subCategoryHorizontalAdapter(dairySubList);
            subCategoryListAdapter(dairySubList);
        } else if (title.equalsIgnoreCase("Service Provider")) {
            productsRV.setVisibility(View.VISIBLE);
            subCategoryHorizontalAdapter(serviceSubList);
            subCategoryListAdapter(serviceSubList);
        } else if (title.equalsIgnoreCase("Restaurants")) {
            productsRV.setVisibility(View.VISIBLE);
            subCategoryHorizontalAdapter(restaurantsSubList);
            subCategoryListAdapter(restaurantsSubList);
        }

        //


        subCategoryRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE && fabTV.getVisibility() == View.VISIBLE) {
                    fab.hide();
                    fabTV.setVisibility(View.GONE);
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE && fabTV.getVisibility() != View.VISIBLE) {
                    fab.show();
                    fabTV.setVisibility(View.VISIBLE);
                }
            }
        });

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

    public void subCategoryHorizontalAdapter(ArrayList<Category> list) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productsRV.setLayoutManager(mLayoutManager);
        categoryAdapter = new CategorysAdapter(this, list);
        productsRV.setAdapter(categoryAdapter);
        categoryAdapter.setActionListener(this);
    }

    public void subCategoryListAdapter(ArrayList<Category> list) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        subCategoryRV.setLayoutManager(mLayoutManager);
        subCategorysAdapter = new SubCategorysAdapter(this, list);
        subCategoryRV.setAdapter(subCategorysAdapter);
        subCategorysAdapter.setActionListener(this);
    }


    @Override
    public void actionAfterSelectCat(Category category, boolean isSelected) {
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditSubCat(Category category, boolean isSelected, int position) {
        Toast.makeText(this, "Edit Screen Here", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteSubCat(Category category, boolean isSelected, int position) {
        String massage = "Are you sure you want to delete " + "<b>" + category.getGameName() + "?" + "</b>";
        deleteSubCatPopup(massage, category.getGameName(), position);
    }


    public void deleteSubCatPopup(String massage, final String name, final int position) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_popup);

        TextView msgTV = dialog.findViewById(R.id.msgTV);
        Button cancelBT = dialog.findViewById(R.id.cancelBT);
        Button okBT = dialog.findViewById(R.id.okBT);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        msgTV.setText(Html.fromHtml(massage));

        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        okBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), name + " Removed ", Toast.LENGTH_SHORT).show();
                dairySubList.remove(position);
                subCategorysAdapter.notifyDataSetChanged();
            }
        });
        dialog.show();
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.addProductsLL:
                if (catType.equalsIgnoreCase("Service Provider")) {
                    intent = new Intent(getApplicationContext(), AddServiceProductsActivity.class);
                } else if (catType.equalsIgnoreCase("Restaurants")) {
                    intent = new Intent(getApplicationContext(), AddRestaurantProductsActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), AddProductsActivity.class);
                }
                //
                //
                intent.putExtra("CAT_TYPE", catType);
                startActivity(intent);
                break;
        }
    }


}