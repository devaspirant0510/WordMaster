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
import com.example.wordmaster.data.recycler.DictionaryWordItem;

import java.util.ArrayList;

public class DictionaryInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<DictionaryWordItem> wordList = new ArrayList<>();
    private Context context;

    public DictionaryInfoAdapter(Context context){
        this.context = context;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.layout_item_dictionary_word,parent,false);
        return new WordItem(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WordItem){
            ((WordItem) holder).setItem(wordList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public void addItem(DictionaryWordItem item){
        wordList.add(item);
    }

    public class WordItem extends RecyclerView.ViewHolder {
        TextView englishText;
        TextView koreanText;
        public WordItem(@NonNull View itemView) {
            super(itemView);
            englishText = itemView.findViewById(R.id.word_eng);
            koreanText = itemView.findViewById(R.id.word_kor);
        }
        public void setItem(DictionaryWordItem item){
            Log.e("s", "setItem: "+item.getEng() );
            koreanText.setText(item.getKor());
            englishText.setText(item.getEng());

        }
    }
}
