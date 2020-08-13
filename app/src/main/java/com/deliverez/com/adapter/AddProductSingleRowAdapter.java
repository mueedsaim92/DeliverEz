package com.deliverez.com.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.deliverez.com.R;
import com.deliverez.com.models.AddProductRowSingleItem;

import java.util.List;

public class AddProductSingleRowAdapter extends RecyclerView.Adapter<AddProductSingleRowAdapter.ViewHolder> {

    private Context context;
    private List<AddProductRowSingleItem> rowSingleItem;

    TourPlannerActionListener tourPlannerActionListener;

    public AddProductSingleRowAdapter(Context context, List<AddProductRowSingleItem> rowSingleItem) {
        this.context = context;
        this.rowSingleItem = rowSingleItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_product_row_single_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AddProductRowSingleItem student = this.rowSingleItem.get(position);
        holder.priceET.setText(student.getCompanyName());
        holder.qtyET.setText(student.getAddress());

        holder.deleteRowIV.setTag(student);
        holder.deleteRowIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tourPlannerActionListener.onRemoveSingleItem((AddProductRowSingleItem) v.getTag(), position);
                if (rowSingleItem.size() >= 1) {
                    holder.deleteRowIV.setVisibility(View.VISIBLE);
                } else holder.deleteRowIV.setVisibility(View.GONE);
            }
        });


        if (rowSingleItem.size()>1) {
            holder.deleteRowIV.setVisibility(View.VISIBLE);
        } else {
            holder.deleteRowIV.setVisibility(View.GONE);
        }


    }

    public void updateList(List<AddProductRowSingleItem> student) {
        student = student;
        notifyDataSetChanged();
    }

    public void addItem(int position, AddProductRowSingleItem stud) {
        rowSingleItem.add(position, stud);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return rowSingleItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView priceET, qtyET;
        ImageView deleteRowIV;

        public ViewHolder(View itemView) {
            super(itemView);

            priceET = (TextView) itemView.findViewById(R.id.priceET);
            qtyET = (TextView) itemView.findViewById(R.id.qtyET);
            deleteRowIV = (ImageView) itemView.findViewById(R.id.deleteRowIV);
        }
    }


    public interface TourPlannerActionListener {
        public void onRemoveSingleItem(AddProductRowSingleItem student, int position);
    }


    public TourPlannerActionListener getTourPlannerActionListener() {
        return tourPlannerActionListener;
    }

    public void setTourPlannerActionListener(TourPlannerActionListener tourPlannerActionListener) {
        this.tourPlannerActionListener = tourPlannerActionListener;
    }

}
