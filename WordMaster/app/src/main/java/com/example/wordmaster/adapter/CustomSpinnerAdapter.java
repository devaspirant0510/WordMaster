package com.example.wordmaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordmaster.R;
import com.example.wordmaster.model.etc.SpinnerItem;

import org.jetbrains.annotations.NotNull;

/**
 * @author seungho
 * @since 2021-06-23
 * project com.example.wordmaster.adapter
 * class CustomSpinnerAdapter.java
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description
 **/
public class CustomSpinnerAdapter extends ArrayAdapter<SpinnerItem> {


    public CustomSpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView tvTitle,tvCount,tvDescription;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.spinner_custom_item,parent,false);
        }
        tvTitle = convertView.findViewById(R.id.tv_spinner_title);
        tvCount = convertView.findViewById(R.id.tv_spinner_count);
        tvDescription = convertView.findViewById(R.id.tv_spinner_description);
        SpinnerItem item =getItem(position);
        tvTitle.setText(item.getTitle());
        tvCount.setText(String.valueOf(item.getMaxCount()));
        tvDescription.setText(item.getDescription());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView tvTitle,tvCount,tvDescription;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.spinner_custom_item,parent,false);
        }
        tvTitle = convertView.findViewById(R.id.tv_spinner_title);
        tvCount = convertView.findViewById(R.id.tv_spinner_count);
        tvDescription = convertView.findViewById(R.id.tv_spinner_description);
        SpinnerItem item =getItem(position);
        tvTitle.setText(item.getTitle());
        tvCount.setText(String.valueOf(item.getMaxCount()));
        tvDescription.setText(item.getDescription());

        return convertView;
    }
}
