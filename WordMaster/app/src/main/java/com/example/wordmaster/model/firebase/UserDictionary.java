package com.example.wordmaster.model.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserDictionary {
    String option;
    String title;
    int maxCount;
    int currentCount;
    String description;
    String hashTag;
    public UserDictionary(){

    }
    public UserDictionary(String option, String title, int maxCount,int currentCount, String description, String hashTag) {
        this.option = option;
        this.title = title;
        this.maxCount = maxCount;
        this.currentCount = currentCount;
        this.description = description;
        this.hashTag = hashTag;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }
}
