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

public class MeInfoViewModel extends WDViewModel<IUserRequest> {

    public ObservableField<String> userName = new ObservableField<>();
    public MutableLiveData<String> avatar = new MutableLiveData<>();

    @Override
    protected void create(){
        super.create();
        if(LOGIN_USER.getUsername() == null){
            updateInfo();
        }
        userName.set(LOGIN_USER.getUsername());
        avatar.setValue(LOGIN_USER.getAvatar());

    }

    public void updateInfo(){
        request(iRequest.getUserInfo(LOGIN_USER.getToken()), new DataCall<UserInfoResult>() {
            @Override
            public void success(UserInfoResult data) {
                LOGIN_USER.setAvatar(data.getAvatar());
                LOGIN_USER.setUsername(data.getUserName());
                LOGIN_USER.setMobile(data.getMobile());
                LOGIN_USER.setEmail(data.getEmail());
                userName.set(LOGIN_USER.getUsername());
                avatar.setValue(LOGIN_USER.getAvatar());
//                    System.out.println(userName.get());
            }

            @Override
            public void fail(ApiException e) {
                UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
            }
        });
    }

    public void editUserName(){
        intentByRouter(Constant.ACTIVITY_URL_EDIT_USER_NAME);
    }

}
