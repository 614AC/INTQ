package com.example.intq.login.vm;

import android.text.TextUtils;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.core.http.NetworkManager;
import com.example.intq.common.util.UIUtils;
import com.example.intq.login.request.ILoginRequest;

import okhttp3.RequestBody;

public class RegisterViewModel extends WDViewModel<ILoginRequest> {
    public ObservableField<String> mobile = new ObservableField<>();
    public ObservableField<String> pas = new ObservableField<>();
    public MutableLiveData<Boolean> pasVis = new MutableLiveData<>();

    public void pasVisibility(){
        pasVis.setValue(pasVis.getValue()==null?false:!pasVis.getValue());
    }

    public void register() {
        String m = mobile.get();
        String p = pas.get();
        if (TextUtils.isEmpty(m)) {
            UIUtils.showToastSafe("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(p)) {
            UIUtils.showToastSafe("请输入密码");
            return;
        }
        dialog.setValue(true);

        RequestBody body = NetworkManager.convertJsonBody(new String[]{"phone","pwd"},new String[]{m,p});
        request(iRequest.register(body), new DataCall<Void>() {

            @Override
            public void success(Void data) {
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
}
