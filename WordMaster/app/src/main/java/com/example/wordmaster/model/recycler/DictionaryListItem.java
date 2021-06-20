package com.example.wordmaster.model.recycler;

import java.util.ArrayList;

//단어장 항목 리스트
public class DictionaryListItem {
    String dictionaryTitle;
    String dictionaryMaxCount;
    String dictionaryDescription;
    String dictionaryHost;
    String dictOption;
    String dictRoomKey;
    ArrayList<String> dictHashTag;
    String userId;
    String userName;
    int viewType;

    public DictionaryListItem(String dictionaryTitle, String dictionaryMaxCount,
                              String dictionaryDescription, String dictionaryHost,
                              String dictOption, String dictRoomKey, ArrayList<String> dictHashTag,
                              String userId, String userName,int viewType) {
        this.dictionaryTitle = dictionaryTitle;
        this.dictionaryMaxCount = dictionaryMaxCount;
        this.dictionaryDescription = dictionaryDescription;
        this.dictionaryHost = dictionaryHost;
        this.dictOption = dictOption;
        this.dictRoomKey = dictRoomKey;
        this.dictHashTag = dictHashTag;
        this.viewType = viewType;
        this.userId = userId;
        this.userName = userName;
    }

    public String getDictionaryTitle() {
        return dictionaryTitle;
    }

    public void setDictionaryTitle(String dictionaryTitle) {
        this.dictionaryTitle = dictionaryTitle;
    }

    public String getDictionaryMaxCount() {
        return dictionaryMaxCount;
    }

    public void setDictionaryMaxCount(String dictionaryMaxCount) {
        this.dictionaryMaxCount = dictionaryMaxCount;
    }

    public String getDictionaryDescription() {
        return dictionaryDescription;
    }

    public void setDictionaryDescription(String dictionaryDescription) {
        this.dictionaryDescription = dictionaryDescription;
    }

    public String getDictionaryHost() {
        return dictionaryHost;
    }

    public void setDictionaryHost(String dictionaryHost) {
        this.dictionaryHost = dictionaryHost;
    }

    public String getDictOption() {
        return dictOption;
    }

    public void setDictOption(String dictOption) {
        this.dictOption = dictOption;
    }

    public String getDictRoomKey() {
        return dictRoomKey;
    }

    public void setDictRoomKey(String dictRoomKey) {
        this.dictRoomKey = dictRoomKey;
    }

    public ArrayList<String> getDictHashTag() {
        return dictHashTag;
    }

    public void setDictHashTag(ArrayList<String> dictHashTag) {
        this.dictHashTag = dictHashTag;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
