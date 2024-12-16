package com.example.chatbox;

public class UserModel {
    String userId;
    String userName;
    String userEmail;
    String phoneno;

    public UserModel() {
    }

    public UserModel(String userId, String userName, String userEmail, String phoneno) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.phoneno = phoneno;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
