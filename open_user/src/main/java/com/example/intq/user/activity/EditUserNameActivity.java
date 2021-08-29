package com.example.intq.user.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.user.R;
import com.example.intq.user.databinding.ActivityEditUserNameBinding;
import com.example.intq.user.vm.EditUserNameViewModel;


@Route(path = Constant.ACTIVITY_URL_EDIT_USER_NAME)
public class EditUserNameActivity extends WDActivity<EditUserNameViewModel, ActivityEditUserNameBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_user_name;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
