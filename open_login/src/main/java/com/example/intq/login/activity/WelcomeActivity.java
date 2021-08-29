package com.example.intq.login.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.login.R;
import com.example.intq.common.core.WDActivity;
import com.example.intq.login.databinding.ActivityWelcomeBinding;
import com.example.intq.common.util.Constant;
import com.example.intq.login.vm.WelcomeViewModel;

@Route(path = Constant.ACTIVITY_URL_WELCOME)
public class WelcomeActivity extends WDActivity<WelcomeViewModel, ActivityWelcomeBinding> {

    @Override
    protected void initView(Bundle bundle) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

}
