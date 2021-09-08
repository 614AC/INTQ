package com.example.intq.user.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.core.content.FileProvider;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.Constant;
import com.example.intq.user.R;
import com.example.intq.user.databinding.FragMeBinding;
import com.example.intq.user.vm.UserViewModel;

import java.io.File;

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
                try {
                    binding.meAvatar.setImageURI(Uri.parse(s));
                }catch (Exception e){
                    File head = new File(getActivity().getFilesDir(), "head.jpg");
                    if(head.exists()){
                        binding.meAvatar.setImageURI(FileProvider.getUriForFile(getActivity().getApplicationContext(), "com.example.intq.fileprovider",head ));
                    }
                    else {
                        binding.meAvatar.setImageResource(R.drawable.ic___head_pic);
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.updateInfo();
        viewModel.updateAvatar();
    }

}
