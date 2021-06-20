package com.example.wordmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordmaster.R;
import com.example.wordmaster.model.recycler.OnlineTestItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author seungho
 * @since 2021-06-20
 * project com.example.wordmaster.adapter
 * class OnlineTestAdapter.java
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description 온라인 테스트 리사이클러뷰 어댑터
 **/
public class OnlineTestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<OnlineTestItem> list = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_online_test_private,parent,false);
        return new PublicTestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class PublicTestViewHolder extends RecyclerView.ViewHolder{
        Button btnJoin,btnMoreInfo;
        TextView tvTitle,tvUserName,tvDescription;

        public PublicTestViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            
        }
    }
    class PrivateTestViewHodler extends RecyclerView.ViewHolder{

        public PrivateTestViewHodler(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
