package com.example.intq.common.bean.user;

public class AvatarResult {
    private String avatar, url;

    public AvatarResult(String avatar, String url) {
        this.avatar = avatar;
        this.url = url;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
