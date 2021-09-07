package com.example.intq.common.bean;

import android.graphics.Bitmap;

import com.example.intq.common.bean.instance.HomeTabInfo;

import java.util.List;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * desc
 * author VcStrong
 * github VcStrong
 * date 2020/5/28 1:42 PM
 */
@Entity
public class UserInfo {
    //as-signable:允许使用其他地方分配的ID，默认false代表objectbox自动生成
    @Id(assignable = false)
    long userId;
    String avatar;
    String username;
    String mobile;
    String email;
    String token;

    String starInst;
    String historyInst;

    //首页相关记录
    String homeTabInfo;

    int status;//记录本地用户登录状态，用于直接登录和退出,1:登录，0：未登录或退出

    public UserInfo(String avatar, String username, String mobile, String email, String token,
                    int status) {
        this.avatar = avatar;
        this.username = username;
        this.mobile = mobile;
        this.email = email;
        this.token = token;
        this.status = status;
    }

    public UserInfo() {
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public String getStarInst() {
        return starInst;
    }

    public void setStarInst(String starInst) {
        this.starInst = starInst;
    }

    public String getHistoryInst() {
        return historyInst;
    }

    public void setHistoryInst(String historyInst) {
        this.historyInst = historyInst;
    }

    public String getHomeTabInfo() {
        return homeTabInfo;
    }

    public void setHomeTabInfo(String homeTabInfo) {
        this.homeTabInfo = homeTabInfo;
    }
}
