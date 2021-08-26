package com.example.intq.user.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.user.R;
import com.example.intq.user.databinding.ActivityMeSafetyBinding;
import com.example.intq.user.vm.MeSafetyViewModel;

@Route(path = Constant.ACTIVITY_URL_ME_SAFETY)
public class MeSafetyActivity extends WDActivity<MeSafetyViewModel, ActivityMeSafetyBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_safety;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        viewModel.updateInfo();
    }
}
