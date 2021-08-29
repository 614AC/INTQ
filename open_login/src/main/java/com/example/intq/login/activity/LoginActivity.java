package com.example.intq.login.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.login.R;
import com.example.intq.common.core.WDActivity;
import com.example.intq.login.databinding.ActivityLoginBinding;
import com.example.intq.common.util.Constant;
import com.example.intq.login.vm.LoginViewModel;

@Route(path = Constant.ACTIVITY_URL_LOGIN)
public class LoginActivity extends WDActivity<LoginViewModel, ActivityLoginBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewModel.pasVis.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//密码显示，则隐藏
                    binding.loginPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {//密码隐藏则显示
                    binding.loginPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }
}
