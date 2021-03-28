package com.example.wordmaster.callback;

public interface SendDataToActivity {
    void sendDictData(String title,String option,String hashTag,int count);
    void sendTestingData(int maxCount, String limitTime, int rgTestType, int rgTestTimeOption,String host,String title);
}
