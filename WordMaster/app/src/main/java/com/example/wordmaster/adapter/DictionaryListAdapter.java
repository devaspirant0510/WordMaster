package com.example.wordmaster.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordmaster.R;
import com.example.wordmaster.data.DictionaryListItem;

import java.util.ArrayList;

public class DictionaryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<DictionaryListItem> dictList = new ArrayList<>();
    private Context context;

    public DictionaryListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType==1){
            View view = inflater.inflate(R.layout.layout_item_dictionary_list,parent,false);
            return new DictionaryItemHolder(view);

        }
        else{
            View view = inflater.inflate(R.layout.dictionary_list_item,parent,false);
            return new DefaultItem(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DictionaryItemHolder){
            Log.e("a", "onBindViewHolder: " );
            //((DictionaryItemHolder) holder).dictionaryDescription.setText("A");
            ((DictionaryItemHolder) holder).setItem(dictList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dictList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return dictList.size();
    }
    public void addItem(DictionaryListItem item){
        dictList.add(item);
    }
    public class DictionaryItemHolder extends RecyclerView.ViewHolder {
        TextView dictionaryTitle;
        TextView dictionaryMaxCount;
        TextView dictionaryDescription;
        TextView dictionaryHost;

        public DictionaryItemHolder(@NonNull View itemView) {
            super(itemView);
            Log.e("d", "DictionaryItemHolder: " );
            dictionaryTitle = itemView.findViewById(R.id.dict_title);
            dictionaryMaxCount = itemView.findViewById(R.id.dict_max_count);
            dictionaryDescription = itemView.findViewById(R.id.dict_desc);
            dictionaryHost = itemView.findViewById(R.id.dict_host);
        }
        public void setItem(DictionaryListItem item){
            dictionaryTitle.setText(item.getDictionaryTitle());
            dictionaryMaxCount.setText(item.getDictionaryMaxCount());
            dictionaryDescription.setText(item.getDictionaryDescription());
            dictionaryHost.setText(item.getDictionaryHost());
        }
    }
    public class DefaultItem extends RecyclerView.ViewHolder {
        public DefaultItem(@NonNull View itemView) {
            super(itemView);
        }
    }
}
