package com.example.wordmaster.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordmaster.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author seungho
 * @since 2021-06-13
 * project com.example.wordmaster.adapter
 * class ActivityBoardAdapter.java
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description ProfileFragment 에서 내 활동기록을 보여줄 리사이클러뷰 그리드
 **/
public class ActivityBoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Integer> list = new ArrayList<>();
    private static String TAG = "ActivityBoardAdapter";

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout_activity_board_item, parent, false);
        return new MyActivityAmount(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyActivityAmount) {
            ((MyActivityAmount) holder).setItem(list.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(int item) {
        list.add(item);
    }

    private static class MyActivityAmount extends RecyclerView.ViewHolder {
        View btnNone;
        View btnBest;
        View btnGood;
        View btnNormal;
        View btnBad;
        View btnWorst;


        public MyActivityAmount(@NonNull @NotNull View itemView) {
            super(itemView);
            btnNone = itemView.findViewById(R.id.v_activity_none);
            btnNone.setVisibility(View.GONE);
            btnBest = itemView.findViewById(R.id.v_activity_best);
            btnBest.setVisibility(View.VISIBLE);
            btnGood = itemView.findViewById(R.id.v_activity_good);
            btnGood.setVisibility(View.GONE);
            btnNormal = itemView.findViewById(R.id.v_activity_normal);
            btnNormal.setVisibility(View.GONE);
            btnBad = itemView.findViewById(R.id.v_activity_bad);
            btnBad.setVisibility(View.GONE);
            btnWorst = itemView.findViewById(R.id.v_activity_worst);
            btnWorst.setVisibility(View.GONE);

        }

        private void setItem(int point){

        }
    }
}
