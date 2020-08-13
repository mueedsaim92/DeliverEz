package com.deliverez.com.imagePicker.imageAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.deliverez.com.R;
import com.deliverez.com.databinding.SubgalleryitemchildBinding;
import com.deliverez.com.imagePicker.imageModel.ImageModel;

import java.io.File;
import java.util.ArrayList;

import static com.deliverez.com.tools.DeliverezConstants.selctedImagesGallery;


public class GallerySubItemAdapter extends RecyclerView.Adapter<GallerySubItemAdapter.ViewAgency> {

    private Context context;
    private ArrayList<ImageModel> arrayList;
    private SubItemClickListener subItemClickListener;


    public GallerySubItemAdapter(Context context, ArrayList<ImageModel> arrayList,
                                 SubItemClickListener subItemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.subItemClickListener = subItemClickListener;
    }

    @NonNull
    @Override
    public GallerySubItemAdapter.ViewAgency onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SubgalleryitemchildBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.subgalleryitemchild, viewGroup, false);
        return new GallerySubItemAdapter.ViewAgency(mBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull final GallerySubItemAdapter.ViewAgency holder, final int i) {

        ImageModel item = arrayList.get(i);

        Glide.with(context).load(new File(item.getPathFile())).into(holder.mBinding.albumItemfileimage);

        for (int k = 0; k < selctedImagesGallery.size(); k++) {
            if (selctedImagesGallery.get(k).getPathFile().equalsIgnoreCase(item.getPathFile())) {
                holder.mBinding.albumItemTick.setVisibility(View.VISIBLE);
            } else {
                holder.mBinding.albumItemTick.setVisibility(View.GONE);
            }
        }

        holder.mBinding.albumItemFileContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mBinding.albumItemTick.getVisibility() == View.VISIBLE) {
                    holder.mBinding.albumItemTick.setVisibility(View.GONE);

                    String path = arrayList.get(i).getPathFile();

                    if (selctedImagesGallery.size() > 0) {
                        for (int k = 0; k < selctedImagesGallery.size(); k++) {
                            String filePath = selctedImagesGallery.get(k).getPathFile();
                            if (filePath.equalsIgnoreCase(path)) {
                                selctedImagesGallery.remove(k);
                            }
                        }
                    }
                } else {
                    holder.mBinding.albumItemTick.setVisibility(View.VISIBLE);
                    String path = arrayList.get(i).getPathFile();
                    ImageModel imageModel =new ImageModel(arrayList.get(i).getName(), arrayList.get(i).getPathFile(), arrayList.get(i).getPathFile(),"");
                    selctedImagesGallery.add(imageModel);
                }
                subItemClickListener.performaction();
            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewAgency extends RecyclerView.ViewHolder {
        private final SubgalleryitemchildBinding mBinding;

        private ViewAgency(SubgalleryitemchildBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }

    public interface SubItemClickListener {

        void performaction();


    }

}