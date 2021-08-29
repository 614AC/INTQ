package com.example.intq.user.vm;

import android.graphics.Bitmap;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.user.AvatarResult;
import com.example.intq.common.bean.user.UserInfoResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.core.http.NetworkManager;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.ImageUtils;
import com.example.intq.common.util.UIUtils;
import com.example.intq.user.request.IUserRequest;
import com.google.gson.internal.LinkedTreeMap;

import okhttp3.RequestBody;

public class MeInfoViewModel extends WDViewModel<IUserRequest> {

    public ObservableField<String> userName = new ObservableField<>();
    public MutableLiveData<String> avatar = new MutableLiveData<>();
    public ObservableField<String> email = new ObservableField<>();

    @Override
    protected void create(){
        super.create();
        userName.set(LOGIN_USER.getUsername());
        avatar.setValue(LOGIN_USER.getAvatar());
        email.set(LOGIN_USER.getEmail());
        if(LOGIN_USER.getUsername() == null){
            updateInfo();
        }
        if(LOGIN_USER.getAvatar() == null){
            updateAvatar();
        }
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
                email.set(LOGIN_USER.getEmail());
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

    public void editUserName(){
        intentByRouter(Constant.ACTIVITY_URL_EDIT_USER_NAME);
    }

    public void editEmail(){
        intentByRouter(Constant.ACTIVITY_URL_EDIT_EMAIL);
    }

    public void editAvatar(Bitmap bitmap){
        String avatarString = "data:image/jpg;base64," + ImageUtils.bitmapToBase64(bitmap).replace("\n","");
        RequestBody body = NetworkManager.convertJsonBody(new String[]{"avatar"}, new String[]{avatarString});
        request(iRequest.changeAvatar(LOGIN_USER.getToken(), body), new DataCall<AvatarResult>() {
            @Override
            public void success(AvatarResult data) {
                updateInfo();
                updateAvatar();
            }

            @Override
            public void fail(ApiException data) {

            }
        });
    }
}
