package com.example.intq.login.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.login.R;
import com.example.intq.common.core.WDActivity;
import com.example.intq.login.databinding.ActivityRegisterBinding;
import com.example.intq.common.util.Constant;
import com.example.intq.login.vm.RegisterViewModel;

@Route(path = Constant.ACTIVITY_URL_REGISTER)
public class RegisterActivity extends WDActivity<RegisterViewModel, ActivityRegisterBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewModel.pasVis.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//密码显示，则隐藏
                    binding.regPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.regPasEye.setActivated(false);
                } else {//密码隐藏则显示
                    binding.regPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.regPasEye.setActivated(true);
                }
            }
        });
        viewModel.pasRptVis.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//密码显示，则隐藏
                    binding.regPasRpt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.regPasEye2.setActivated(false);
                } else {//密码隐藏则显示
                    binding.regPasRpt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.regPasEye2.setActivated(true);
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
