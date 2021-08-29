package com.example.intq.debug.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.debug.R;
import com.example.intq.debug.databinding.ActivityDebugBinding;
import com.example.intq.debug.vm.DebugViewModel;

/**
 *  debug功能页
 */
@Route(path = Constant.ACTIVITY_URL_DEBUG)
public class DebugActivity extends WDActivity<DebugViewModel, ActivityDebugBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_debug;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }
}
