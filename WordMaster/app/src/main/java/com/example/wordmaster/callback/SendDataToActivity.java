package com.example.wordmaster.callback;

import android.os.Bundle;

import com.example.wordmaster.model.recycler.DictionaryListItem;
import com.example.wordmaster.model.recycler.DictionaryWordItem;
import com.example.wordmaster.model.recycler.OnlineTestItem;

import java.util.ArrayList;

public interface SendDataToActivity {
    void sendDictData(DictionaryListItem item);
    @Deprecated
    void sendTestingData(int pos,int maxCount, String limitTime, int rgTestType, int rgTestTimeOption,String host,String title);

    void sendTestingData(String userid,String roomKey,int maxCount,int rgTestType,String host,String title);

    void sendTestResult(int maxCount, int trueCount, String[] myAnswer, String[] answer, ArrayList<DictionaryWordItem> list);

    void sendSearchInfoData(Bundle bundle);

    void otherDict2Info(String title,String option,int maxCount,String roomKey,String userId,String userName);

    void onlineTest2testJoin(OnlineTestItem item);

    void onlineTest2testInfo(OnlineTestItem item);
}
