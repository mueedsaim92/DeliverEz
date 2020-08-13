package com.deliverez.com.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deliverez.com.R;

import java.util.ArrayList;

/**
 * Created by new on 31-10-2017.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> data;
    public Resources res;
    String tempValues=null;
    LayoutInflater inflater;

    public CustomSpinnerAdapter(Context mContext, int textViewResourceId, ArrayList objects) {
        super(mContext, textViewResourceId, objects);
        mContext = mContext;
        data     = objects;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_rows, parent, false);

        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = data.get(position);

        TextView txt_spinner_item = (TextView)row.findViewById(R.id.txt_spinner_item);

        if(position==0){
            // Default selected Spinner item
            txt_spinner_item.setText(tempValues);

        }
        else {
            // Set values for spinner each row
            txt_spinner_item.setText(tempValues);

        }

        return row;
    }
}
