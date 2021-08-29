package com.example.intq.common.bean.user;

public class UserLoginResult {
    private String token;

    public UserLoginResult(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
