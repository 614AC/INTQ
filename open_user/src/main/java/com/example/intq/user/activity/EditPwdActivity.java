package com.example.intq.user.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.user.R;
import com.example.intq.user.databinding.ActivityEditPwdBinding;
import com.example.intq.user.vm.EditPwdViewModel;

@Route(path = Constant.ACTIVITY_URL_EDIT_PWD)
public class EditPwdActivity extends WDActivity<EditPwdViewModel, ActivityEditPwdBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_pwd;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewModel.oldVis.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//密码显示，则隐藏
                    binding.old.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {//密码隐藏则显示
                    binding.old.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        viewModel.pwdVis.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//密码显示，则隐藏
                    binding.pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {//密码隐藏则显示
                    binding.pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        viewModel.pwdRptVis.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//密码显示，则隐藏
                    binding.pwdRpt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {//密码隐藏则显示
                    binding.pwdRpt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }
}
