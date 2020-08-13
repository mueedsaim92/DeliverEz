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

public class AddServiceProductsActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = AddServiceProductsActivity.class.getSimpleName();
    Toolbar mToolbar;

    EditText descriptionET;
    ImageView addProductIV;
    Spinner serviceSpinner;

    ArrayList<String> serviceSpinnerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_product);
        customToolbar("Add Service Product");

        addProductIV = findViewById(R.id.addProductIV);
        descriptionET = findViewById(R.id.descriptionET);
        serviceSpinner = findViewById(R.id.serviceSpinner);

        addProductIV.setOnClickListener(this);

         final String serviceNameList[] = {"Select Type","Carpenter","Electrician","Plumber","Mounting Service","Painters","Tiles","Construction"};
        serviceSpinnerList = new ArrayList<>();
       for(int i=0; i<serviceNameList.length; i++){
           serviceSpinnerList.add(serviceNameList[i]);
       }

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getApplicationContext(), R.layout.spinner_rows, serviceSpinnerList);
        serviceSpinner.setAdapter(adapter);

        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //customer_id = "0";
                } else {
                    Toast.makeText(AddServiceProductsActivity.this, serviceSpinnerList.get(position)+" selected", Toast.LENGTH_SHORT).show();
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
                break;

            case R.id.addProductIV:
                gotoGallery();
                break;
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