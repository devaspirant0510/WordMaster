package com.example.wordmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordmaster.R;
import com.example.wordmaster.data.recycler.TestResultItem;

import java.util.ArrayList;

public class TestResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<TestResultItem> list = new ArrayList<>();
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_test_result,parent,false);
        return new TestResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TestResultViewHolder){
            ((TestResultViewHolder) holder).setItem(list.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(TestResultItem item){
        list.add(item);
    }

    public class TestResultViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIndex;
        private TextView tvQuestion;
        private TextView tvAnswer;
        private TextView tvMyAnswer;
        private TextView tvIsCorrect;

        private TestResultViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tv_index);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            tvAnswer = itemView.findViewById(R.id.tv_answer);
            tvMyAnswer = itemView.findViewById(R.id.tv_my_answer);
            tvIsCorrect = itemView.findViewById(R.id.tv_is_correct);
        }

        private void setItem(TestResultItem item){
            tvIndex.setText(String.valueOf(item.getIndex()));
            tvQuestion.setText(item.getQuestion());
            tvAnswer.setText(item.getAnswer());
            tvMyAnswer.setText(item.getMyAnswer());
            tvIsCorrect.setText(item.isCorrect());


        }
    }
}
