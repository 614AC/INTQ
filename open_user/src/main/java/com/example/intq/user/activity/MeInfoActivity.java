package com.example.intq.user.activity;

import android.net.Uri;
import android.os.Bundle;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.user.R;
import com.example.intq.user.databinding.ActivityMeInfoBinding;
import com.example.intq.user.vm.MeInfoViewModel;
import com.example.intq.user.vm.UserViewModel;

@Route(path = Constant.ACTIVITY_URL_ME_INFO)
public class MeInfoActivity extends WDActivity<MeInfoViewModel, ActivityMeInfoBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewModel.avatar.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null)
                    binding.meAvatar.setImageURI(Uri.parse(s));
            }
        });
    }
}
