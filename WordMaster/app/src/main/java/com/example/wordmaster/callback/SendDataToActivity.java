package com.example.wordmaster.callback;

import com.example.wordmaster.model.recycler.DictionaryWordItem;

import java.util.ArrayList;

public interface SendDataToActivity {
    void sendDictData(int pos,String title,String option,String hashTag,int count);
    void sendTestingData(int pos,int maxCount, String limitTime, int rgTestType, int rgTestTimeOption,String host,String title);
    void sendTestResult(int maxCount, int trueCount, String[] myAnswer, String[] answer, ArrayList<DictionaryWordItem> list);
}
