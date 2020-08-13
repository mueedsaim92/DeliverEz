package com.deliverez.com.imagePicker.imageActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.deliverez.com.R;
import com.deliverez.com.databinding.GalleryimageactivityBinding;
import com.deliverez.com.imagePicker.imageAdapter.GallerListAdapter;
import com.deliverez.com.imagePicker.imageModel.ImageModel;
import com.deliverez.com.imagePicker.imageTools.GridSpacingItemDecoration;
import com.deliverez.com.imagePicker.imageTools.RecyclerItemClickListener;
import com.deliverez.com.tools.DeliverezConstants;

import java.io.File;
import java.util.ArrayList;

public class GalleryImageActivity extends AppCompatActivity {

    private GallerListAdapter gallerListAdapter;

    private RecyclerView recyclerView;

    private ArrayList<String> pathList = new ArrayList<>();
    private ArrayList<ImageModel> dataAlbum = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GalleryimageactivityBinding binding = DataBindingUtil.setContentView(this, R.layout.galleryimageactivity);
        recyclerView = binding.gallerylist;

        TextView galleryTextView = binding.galleryTextView;

        intializaRecyclerView();

        findViewById(R.id.gallerybackbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void intializaRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(GalleryImageActivity.this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        int spanCount = 2;
        int spacing = dpToPx(5);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, false));
        gallerListAdapter = new GallerListAdapter(GalleryImageActivity.this, dataAlbum);
        recyclerView.setAdapter(gallerListAdapter);

        new GetItemAlbum().execute();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(GalleryImageActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageModel imageModel = dataAlbum.get(position);
                String imageFolderName = imageModel.getPathFolder();
                Intent intent = new Intent(GalleryImageActivity.this, GallerySubItemActivity.class);
                intent.putExtra("foldername", imageFolderName);
                intent.putExtra("header", imageModel.getName());
                startActivityForResult(intent, 600);
            }
        }));
    }


    @SuppressLint("StaticFieldLeak")
    private class GetItemAlbum extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            Cursor cursor = getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]
                            {"_data", "bucket_display_name"}, null, null, null);
            if (cursor != null) {
                int column_index_data = cursor.getColumnIndexOrThrow("_data");
                while (cursor.moveToNext()) {
                    String pathFile = cursor.getString(column_index_data);
                    File file = new File(pathFile);
                    if (file.exists()) {
                        boolean check = checkFile(file);
                        if (!Check(file.getParent(), pathList) && check) {
                            pathList.add(file.getParent());
                            dataAlbum.add(new ImageModel(file.getParentFile().getName(), pathFile, file.getParent(),""));
                        }
                    }
                }
                cursor.close();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            gallerListAdapter.notifyDataSetChanged();
        }
    }


    private boolean Check(String a, ArrayList<String> list) {
        return !list.isEmpty() && list.contains(a);
    }


    private boolean checkFile(File file) {
        if (file == null) {
            return false;
        }
        if (!file.isFile()) {
            return true;
        }
        String name = file.getName();
        if (name.startsWith(".") || file.length() == 0) {
            return false;
        }
        boolean isCheck = false;
        for (int k = 0; k < DeliverezConstants.FORMAT_IMAGE.size(); k++) {
            if (name.endsWith(DeliverezConstants.FORMAT_IMAGE.get(k))) {
                isCheck = true;
                break;
            }
        }
        return isCheck;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 600) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
