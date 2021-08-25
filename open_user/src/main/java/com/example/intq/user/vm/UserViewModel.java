package com.example.intq.user.vm;

import android.os.Message;

import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.util.Constant;


public class UserViewModel extends WDFragViewModel<Void> {

    @Override
    protected void create() {
        super.create();

    }

    public void dataShare() {
        Message message = Message.obtain();
        message.what = 100;
        message.obj = "点击头像跳转首页,且携带数据";
        fragDataShare.setValue(message);
    }

    public void info() {
        intentByRouter(Constant.ACTIVITY_URL_INFO);
    }

    public void setting() {
        intentByRouter(Constant.ACTIVITY_URL_SET);
    }

    public void logout() {
        userInfoBox.remove(LOGIN_USER.getUserId());

        intentByRouter(Constant.ACTIVITY_URL_LOGIN);
        finish();//本页面关闭
    }
}
