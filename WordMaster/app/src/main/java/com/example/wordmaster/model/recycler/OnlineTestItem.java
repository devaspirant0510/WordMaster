package com.example.wordmaster.model.recycler;

import java.io.Serializable;

/**
 * @author seungho
 * @since 2021-06-20
 * project com.example.wordmaster.model.recycler
 * class OnlineTestAdapter.java
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description 온라인 테스트 리사이클러뷰 아이템
 **/
public class OnlineTestItem implements Serializable {
    String title;
    String host;
    String hostId;
    String roomKey;
    String password;
    String hashTag;
    int maxCount;
    String description;
    String startTime;
    String endTime;
    String option;
    int maxUser;
    int viewType;

    public OnlineTestItem(String title, String host, String hostId, String roomKey, String password,
                          String hashTag, int maxCount, String description, String startTime,
                          String endTime, String option,int maxUser,int viewType) {
        this.title = title;
        this.host = host;
        this.hostId = hostId;
        this.roomKey = roomKey;
        this.password = password;
        this.hashTag = hashTag;
        this.maxCount = maxCount;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.option = option;
        this.maxUser = maxUser;
        this.viewType = viewType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getMaxUser() {
        return maxUser;
    }

    public void setMaxUser(int maxUser) {
        this.maxUser = maxUser;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

}
