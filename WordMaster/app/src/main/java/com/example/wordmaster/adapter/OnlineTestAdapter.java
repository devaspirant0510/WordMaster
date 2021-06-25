package com.example.wordmaster.adapter;

import android.content.Context;
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
    private Context context;
    private onClickListener onClickListener = null;
    public void setOnClickListener(onClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
    public interface onClickListener{
        void onClickViewMore(int pos);
        void onClickJoin(int pos);
    }
    public OnlineTestAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_online_test_private,parent,false);
        return new PublicTestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PublicTestViewHolder){
            ((PublicTestViewHolder) holder).setItem(list.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(OnlineTestItem item){
        list.add(item);
    }

    class PublicTestViewHolder extends RecyclerView.ViewHolder{
        Button btnJoin,btnMoreInfo;
        TextView tvTitle,tvUserName,tvDescription,tvOption,tvMaxUser;

        public PublicTestViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            btnJoin = itemView.findViewById(R.id.btn_online_test_join);
            btnMoreInfo = itemView.findViewById(R.id.btn_online_test_more);
            tvTitle = itemView.findViewById(R.id.tv_online_test_title);
            tvUserName = itemView.findViewById(R.id.tv_online_test_host);
            tvDescription = itemView.findViewById(R.id.tv_online_test_description);
            tvOption = itemView.findViewById(R.id.tv_online_test_option);
            tvMaxUser = itemView.findViewById(R.id.tv_max_user);

            int position = getBindingAdapterPosition();
            btnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickJoin(position);
                }
            });
            btnMoreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickViewMore(position);

                }
            });
        }
        private void setItem(OnlineTestItem item){
            tvTitle.setText(item.getTitle());
            tvOption.setText(item.getOption());
            tvDescription.setText(item.getDescription());
            tvUserName.setText(item.getHost());
            tvMaxUser.setText(String.valueOf(item.getMaxUser()));

        }
    }
    class PrivateTestViewHodler extends RecyclerView.ViewHolder{

        public PrivateTestViewHodler(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
