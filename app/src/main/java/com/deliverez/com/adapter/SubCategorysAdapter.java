package com.deliverez.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.deliverez.com.R;
import com.deliverez.com.models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by new on 14-11-2017.
 */

public class SubCategorysAdapter extends RecyclerView.Adapter<SubCategorysAdapter.ViewHolder> {
    private ArrayList<Category> list;
    private Context mContext;
    boolean isSelected;
    SubCategoryListener actionListener;

    public SubCategorysAdapter(Context mContext, ArrayList<Category> list) {
        super();
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_single_item, parent, false);
        SubCategorysAdapter.ViewHolder holder = new SubCategorysAdapter.ViewHolder(v);

        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Category category=list.get(position);

        holder.nameTV.setText(category.getGameName());
        if(category.getImage()!=null){
           // Picasso.with(mContext).load(gameName.getImage()).into(holder.gameImgIV);
            holder.productImgIV.setImageResource(category.getImage());
        }else Picasso.with(mContext).load(R.drawable.default_profile).into(holder.productImgIV);

        holder.deleteBT.setTag(position);
        holder.deleteBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onDeleteSubCat(category,isSelected,position);
            }
        });

        holder.editBT.setTag(position);
        holder.editBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onEditSubCat(category,isSelected,position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTV;
        public ImageView productImgIV;
        public RelativeLayout singleViewRL;
        FrameLayout frameViewFL;
        Button editBT,deleteBT;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            productImgIV = (ImageView) itemView.findViewById(R.id.productImgIV);
            singleViewRL = itemView.findViewById(R.id.singleViewRL);
            frameViewFL = itemView.findViewById(R.id.frameViewFL);
            editBT = itemView.findViewById(R.id.editBT);
            deleteBT = itemView.findViewById(R.id.deleteBT);
        }
    }

    public interface SubCategoryListener {
        void onEditSubCat(Category category, boolean isSelected, int position);
        void onDeleteSubCat(Category category, boolean isSelected,int position);
    }

    public SubCategoryListener getActionListener() {
        return actionListener;
    }

    public void setActionListener(SubCategoryListener actionListener) {
        this.actionListener = actionListener;
    }

}
