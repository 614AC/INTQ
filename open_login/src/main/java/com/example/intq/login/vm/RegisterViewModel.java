package com.example.intq.login.vm;

import android.text.TextUtils;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.core.http.NetworkManager;
import com.example.intq.common.util.MD5Utils;
import com.example.intq.common.util.UIUtils;
import com.example.intq.login.request.ILoginRequest;
import com.google.gson.internal.LinkedTreeMap;

import java.util.Objects;

import okhttp3.RequestBody;

public class RegisterViewModel extends WDViewModel<ILoginRequest> {
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> pas = new ObservableField<>();
    public ObservableField<String> pasRpt = new ObservableField<>();
    public MutableLiveData<Boolean> pasVis = new MutableLiveData<>();
    public MutableLiveData<Boolean> pasRptVis = new MutableLiveData<>();

    public void pasVisibility(){
        pasVis.setValue(pasVis.getValue()==null?false:!pasVis.getValue());
    }
    public void pasRptVisibility(){
        pasRptVis.setValue(pasRptVis.getValue()==null?false:!pasRptVis.getValue());
    }

    public void register() {
        String m = userName.get();
        String p = pas.get();
        if (TextUtils.isEmpty(m) || m.length() > 16) {
            UIUtils.showToastSafe("请输入合法的用户名");
            return;
        }
        if (TextUtils.isEmpty(p) || p.length() < 6 || p.length() > 16) {
            UIUtils.showToastSafe("请输入密码");
            return;
        }
        if (!Objects.equals(pas.get(), pasRpt.get())) {
            UIUtils.showToastSafe("两次密码输入不同");
            return;
        }

        dialog.setValue(true);

        RequestBody info = NetworkManager.convertJsonBody(new String[]{"userName", "password"}, new String[]{m, MD5Utils.md5(p)});
        request(iRequest.register(info), new DataCall<LinkedTreeMap>() {

            @Override
            public void success(LinkedTreeMap data) {
                dialog.setValue(false);
                UIUtils.showToastSafe("注册成功，请登录");
                finish();
            }

            @Override
            public void fail(ApiException e) {
                dialog.setValue(false);
                UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
            }
        });
    }

    public void login(){
        finish();
    }
}
