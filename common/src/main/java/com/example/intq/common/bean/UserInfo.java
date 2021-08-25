package com.example.intq.common.bean;

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
    @Id(assignable = true)
    long userId;
    String avatar;
    String username;
    String mobile;
    String sessionId;
    int sex;

    int status;//记录本地用户登录状态，用于直接登录和退出,1:登录，0：未登录或退出

    public UserInfo(long userId, String avatar, String username, String mobile,
                    String sessionId, int sex, int status) {
        this.userId = userId;
        this.avatar = avatar;
        this.username = username;
        this.mobile = mobile;
        this.sessionId = sessionId;
        this.sex = sex;
        this.status = status;
    }

    public UserInfo() {
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
