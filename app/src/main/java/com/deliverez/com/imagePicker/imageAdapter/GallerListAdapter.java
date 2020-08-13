package com.deliverez.com.imagePicker.imageAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deliverez.com.R;
import com.deliverez.com.databinding.GallerylistitemBinding;
import com.deliverez.com.imagePicker.imageModel.ImageModel;


import java.io.File;
import java.util.ArrayList;

public class GallerListAdapter extends RecyclerView.Adapter<GallerListAdapter.ViewAgency> {

    private Context context;
    private ArrayList<ImageModel> arrayList;

    public GallerListAdapter(Context context, ArrayList<ImageModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public GallerListAdapter.ViewAgency onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        GallerylistitemBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.gallerylistitem, viewGroup, false);
        return new GallerListAdapter.ViewAgency(mBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull final GallerListAdapter.ViewAgency viewAgency, int i) {
        ImageModel item=arrayList.get(i);
        Glide.with(context).load(new File(item.getPathFile())).into(viewAgency.mBinding.albumfileimage);

        viewAgency.mBinding.albumfilename.setText(item.getName());
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewAgency extends RecyclerView.ViewHolder {
        private final GallerylistitemBinding mBinding;

        private ViewAgency(GallerylistitemBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}