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

import java.util.Objects;

import okhttp3.RequestBody;

public class EditPwdViewModel extends WDViewModel<IUserRequest> {

    public ObservableField<String> oldPwd = new ObservableField<>();
    public ObservableField<String> pwd = new ObservableField<>();
    public ObservableField<String> pwdRpt = new ObservableField<>();
    public MutableLiveData<Boolean> oldVis = new MutableLiveData<>();
    public MutableLiveData<Boolean> pwdVis = new MutableLiveData<>();
    public MutableLiveData<Boolean> pwdRptVis = new MutableLiveData<>();

    public void oldVisibility(){
        oldVis.setValue(oldVis.getValue()==null?false:!oldVis.getValue());
    }
    public void pwdVisibility(){
        pwdVis.setValue(pwdVis.getValue()==null?false:!pwdVis.getValue());
    }
    public void pwdRptVisibility(){
        pwdRptVis.setValue(pwdRptVis.getValue()==null?false:!pwdRptVis.getValue());
    }
    @Override
    protected void create(){
        super.create();
    }

    public void editPwd(){
        if(oldPwd.get() == null){
            UIUtils.showToastSafe("请输入原密码");
            return;
        }
        if(pwd.get() == null || pwd.get().length() < 6 || pwd.get().length() > 16){
            UIUtils.showToastSafe("请输入合法新密码");
            return;
        }
        if(!Objects.equals(pwd.get(), pwdRpt.get())){
            UIUtils.showToastSafe("两次输入密码不一致");
            return;
        }
        RequestBody body = NetworkManager.convertJsonBody(new String[]{"oldPassword", "password"}, new String[]{oldPwd.get(), pwd.get()});
        request(iRequest.changeinfo(body, LOGIN_USER.getToken()), new DataCall<LinkedTreeMap>() {
            @Override
            public void success(LinkedTreeMap data) {
                UIUtils.showToastSafe("修改密码成功");
                finish();
            }

            @Override
            public void fail(ApiException data) {

            }
        });
    }
}