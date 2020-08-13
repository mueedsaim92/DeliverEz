package com.deliverez.com.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deliverez.com.R;
import com.deliverez.com.adapter.CategorysAdapter;
import com.deliverez.com.models.Category;
import com.deliverez.com.models.GameName;
import com.deliverez.com.tools.DeliverezConstants;
import com.deliverez.com.tools.Methods;
import com.deliverez.com.tools.PermissionCommon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, CategorysAdapter.ActionListener {
    private static final String TAG = EditProfileActivity.class.getSimpleName();

    Toolbar toolbar;

    EditText gameNameET, gameShortNameET;
    TextView shopOpeningTimeTV,shopClosingTimeTV;
    CircleImageView defaultImageIV;
    RelativeLayout defaultImageRR;
    Button saveTV;
    private static int RESULT_LOAD_IMAGE = 1;
    CategorysAdapter categoryAdapter;
    RecyclerView categoryRV;

    private final String listNames[] = {"Electronics","Grocery",  "Vegetables", "Fruits","Dairy","Sweets"};
    private Integer imgs[]={R.drawable.electronics,R.drawable.grocery,R.drawable.vegetables,R.drawable.fruits,
            R.drawable.dairy,R.drawable.sweet};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        shopOpeningTimeTV = findViewById(R.id.shopOpeningTimeTV);
        shopClosingTimeTV = findViewById(R.id.shopClosingTimeTV);
        gameNameET = (EditText) findViewById(R.id.gameNameET);
        gameShortNameET = (EditText) findViewById(R.id.gameShortNameET);
        defaultImageIV = (CircleImageView) findViewById(R.id.defaultImageIV);
        defaultImageRR = findViewById(R.id.defaultImageRR);
        categoryRV = findViewById(R.id.categoryRV);
        saveTV = (Button) findViewById(R.id.saveBT);

        shopOpeningTimeTV.setOnClickListener(this);
        shopClosingTimeTV.setOnClickListener(this);
        defaultImageRR.setOnClickListener(this);
        saveTV.setOnClickListener(this);



        customToolbar();

        ArrayList<Category> list = prepareData();
        initAdapter(list);

    }


    public void customToolbar() {
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        this.toolbar.setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.defaultImageRR:
                gotoGallery();
                break;

            case R.id.shopOpeningTimeTV:
                timePicker(shopOpeningTimeTV);
                break;

            case R.id.shopClosingTimeTV:
                timePicker(shopClosingTimeTV);
                break;


            case R.id.saveBT:
                vendorPaymentPopup("As vendor you have need to do payment 1000 rs");
                /*if (gameNameET.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "" + "Enter Game Name", Toast.LENGTH_SHORT).show();
                } else if (gameShortNameET.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "" + "Enter Game Short Name", Toast.LENGTH_SHORT).show();
                } else if (gameShortNameET.getText().toString().length() > 3) {
                    Toast.makeText(getApplicationContext(), "" + "Short name will be max 3 character", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "" + "SignUp success", Toast.LENGTH_SHORT).show();
                }*/
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

            defaultImageIV.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        } else if (requestCode == 2000 && PermissionCommon.getCameraPer(this)) {
            gotoGallery();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void vendorPaymentPopup(String massage) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.vender_payment_popup);

        TextView msgTV = dialog.findViewById(R.id.msgTV);
        Button cancelBT = dialog.findViewById(R.id.cancelBT);
        Button payNowBT = dialog.findViewById(R.id.payNowBT);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        msgTV.setText(massage);

        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        payNowBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }


    private void timePicker(final TextView timeTV){
        TimePickerDialog mTimePicker;
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(EditProfileActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String selectedTime = selectedHour + ":" + selectedMinute;
                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                Date date = null;
                try {
                    date = fmt.parse(selectedTime );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm a");
                String formattedTime=fmtOut.format(date).toUpperCase();

                timeTV.setText(formattedTime);
                timeTV.setTextColor(getResources().getColor(R.color.textColor));

                String hvrs="",minut="";
                if(selectedHour < 10){
                    hvrs="0"+selectedHour;
                }else hvrs=String.valueOf(selectedHour);

                if(selectedMinute < 10){
                    minut="0"+selectedMinute;
                }else minut=String.valueOf(selectedMinute);

               // time = hvrs+":"+minut+":00";
                Log.d("<<<<<","====> "+hvrs+":"+minut+":00");
            }
        }, hour, minute, false);//No 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    public void initAdapter(ArrayList<Category> list) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,  RecyclerView.HORIZONTAL, false);
        categoryRV.setLayoutManager(mLayoutManager);
        categoryAdapter = new CategorysAdapter(this, list);
        categoryRV.setAdapter(categoryAdapter);
        categoryAdapter.setActionListener(this);
    }


    private ArrayList<Category> prepareData() {
        ArrayList<Category> arrayList = new ArrayList<>();
        for (int i = 0; i < listNames.length; i++) {
            Category category = new Category();
            category.setGameName(listNames[i]);
            category.setImage(imgs[i]);
            // add into list
            arrayList.add(category);
        }
        return arrayList;
    }


    @Override
    public void actionAfterSelectCat(Category category, boolean isSelected) {
        categoryAdapter.notifyDataSetChanged();
    }

}