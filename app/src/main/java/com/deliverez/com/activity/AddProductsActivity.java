package com.deliverez.com.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deliverez.com.R;
import com.deliverez.com.adapter.AddProductSingleRowAdapter;
import com.deliverez.com.adapter.CustomSpinnerAdapter;
import com.deliverez.com.models.AddProductRowSingleItem;
import com.deliverez.com.tools.CustomDialog;
import com.deliverez.com.tools.DeliverezConstants;
import com.deliverez.com.tools.PermissionCommon;

import java.util.ArrayList;
import java.util.List;

import static com.deliverez.com.tools.DeliverezConstants.serviceNameList;

public class AddProductsActivity extends AppCompatActivity implements View.OnClickListener, AddProductSingleRowAdapter.TourPlannerActionListener {
    private static final String TAG = AddProductsActivity.class.getSimpleName();
    Toolbar mToolbar;

    EditText priceET, qtyET;
    TextView addNewTV;
    LinearLayout multiViewLL;
    RecyclerView recyclerView;
    ImageView addProductIV;
    Spinner unitSpinner, categorySpinner;

    List<AddProductRowSingleItem> addSingleRowList;
    AddProductSingleRowAdapter addProductSingleRowAdapter;
    CustomDialog dialog;
    public String price, qty;
    ArrayList<String> unitSpinnerList;
    ArrayList<String> categorySpinnerList;
    String catType;

    public static final String vegiAndFruitSubCatNameList[] = {"Select Category","Fruits", "Frozen", "Canned"};
    public static final String dairySubCatNameList[] = {"Select Category","Milk", "Cheese", "Curds", "Butter", "Ghee", "Butter Milk", "Mawa", "Milk"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        customToolbar("Add Product");
        catType = getIntent().getStringExtra("CAT_TYPE");

        addProductIV = findViewById(R.id.addProductIV);
        priceET = findViewById(R.id.priceET);
        qtyET = findViewById(R.id.qtyET);
        categorySpinner = findViewById(R.id.categorySpinner);
        unitSpinner = findViewById(R.id.unitSpinner);
        addNewTV = findViewById(R.id.addNewTV);
        multiViewLL = findViewById(R.id.multiViewLL);
        recyclerView = findViewById(R.id.recyclerView);

        addNewTV.setOnClickListener(this);
        addProductIV.setOnClickListener(this);


        addSingleRowList = new ArrayList<>();
        addProductSingleRowAdapter = new AddProductSingleRowAdapter(getApplicationContext(), addSingleRowList);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        addProductSingleRowAdapter.setTourPlannerActionListener(this);


        categorySpinnerList = new ArrayList<>();
        if (catType.equalsIgnoreCase("Grocery")) {
            categorySpinner.setVisibility(View.GONE);
        } else if (catType.equalsIgnoreCase("Vegetables & Fruits")) {
            categorySpinner.setVisibility(View.VISIBLE);
            preparedCategorySpinnerData(vegiAndFruitSubCatNameList);
        } else if (catType.equalsIgnoreCase("Dairy")) {
            categorySpinner.setVisibility(View.VISIBLE);
            preparedCategorySpinnerData(dairySubCatNameList);
        }


        unitSpinnerList = new ArrayList<>();
        unitSpinnerList.add("Select Unit");
        unitSpinnerList.add("ml");
        unitSpinnerList.add("gram");
        unitSpinnerList.add("pieces");

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getApplicationContext(), R.layout.spinner_rows, unitSpinnerList);
        unitSpinner.setAdapter(adapter);

        CustomSpinnerAdapter categorySpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), R.layout.spinner_rows, categorySpinnerList);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //customer_id = "0";
                } else {
                    Toast.makeText(AddProductsActivity.this, unitSpinnerList.get(position) + " selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //customer_id = "0";
                } else {
                    Toast.makeText(AddProductsActivity.this, unitSpinnerList.get(position) + " selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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


    private void preparedCategorySpinnerData(String[] stringsArr) {
        for (int i = 0; i < stringsArr.length; i++) {
            /*if (i == 0) {
                categorySpinnerList.add("Select Category");
            } else*/
                categorySpinnerList.add(stringsArr[i]);
        }
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

            case R.id.addNewTV:
                if (multiViewLL.getVisibility() == View.VISIBLE) {
                    if (!priceET.getText().toString().isEmpty() && !qtyET.getText().toString().isEmpty()) {
                        addSingleRowList.add(0, new AddProductRowSingleItem(priceET.getText().toString(), qtyET.getText().toString()));
                        recyclerView.setAdapter(addProductSingleRowAdapter);
                        addProductSingleRowAdapter.notifyDataSetChanged();
                        multiViewLL.setVisibility(View.GONE);
                        addNewRowPopup();
                    } else {
                        Toast.makeText(this, "Please fill Price And Qty. First", Toast.LENGTH_SHORT).show();
                    }
                } else addNewRowPopup();
                break;

            case R.id.addProductIV:
                gotoGallery();
                break;
        }
    }


    public void addNewRowPopup() {
        dialog = new CustomDialog(this, R.layout.add_product_price_qty_popup);

        final EditText priceET = (EditText) dialog.findViewById(R.id.companyNameET);
        final EditText qtyET = (EditText) dialog.findViewById(R.id.addressET);
        final Button saveBT = (Button) dialog.findViewById(R.id.saveBT);
        final Button cancelBT = (Button) dialog.findViewById(R.id.cancelBT);

        dialog.show();


        saveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price = priceET.getText().toString().trim();
                qty = qtyET.getText().toString().trim();

                if (price.equals("")) {
                    Toast.makeText(getApplicationContext(), "Required Price", Toast.LENGTH_SHORT).show();
                } else if (qty.equals("")) {
                    Toast.makeText(getApplicationContext(), "Required Qty", Toast.LENGTH_SHORT).show();
                } else {
                    addSingleRowList.add(new AddProductRowSingleItem(price, qty));
                    recyclerView.setAdapter(addProductSingleRowAdapter);
                    addProductSingleRowAdapter.notifyDataSetChanged();
                    checkFirstDeletIconItem();
                    dialog.dismiss();
                }
            }
        });

        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    @Override
    public void onRemoveSingleItem(AddProductRowSingleItem singleRowList, int position) {
        addSingleRowList.remove(position);
        checkFirstDeletIconItem();
        addProductSingleRowAdapter.notifyDataSetChanged();
    }


    public void checkFirstDeletIconItem() {
        if (addSingleRowList.size() >= 1) {
            multiViewLL.setVisibility(View.GONE);
            // deleteRowIV.setVisibility(View.VISIBLE);
        } else {
            multiViewLL.setVisibility(View.VISIBLE);
            // deleteRowIV.setVisibility(View.GONE);
        }
    }

    private void gotoGallery() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, DeliverezConstants.RESULT_LOAD_IMAGE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2000);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DeliverezConstants.RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            addProductIV.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        } else if (requestCode == 2000 && PermissionCommon.getCameraPer(this)) {
            gotoGallery();
        }
    }


}