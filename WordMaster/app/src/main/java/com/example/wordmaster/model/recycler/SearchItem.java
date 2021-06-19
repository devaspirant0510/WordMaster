package com.example.wordmaster.model.recycler;

import java.util.ArrayList;

public class SearchItem {
    String host;
    String title;
    String description;
    String maxCount;
    String passWord;
    String id;
    String roomKey;
    ArrayList<String> hashTag;
    int viewType;

    public SearchItem(String host, String title, String description, String maxCount,
                      String passWord,String id,String roomKey,ArrayList<String> hashTag,int viewType) {
        this.host = host;
        this.title = title;
        this.description = description;
        this.maxCount = maxCount;
        this.passWord = passWord;
        this.id = id;
        this.roomKey = roomKey;
        this.hashTag = hashTag;
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    public ArrayList<String> getHashTag() {
        return hashTag;
    }

    public void setHashTag(ArrayList<String> hashTag) {
        this.hashTag = hashTag;
    }
}
