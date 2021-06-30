package com.example.wordmaster.adapter;

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
import com.example.wordmaster.model.recycler.OnlineTestMemberItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author seungho
 * @since 2021-06-30
 * class OnlineTestMemberAdapter.java
 * project WordMaster
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description
 **/
public class OnlineTestMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<OnlineTestMemberItem> list = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_online_test_member,parent,false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MemberViewHolder){
            ((MemberViewHolder) holder).setItem(list.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void addItem(OnlineTestMemberItem item){
        list.add(item);
    }

    class MemberViewHolder extends RecyclerView.ViewHolder{
        ImageView ivMemberProfile;
        TextView tvMember,tvMemberComment;


        public MemberViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivMemberProfile = itemView.findViewById(R.id.iv_member_profile);
            tvMember =itemView.findViewById(R.id.tv_member_name);
            tvMemberComment = itemView.findViewById(R.id.tv_member_comment);
            ivMemberProfile.setBackground(new ShapeDrawable(new OvalShape()));
            ivMemberProfile.setClipToOutline(true);


        }
        private void setItem(OnlineTestMemberItem item){
            Glide.with(itemView.getContext()).load(item.getProfileURL()).into(ivMemberProfile);
            tvMember.setText(item.getUserName());
            tvMemberComment.setText(item.getMemberComment());

        }
    }
}
