package com.example.wordmaster.model.recycler;

/**
 * @author seungho
 * @since 2021-06-20
 * project com.example.wordmaster.model.recycler
 * class OnlineTestAdapter.java
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description 온라인 테스트 리사이클러뷰 아이템
 **/
public class OnlineTestItem {
    String title;
    String host;
    String hostId;
    String password;
    String hashTag;
    int maxCount;
    String description;
    String startTime;
    String endTime;
    int viewType;

    public OnlineTestItem(String title, String host, String hostId, String password,
                          String hashTag, int maxCount, String description, String startTime,
                          String endTime, int viewType) {
        this.title = title;
        this.host = host;
        this.hostId = hostId;
        this.password = password;
        this.hashTag = hashTag;
        this.maxCount = maxCount;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
