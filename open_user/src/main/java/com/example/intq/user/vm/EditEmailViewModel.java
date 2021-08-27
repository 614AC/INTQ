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

import java.util.regex.Pattern;

import okhttp3.RequestBody;

public class EditEmailViewModel extends WDViewModel<IUserRequest> {

    public ObservableField<String> email = new ObservableField<>();

    @Override
    protected void create(){
        super.create();
        if(LOGIN_USER.getUsername() == null){
            request(iRequest.getUserInfo(LOGIN_USER.getToken()), new DataCall<UserInfoResult>() {
                @Override
                public void success(UserInfoResult data) {
                    LOGIN_USER.setEmail(data.getEmail());
                    userInfoBox.put(LOGIN_USER);
                    email.set(LOGIN_USER.getEmail());
                }

                @Override
                public void fail(ApiException e) {
                    UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
                }
            });
        }
        email.set(LOGIN_USER.getEmail());
    }

    public void editEmail(){
        if(email.get() == null || !Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", email.get())){
            UIUtils.showToastSafe("请输入合法邮箱");
            return;
        }
        RequestBody body = NetworkManager.convertJsonBody(new String[]{"email"}, new String[]{email.get()});
        request(iRequest.changeinfo(body, LOGIN_USER.getToken()), new DataCall<LinkedTreeMap>() {
            @Override
            public void success(LinkedTreeMap data) {
                UIUtils.showToastSafe("修改邮箱成功");
                finish();
            }

            @Override
            public void fail(ApiException data) {

            }
        });
    }
}
