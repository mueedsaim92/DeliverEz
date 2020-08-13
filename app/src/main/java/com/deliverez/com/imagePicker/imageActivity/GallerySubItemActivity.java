package com.deliverez.com.imagePicker.imageActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.deliverez.com.R;
import com.deliverez.com.databinding.GallerysubitemBinding;
import com.deliverez.com.imagePicker.imageAdapter.GallerySubItemAdapter;
import com.deliverez.com.imagePicker.imageModel.ImageModel;
import com.deliverez.com.imagePicker.imageTools.GridSpacingItemDecoration;
import com.deliverez.com.tools.DeliverezConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.deliverez.com.tools.DeliverezConstants.selctedImagesGallery;

public class GallerySubItemActivity extends AppCompatActivity {

    private GallerySubItemAdapter gallerySubItemAdapter;
    private ArrayList<ImageModel> dataListPhoto = new ArrayList<>();
    int counter = 0;

    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GallerysubitemBinding binding = DataBindingUtil.setContentView(this, R.layout.gallerysubitem);
        final String folderName = getIntent().getStringExtra("foldername");
        String header = getIntent().getStringExtra("header");


        selctedImagesGallery.clear();


        TextView headersubitem = findViewById(R.id.headersubitem);
        TextView imageCount = findViewById(R.id.imageCount);

        checkCounter();


        imageCount.setText(String.valueOf(selctedImagesGallery.size()));

        headersubitem.setText(header);
        RecyclerView recyclerView = binding.albumItemlist;
        recyclerView.setLayoutManager(new GridLayoutManager(GallerySubItemActivity.this, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int spanCount = 3;
        int spacing = dpToPx(5);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, false));
        gallerySubItemAdapter = new GallerySubItemAdapter(GallerySubItemActivity.this, dataListPhoto, new GallerySubItemAdapter.SubItemClickListener() {
            @Override
            public void performaction() {
                checkCounter();
            }
        });
        recyclerView.setAdapter(gallerySubItemAdapter);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(folderName);
                if (file.isDirectory()) {
                    for (File fileTmp : file.listFiles()) {
                        if (fileTmp.exists()) {
                            boolean check = checkFile(fileTmp);
                            if (!fileTmp.isDirectory() && check) {
                                dataListPhoto.add(new ImageModel(fileTmp.getName(), fileTmp.getAbsolutePath(), fileTmp.getAbsolutePath(),""));
                            }
                        }
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Collections.sort(dataListPhoto, new Comparator<ImageModel>() {
                                @Override
                                public int compare(ImageModel item, ImageModel t1) {
                                    File fileI = new File(item.getPathFolder());
                                    File fileJ = new File(t1.getPathFolder());
                                    if (fileI.lastModified() > fileJ.lastModified()) {
                                        return -1;
                                    } else if (fileI.lastModified() < fileJ.lastModified()) {
                                        return 1;
                                    } else {
                                        return 0;
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                        gallerySubItemAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        thread.start();

        TextView doneButton = binding.doneButton;

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.gallerysubbackbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void checkCounter() {
        counter = 0;
        for (int k = 0; k < selctedImagesGallery.size(); k++) {
            if (!selctedImagesGallery.get(k).getPathFile().startsWith(".")) {
                counter++;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

}
