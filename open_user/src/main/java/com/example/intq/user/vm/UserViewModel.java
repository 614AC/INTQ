package com.example.intq.user.vm;

import android.os.Message;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.user.AvatarResult;
import com.example.intq.common.bean.user.UserInfoResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.user.request.IUserRequest;


public class UserViewModel extends WDFragViewModel<IUserRequest> {

    public ObservableField<String> userName = new ObservableField<>();
    public MutableLiveData<String> avatar = new MutableLiveData<>();
    @Override
    protected void create() {
        super.create();
        userName.set(LOGIN_USER.getUsername());
        avatar.setValue(LOGIN_USER.getAvatar());
        updateInfo();
        updateAvatar();
    }

    public void updateInfo(){
        request(iRequest.getUserInfo(LOGIN_USER.getToken()), new DataCall<UserInfoResult>() {
            @Override
            public void success(UserInfoResult data) {
                LOGIN_USER.setUsername(data.getUserName());
                LOGIN_USER.setMobile(data.getMobile());
                LOGIN_USER.setEmail(data.getEmail());
                userInfoBox.put(LOGIN_USER);
                userName.set(LOGIN_USER.getUsername());
            }

            @Override
            public void fail(ApiException e) {
                UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
            }
        });
    }

    public void updateAvatar(){
        request(iRequest.avatar(LOGIN_USER.getToken()), new DataCall<AvatarResult>() {
            @Override
            public void success(AvatarResult data) {
                LOGIN_USER.setAvatar(data.getUrl());
                userInfoBox.put(LOGIN_USER);
                avatar.setValue(LOGIN_USER.getAvatar());
            }

            @Override
            public void fail(ApiException data) {

            }
        });
    }

    public void dataShare() {
        Message message = Message.obtain();
        message.what = 100;
        message.obj = "????????????????????????,???????????????";
        fragDataShare.setValue(message);
    }

    public void meInfo() {
        intentByRouter(Constant.ACTIVITY_URL_ME_INFO);
    }

    public void meSafety(){
        intentByRouter(Constant.ACTIVITY_URL_ME_SAFETY);
    }

    public void meStar(){intentByRouter(Constant.ACTIVITY_URL_ME_STAR);}

    public void meHistory(){intentByRouter(Constant.ACTIVITY_URL_ME_HISTORY);}

    public void setting() {
        intentByRouter(Constant.ACTIVITY_URL_SET);
    }

    public void logout() {
        userInfoBox.remove(LOGIN_USER.getUserId());

        intentByRouter(Constant.ACTIVITY_URL_LOGIN);
        finish();//???????????????
    }

    public String getUserId(){
        return String.valueOf(LOGIN_USER.getUserId());
    }
}
