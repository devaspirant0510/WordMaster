package com.example.wordmaster.model.firebase;

import com.example.wordmaster.model.recycler.OnlineTestMemberItem;

import java.util.ArrayList;

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
    private int userCount;
    private int maxCount;
    private ArrayList<OnlineTestMemberItem> memberList;
    private String testRoomKey;
    public UserTest(){

    }

    public UserTest(String title, String userId, String userName, String roomKey, String startTime,
                    String endTime,String password, String description,int option, int orderBy,
                    int userCount,int type,int maxCount,ArrayList<OnlineTestMemberItem> memberList,
                    String testRoomKey) {
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
        this.userCount = userCount;
        this.type = type;
        this.maxCount = maxCount;
        this.memberList = memberList;
        this.testRoomKey = testRoomKey;
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

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<OnlineTestMemberItem> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<OnlineTestMemberItem> memberList) {
        this.memberList = memberList;
    }

    public String getTestRoomKey() {
        return testRoomKey;
    }

    public void setTestRoomKey(String testRoomKey) {
        this.testRoomKey = testRoomKey;
    }
}
