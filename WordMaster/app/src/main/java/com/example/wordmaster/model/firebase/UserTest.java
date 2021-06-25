package com.example.wordmaster.model.firebase;

/**
 * @author seungho
 * @since 2021-06-23
 * class UserTest.java
 * project WordMaster
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description UserTest 모델
 **/
public class UserTest {
    private String title;
    private String userId;
    private String userName;
    private String roomKey;
    private String startTime;
    private String endTime;
    private String password;
    private String description;
    private int option;
    private int orderBy;
    private int type;
    public UserTest(){

    }

    public UserTest(String title, String userId, String userName, String roomKey, String startTime,
                    String endTime,String password, String description,int option, int orderBy, int type) {
        this.title = title;
        this.userId = userId;
        this.userName = userName;
        this.roomKey = roomKey;
        this.startTime = startTime;
        this.endTime = endTime;
        this.password = password;
        this.description = description;
        this.option = option;
        this.orderBy = orderBy;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}