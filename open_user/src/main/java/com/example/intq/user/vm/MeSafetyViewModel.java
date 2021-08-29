package com.example.intq.user.vm;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.user.UserInfoResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.user.request.IUserRequest;

public class MeSafetyViewModel extends WDViewModel<IUserRequest> {

    public ObservableField<String> mobile = new ObservableField<>();

    @Override
    protected void create(){
        super.create();
        if(LOGIN_USER.getMobile() == null){
            updateInfo();
        }
        mobile.set(LOGIN_USER.getMobile());

    }

    public void updateInfo(){
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

    public void editPwd(){
        intentByRouter(Constant.ACTIVITY_URL_EDIT_PWD);
    }

    public void editMobile(){
        intentByRouter(Constant.ACTIVITY_URL_EDIT_MOBILE);
    }

}
