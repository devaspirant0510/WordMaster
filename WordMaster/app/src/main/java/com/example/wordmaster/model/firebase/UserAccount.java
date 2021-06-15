package com.example.wordmaster.model.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class UserAccount  {
    String userName;
    String userEmail;
    String userGender;
    String userAge;
    String userBirth;
    private String userProfileUri;
    private ArrayList<String> userDownloadDict;

    public UserAccount(){

    }

    public UserAccount(String userName, String userEmail, String userGender, String userAge,
                       String userBirth, String userProfileUri,ArrayList<String> userDownloadDict) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userAge = userAge;
        this.userBirth = userBirth;
        this.userProfileUri = userProfileUri;
        this.userDownloadDict = userDownloadDict;
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
}
