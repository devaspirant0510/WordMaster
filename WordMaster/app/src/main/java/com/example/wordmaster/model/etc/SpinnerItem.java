package com.example.wordmaster.model.etc;

/**
 * @author seungho
 * @since 2021-06-23
 * project com.example.wordmaster.model.etc
 * class SpinnerItem.java
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description TestSettingFragment 에서 단어장 보여줄 스피너 아이템
 **/
public class SpinnerItem {
    private String title;
    private String maxCount;
    private String description;
    private String roomKey;

    public SpinnerItem(String title, String maxCount, String description,String roomKey) {
        this.title = title;
        this.maxCount = maxCount;
        this.description = description;
        this.roomKey = roomKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }
}
