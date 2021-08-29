package com.example.intq.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.intq.common.core.WDFragment;
import com.example.intq.main.R;
import com.example.intq.common.core.WDActivity;
import com.example.intq.main.databinding.ActivityMainBinding;
import com.example.intq.main.fragment.CircleFragment;
import com.example.intq.main.fragment.HomeFragment;
import com.example.intq.common.util.Constant;
import com.example.intq.main.vm.MainViewModel;

/**
 * 本页面使用FragmentManager的add/hide/show，如果要使用ViewPager+Fragment，请参照MainFromViewPagerActivity
 */
//@Route(path = Constant.ACTIVITY_URL_MAIN)
public class MainActivity extends WDActivity<MainViewModel, ActivityMainBinding> {

    private HomeFragment homeFragment;
    private CircleFragment circleFragment;
    private WDFragment meFragment;
    private Fragment currentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        homeFragment = new HomeFragment();
        circleFragment = new CircleFragment();
        meFragment = (WDFragment) ARouter.getInstance().build(Constant.FRAGMENT_URL_ME).navigation();
        if (meFragment!=null){//加载组件之后再赋值
            viewModel.addFragViewModel(meFragment.getFragViewModel());
        }

        viewModel.addFragViewModel(homeFragment.getFragViewModel());
        viewModel.addFragViewModel(circleFragment.getFragViewModel());

        currentFragment = homeFragment;
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.add(R.id.container, homeFragment)
                .show(homeFragment).commit();
        viewModel.cId.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer checkedId) {
                if (checkedId == R.id.home_btn) {
                    showFragment(homeFragment);
                } else if (checkedId == R.id.circle_btn) {
                    showFragment(circleFragment);
                }else if (checkedId == R.id.me_btn){
                    showFragment(meFragment);
                }
            }
        });

        viewModel.fragDataShare.observe(this, new Observer<Message>() {
            @Override
            public void onChanged(Message message) {
                if (message.what==100){
                    binding.homeBtn.setChecked(true);
                }
            }
        });
    }


    /**
     * 展示Fragment
     */
    private void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.container, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        circleFragment.onActivityResult(requestCode,resultCode,data);
    }
}
