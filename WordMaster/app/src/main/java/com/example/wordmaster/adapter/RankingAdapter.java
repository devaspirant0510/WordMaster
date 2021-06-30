package com.example.wordmaster.adapter;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wordmaster.R;
import com.example.wordmaster.model.recycler.RankingItem;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RankingItem> list = new ArrayList<>();
    private Context context;
    public RankingAdapter(Context context){
        this.context = context;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.layout_item_ranking,parent,false);
        return new RankingViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RankingViewHolder){
            ((RankingViewHolder) holder).setItem(position);

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(RankingItem item){
        list.add(item);
    }
    public class RankingViewHolder extends RecyclerView.ViewHolder{
        private TextView user;
        private TextView ranking;
        private TextView id;
        private TextView message;
        private ImageView imageView;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.tv_user_name);
            ranking = itemView.findViewById(R.id.tv_ranking);
            id = itemView.findViewById(R.id.tv_user_id);
            message = itemView.findViewById(R.id.tv_user_message);
            imageView = itemView.findViewById(R.id.iv_profile);
            imageView.setBackground(new ShapeDrawable(new OvalShape()));
            imageView.setClipToOutline(true);

        }
        public void setItem(int position){
            RankingItem item = list.get(position);
            user.setText(item.getUser());
            ranking.setText(String.valueOf(item.getRanking()));
            id.setText(item.getId());
            message.setText(item.getMessage());
            Glide.with(itemView.getContext()).load(item.getImageView()).into(imageView);


        }
    }
}
