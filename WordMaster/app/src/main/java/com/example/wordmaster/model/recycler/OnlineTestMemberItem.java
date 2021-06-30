package com.example.wordmaster.model.recycler;

/**
 * @author seungho
 * @since 2021-06-30
 * class OnlineTestMemberItem.java
 * project WordMaster
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description
 **/
public class OnlineTestMemberItem {
    private String userName;
    private String userId;
    private String profileURL;
    private String memberComment;

    public OnlineTestMemberItem(){

    }
    public OnlineTestMemberItem(String userName, String userId, String profileURL,String memberComment) {
        this.userName = userName;
        this.userId = userId;
        this.profileURL = profileURL;
        this.memberComment = memberComment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getMemberComment() {
        return memberComment;
    }

    public void setMemberComment(String memberComment) {
        this.memberComment = memberComment;
    }
}
