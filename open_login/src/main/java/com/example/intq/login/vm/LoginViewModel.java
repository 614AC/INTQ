package com.example.intq.login.vm;

import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.BaseResult;
import com.example.intq.common.bean.Result;
import com.example.intq.common.bean.UserInfo;
import com.example.intq.common.bean.UserInfoResult;
import com.example.intq.common.bean.user.UserLoginResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDApplication;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.core.http.NetworkManager;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.login.request.ILoginRequest;

import okhttp3.RequestBody;

public class LoginViewModel extends WDViewModel<ILoginRequest> {

    public ObservableField<Boolean> remPas = new ObservableField<>();
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> pas = new ObservableField<>();
    public MutableLiveData<Boolean> pasVis = new MutableLiveData<>();

    public void pasVisibility() {
        pasVis.setValue(pasVis.getValue() == null ? false : !pasVis.getValue());
    }

    @Override
    protected void create() {
        super.create();
        boolean rem = WDApplication.getShare().getBoolean("remPas", true);
        if (rem) {
            remPas.set(rem);
            userName.set(WDApplication.getShare().getString("userName", ""));
            pas.set(WDApplication.getShare().getString("password", ""));
        }
    }

    public void debug() {
        intentByRouter(Constant.ACTIVITY_URL_DEBUG);
    }

    public void login() {
        String m = userName.get();
        String p = pas.get();
        if (TextUtils.isEmpty(m) || m == null || m.length() > 16) {
            UIUtils.showToastSafe("请输入正确的用户名");
            return;
        }
        if (TextUtils.isEmpty(p) || p == null || p.length() < 6 || p.length() > 16) {
            UIUtils.showToastSafe("请输入密码");
            return;
        }
        if (remPas.get()) {
            WDApplication.getShare().edit().putString("userName", m)
                    .putString("password", p).commit();
        }
        dialog.setValue(true);

        request(iRequest.login(m, p), new DataCall<UserLoginResult>() {
            @Override
            public void success(UserLoginResult result) {
                dialog.setValue(false);
                System.out.println(result.getToken());
//                result.setStatus(1);//设置登录状态，保存到数据库
//                userInfoBox.put(result);
                intentByRouter(Constant.ACTIVITY_URL_MAIN);
                finish();
            }

            @Override
            public void fail(ApiException e) {
                dialog.setValue(false);
                UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
            }
        });
    }

    public void register() {
        intentByRouter(Constant.ACTIVITY_URL_REGISTER);
    }
}
