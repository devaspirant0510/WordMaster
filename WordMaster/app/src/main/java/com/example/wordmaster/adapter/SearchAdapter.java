package com.example.wordmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordmaster.Define.Define;
import com.example.wordmaster.R;
import com.example.wordmaster.model.recycler.SearchItem;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnClickListener listener = null;
    public interface OnClickListener{
        void onClick(int click,View view);
        void longClick(int click,View view);
    }
    public void setOnCustomOnClickListener(OnClickListener onClickListener){
        this.listener = onClickListener;
    }

    private ArrayList<SearchItem> list = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType== Define.SEARCH_PRIVATE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_search_private,parent,false);
            return new PrivateViewHodler(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_search_public,parent,false);
            return new PublicViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PrivateViewHodler){
            ((PrivateViewHodler) holder).setItem(list.get(position));
        }
        else if (holder instanceof PublicViewHolder){
            ((PublicViewHolder) holder).setItem(list.get(position));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(SearchItem item){
        list.add(item);
    }

    public class PublicViewHolder extends RecyclerView.ViewHolder{
        public TextView tvHost,tvTitle,tvDescription,tvMaxCount;

        public PublicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHost = itemView.findViewById(R.id.tv_search_host);
            tvTitle = itemView.findViewById(R.id.tv_search_title);
            tvMaxCount = itemView.findViewById(R.id.tv_search_max_count);
            tvDescription = itemView.findViewById(R.id.tv_search_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        listener.onClick(pos,v);

                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        listener.longClick(pos,v);
                    }
                    return false;
                }
            });
        }
        public void setItem(SearchItem item){
            tvHost.setText(item.getHost());
            tvTitle.setText(item.getTitle());
            tvDescription.setText(item.getDescription());
            tvMaxCount.setText(String.valueOf(item.getMaxCount()));
        }
    }

    public class PrivateViewHodler extends RecyclerView.ViewHolder {
        public TextView tvHost,tvTitle,tvMaxCount;
        public PrivateViewHodler(@NonNull View itemView) {
            super(itemView);
            tvHost = itemView.findViewById(R.id.tv_search_host);
            tvTitle = itemView.findViewById(R.id.tv_search_title);
            tvMaxCount = itemView.findViewById(R.id.tv_search_max_count);
        }
        public void setItem(SearchItem item){
            tvHost.setText(item.getHost());
            tvTitle.setText(item.getTitle());
            tvMaxCount.setText(String.valueOf(item.getMaxCount()));
        }
    }
}
