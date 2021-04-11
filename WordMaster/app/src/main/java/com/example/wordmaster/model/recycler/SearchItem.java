package com.example.wordmaster.model.recycler;

public class SearchItem {
    String host;
    String title;
    String description;
    String maxCount;
    int viewType;

    public SearchItem(String host, String title, String description, String maxCount, int viewType) {
        this.host = host;
        this.title = title;
        this.description = description;
        this.maxCount = maxCount;
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
}
