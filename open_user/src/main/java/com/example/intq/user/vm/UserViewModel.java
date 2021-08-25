package com.example.intq.user.vm;

import android.os.Message;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.user.UserInfoResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.user.request.IUserInfoRequest;


public class UserViewModel extends WDFragViewModel<IUserInfoRequest> {

    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> avatar = new ObservableField<>();
    @Override
    protected void create() {
        super.create();
        if(LOGIN_USER.getUsername() == null){
            request(iRequest.getUserInfo(LOGIN_USER.getToken()), new DataCall<UserInfoResult>() {
                @Override
                public void success(UserInfoResult data) {
                    LOGIN_USER.setAvatar(data.getAvatar());
                    LOGIN_USER.setUsername(data.getUserName());
                    LOGIN_USER.setMobile(data.getMobile());
                    LOGIN_USER.setEmail(data.getEmail());
                    userName.set(LOGIN_USER.getUsername());
                    avatar.set(LOGIN_USER.getAvatar());
//                    System.out.println(userName.get());
                }

                @Override
                public void fail(ApiException e) {
                    UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
                }
            });
        }
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
