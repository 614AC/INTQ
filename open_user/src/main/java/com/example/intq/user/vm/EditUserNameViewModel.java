package com.example.intq.user.vm;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.user.UserInfoResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.core.http.NetworkManager;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.user.request.IUserRequest;
import com.google.gson.internal.LinkedTreeMap;

import okhttp3.RequestBody;

public class EditUserNameViewModel extends WDViewModel<IUserRequest> {

    public ObservableField<String> userName = new ObservableField<>();

    @Override
    protected void create(){
        super.create();
        if(LOGIN_USER.getUsername() == null){
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
        userName.set(LOGIN_USER.getUsername());
    }

    public void editUserName(){
        RequestBody body = NetworkManager.convertJsonBody(new String[]{"userName"}, new String[]{userName.get()});
        request(iRequest.changeinfo(body, LOGIN_USER.getToken()), new DataCall<LinkedTreeMap>() {
            @Override
            public void success(LinkedTreeMap data) {
                UIUtils.showToastSafe("修改用户名成功");
                finish();
            }

            @Override
            public void fail(ApiException data) {

            }
        });
    }
}