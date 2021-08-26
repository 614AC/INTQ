package com.example.intq.user.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.Constant;
import com.example.intq.user.R;
import com.example.intq.user.databinding.FragMeBinding;
import com.example.intq.user.vm.UserViewModel;

@Route(path = Constant.FRAGMENT_URL_ME)
public class MeFragment extends WDFragment<UserViewModel,FragMeBinding> {

    @Override
    protected UserViewModel initFragViewModel() {
        return new UserViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_me;
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
