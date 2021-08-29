package com.example.intq.main.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.main.R;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.main.databinding.ActivitySetBinding;
import com.example.intq.main.vm.SetViewModel;

@Route(path = Constant.ACTIVITY_URL_SET)
public class SetActivity extends WDActivity<SetViewModel, ActivitySetBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView(Bundle bundle) {
    }
}
