package com.example.intq.common.bean;

public class UserInfoResult{
    private String userName, mobile, email, avatar;

    public UserInfoResult(String userName, String mobile, String email, String avatar) {
        this.userName = userName;
        this.mobile = mobile;
        this.email = email;
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }
}