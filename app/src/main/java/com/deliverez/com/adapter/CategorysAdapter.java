package com.deliverez.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CategorysAdapter extends RecyclerView.Adapter<CategorysAdapter.ViewHolder> {
    private ArrayList<Category> list;
    private Context mContext;
    boolean isSelected;
    ActionListener actionListener;

    public CategorysAdapter(Context mContext, ArrayList<Category> list) {
        super();
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_single_item, parent, false);
        CategorysAdapter.ViewHolder holder = new CategorysAdapter.ViewHolder(v);

        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Category category=list.get(position);

        if (category.isSelected() == false) {
            holder.frameViewFL.setBackgroundResource(R.drawable.related_item_text_bg);
            holder.selecctedStatusIV.setVisibility(View.GONE);
            holder.tvName.setVisibility(View.VISIBLE);
        }else {
            holder.frameViewFL.setBackgroundResource(R.drawable.shape_selected_bg);
            holder.selecctedStatusIV.setVisibility(View.VISIBLE);
            holder.tvName.setVisibility(View.GONE);
        }


        holder.tvName.setText(category.getGameName());
        if(category.getImage()!=null){
           // Picasso.with(mContext).load(gameName.getImage()).into(holder.gameImgIV);
            holder.imageViewRP.setImageResource(category.getImage());
        }else Picasso.with(mContext).load(R.drawable.default_profile).into(holder.imageViewRP);




        holder.singleViewRL.setTag(position);
        holder.singleViewRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category.isSelected == false) {
                    category.setSelected(true);
                    isSelected = true;
                } else {
                    category.setSelected(false);
                    isSelected = false;
                }
                actionListener.actionAfterSelectCat(category,isSelected);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public
        ImageView imageViewRP,selecctedStatusIV;
        public RelativeLayout singleViewRL;
        FrameLayout frameViewFL;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            imageViewRP = (ImageView) itemView.findViewById(R.id.imageViewRP);
            selecctedStatusIV = (ImageView) itemView.findViewById(R.id.selecctedStatusIV);
            singleViewRL = itemView.findViewById(R.id.singleViewRL);
            frameViewFL = itemView.findViewById(R.id.frameViewFL);
        }
    }

    public interface ActionListener {
        void actionAfterSelectCat(Category category, boolean isSelected);
    }

    public ActionListener getActionListener() {
        return actionListener;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

}
