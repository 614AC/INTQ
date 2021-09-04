package com.example.intq.main.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ActivityCustomizedTestBinding;
import com.example.intq.main.vm.CustomizedTestViewModel;

@Route(path = Constant.ACTIVITY_CUSTOMIZED)
public class CustomizedTestActivity extends WDActivity<CustomizedTestViewModel, ActivityCustomizedTestBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_customized_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
