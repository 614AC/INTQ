package com.example.intq.user.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.user.R;
import com.example.intq.user.databinding.ActivityEditEmailBinding;
import com.example.intq.user.databinding.ActivityEditUserNameBinding;
import com.example.intq.user.vm.EditEmailViewModel;
import com.example.intq.user.vm.EditUserNameViewModel;

@Route(path = Constant.ACTIVITY_URL_EDIT_EMAIL)
public class EditEmailActivity extends WDActivity<EditEmailViewModel, ActivityEditEmailBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_email;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
