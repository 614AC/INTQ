package com.example.intq.user.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.user.R;
import com.example.intq.user.databinding.ActivityEditMobileBinding;
import com.example.intq.user.vm.EditMobileViewModel;

@Route(path = Constant.ACTIVITY_URL_EDIT_MOBILE)
public class EditMobileActivity extends WDActivity<EditMobileViewModel, ActivityEditMobileBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_mobile;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
