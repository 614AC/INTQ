package com.example.intq.common.bean.user;

public class UserInfoResult{
    private String userName, mobile, email;

    public UserInfoResult(String userName, String mobile, String email) {
        this.userName = userName;
        this.mobile = mobile;
        this.email = email;
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
}