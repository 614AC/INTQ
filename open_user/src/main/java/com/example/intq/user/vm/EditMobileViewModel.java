package com.example.intq.user.vm;

import androidx.annotation.UiThread;
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

public class EditMobileViewModel extends WDViewModel<IUserRequest> {

    public ObservableField<String> mobile = new ObservableField<>();

    @Override
    protected void create(){
        super.create();
        if(LOGIN_USER.getUsername() == null){
            request(iRequest.getUserInfo(LOGIN_USER.getToken()), new DataCall<UserInfoResult>() {
                @Override
                public void success(UserInfoResult data) {
                    LOGIN_USER.setMobile(data.getMobile());
                    userInfoBox.put(LOGIN_USER);
                    mobile.set(LOGIN_USER.getMobile());
                }

                @Override
                public void fail(ApiException e) {
                    UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
                }
            });
        }
        mobile.set(LOGIN_USER.getEmail());
    }

    public void editMobile(){
        if(mobile.get() == null || mobile.get().length() > 11){
            UIUtils.showToastSafe("请输入合法手机号");
            return;
        }
        RequestBody body = NetworkManager.convertJsonBody(new String[]{"mobile"}, new String[]{mobile.get()});
        request(iRequest.changeinfo(body, LOGIN_USER.getToken()), new DataCall<LinkedTreeMap>() {
            @Override
            public void success(LinkedTreeMap data) {
                UIUtils.showToastSafe("修改手机成功");
                finish();
            }

            @Override
            public void fail(ApiException data) {

            }
        });
    }
}

