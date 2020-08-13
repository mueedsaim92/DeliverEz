package com.deliverez.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.deliverez.com.R;
import com.deliverez.com.models.GameName;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by new on 14-11-2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {


    private ArrayList<GameName> list;
    private Context mContext;
    String date;
    String slipNumber;
    ActionListener actionListener;

    public ProductsAdapter(Context mContext, ArrayList<GameName> list) {
        super();
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_single_item, parent, false);
        ProductsAdapter.ViewHolder holder = new ProductsAdapter.ViewHolder(v);

        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final GameName gameName=list.get(position);

        holder.tvName.setText(gameName.getGameName());

        if(gameName.getImage()!=null){
           // Picasso.with(mContext).load(gameName.getImage()).into(holder.gameImgIV);
            holder.imageViewRP.setImageResource(gameName.getImage());
        }else Picasso.with(mContext).load(R.drawable.default_profile).into(holder.imageViewRP);


        holder.singleViewLL.setTag(position);
        holder.singleViewLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.actionAfterSelectGame(gameName);
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
        ImageView imageViewRP;
        public RelativeLayout singleViewLL;


        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            imageViewRP = (ImageView) itemView.findViewById(R.id.imageViewRP);
            singleViewLL = itemView.findViewById(R.id.singleViewLL);
        }
    }

    public interface ActionListener {
        void actionAfterSelectGame(GameName gameName);
    }

    public ActionListener getActionListener() {
        return actionListener;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

}
