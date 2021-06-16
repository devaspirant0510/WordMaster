package com.example.wordmaster.callback;

import android.os.Bundle;

import com.example.wordmaster.model.recycler.DictionaryListItem;
import com.example.wordmaster.model.recycler.DictionaryWordItem;

import java.util.ArrayList;

public interface SendDataToActivity {
    void sendDictData(DictionaryListItem item);
    @Deprecated
    void sendTestingData(int pos,int maxCount, String limitTime, int rgTestType, int rgTestTimeOption,String host,String title);

    void sendTestingData(String userid,String roomKey,int maxCount,int rgTestType,String host,String title);

    void sendTestResult(int maxCount, int trueCount, String[] myAnswer, String[] answer, ArrayList<DictionaryWordItem> list);

    void sendSearchInfoData(Bundle bundle);
}
