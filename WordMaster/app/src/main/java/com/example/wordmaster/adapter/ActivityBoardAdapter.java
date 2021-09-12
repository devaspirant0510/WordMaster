package com.example.wordmaster.adapter;

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
    private final ArrayList<Integer> list = new ArrayList<>();
    private static final String TAG = "ActivityBoardAdapter";

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout_activity_board_item, parent, false);
        return new MyActivityAmount(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyActivityAmount) {
            ((MyActivityAmount) holder).setItem(list.get(position));
        }

    }

    public void adapterClear() {
        list.clear();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(int item) {
        list.add(item);
    }

    public void updateItem(int idx,int item){
        list.set(idx,item);
    }

    private static class MyActivityAmount extends RecyclerView.ViewHolder {
        View btnNone, btnBest, btnGood, btnNormal, btnBad, btnWorst, btnEmpty;
        private final ArrayList<View> viewList = new ArrayList<>();


        public MyActivityAmount(@NonNull @NotNull View itemView) {
            super(itemView);
            btnNone = itemView.findViewById(R.id.v_activity_none);
            btnBest = itemView.findViewById(R.id.v_activity_best);
            btnGood = itemView.findViewById(R.id.v_activity_good);
            btnNormal = itemView.findViewById(R.id.v_activity_normal);
            btnBad = itemView.findViewById(R.id.v_activity_bad);
            btnWorst = itemView.findViewById(R.id.v_activity_worst);
            btnEmpty = itemView.findViewById(R.id.v_activity_empty);

        }

        private void checkItemType(int pos) {
            for (int i = 0; i < viewList.size(); i++) {
                if (i == pos) {
                    viewList.get(i).setVisibility(View.VISIBLE);
                } else {
                    viewList.get(i).setVisibility(View.GONE);
                }

            }
        }

        private void setItem(int point) {
            viewList.add(btnNone);
            viewList.add(btnBest);
            viewList.add(btnGood);
            viewList.add(btnNormal);
            viewList.add(btnBad);
            viewList.add(btnWorst);
            viewList.add(btnEmpty);
            switch (point) {
                case 0:
                    checkItemType(0);
                    break;
                case 1:
                    checkItemType(1);
                    break;
                case 2:
                    checkItemType(2);
                    break;
                case 3:
                    checkItemType(3);
                    break;
                case 4:
                    checkItemType(4);
                    break;
                case 5:
                    checkItemType(5);
                    break;
                case 6:
                    checkItemType(6);
                    break;
                default:
                    break;
            }

        }
    }
}
