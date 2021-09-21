package com.example.wordmaster.model.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;

@IgnoreExtraProperties
public class UserAccount  {
    private String userName;
    private String userEmail;
    private String userGender;
    private String userAge;
    private String userBirth;
    private String userProfileUri;
    private String joinDate;
    private String userComment;
    private String userJoinTestId;
    private HashMap<String,HashMap<String,HashMap<String,Integer>>> activityHistory ;
    private HashMap<String,ArrayList<String>> userDownloadDict;

    public UserAccount(){

    }

    public UserAccount(String userName, String userEmail, String userGender, String userAge,
                       String userBirth, String userProfileUri,
                       HashMap<String, ArrayList<String>> userDownloadDict, String joinDate,
                       String userComment,String userJoinTestId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userAge = userAge;
        this.userBirth = userBirth;
        this.userProfileUri = userProfileUri;
        this.userDownloadDict = userDownloadDict;
        this.joinDate = joinDate;
        this.userComment = userComment;
        this.userJoinTestId = userJoinTestId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserProfileUri() {
        return userProfileUri;
    }

    public void setUserProfileUri(String userProfileUri) {
        this.userProfileUri = userProfileUri;
    }

    public HashMap<String, ArrayList<String>> getUserDownloadDict() {
        return userDownloadDict;
    }

    public void setUserDownloadDict(HashMap<String, ArrayList<String>> userDownloadDict) {
        this.userDownloadDict = userDownloadDict;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public HashMap<String, HashMap<String, HashMap<String, Integer>>> getActivityHistory() {
        return activityHistory;
    }

    public void setActivityHistory(HashMap<String, HashMap<String, HashMap<String, Integer>>> activityHistory) {
        this.activityHistory = activityHistory;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}
