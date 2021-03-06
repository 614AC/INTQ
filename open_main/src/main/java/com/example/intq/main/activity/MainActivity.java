package com.example.intq.main.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ActivityMainBinding;
import com.example.intq.main.fragment.HomeFragment;
import com.example.intq.main.vm.MainFromViewPagerViewModel;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.Constant;
import com.example.intq.main.fragment.MultiToolFragment;

import java.util.LinkedHashMap;
import java.util.Map;

@Route(path = Constant.ACTIVITY_URL_MAIN)
public class MainActivity extends WDActivity<MainFromViewPagerViewModel, ActivityMainBinding> {

    private HomeFragment homeFragment;
    private MultiToolFragment multiToolFragment;
    private WDFragment meFragment;
    private Fragment currentFragment;
    private Map<Integer, Integer> idsMap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        homeFragment = new HomeFragment();
        multiToolFragment = new MultiToolFragment();
        meFragment = (WDFragment) ARouter.getInstance().build(Constant.FRAGMENT_URL_ME).navigation();
        if (meFragment != null) {//加载组件之后再赋值
            viewModel.addFragViewModel(meFragment.getFragViewModel());
        }

        idsMap = new LinkedHashMap<>();
        idsMap.put(0, R.id.home_btn);
//        idsMap.put(1, R.id.circle_btn);
//        idsMap.put(2, R.id.cart_btn);
        idsMap.put(1, R.id.list_btn);
        idsMap.put(2, R.id.me_btn);

        idsMap.put(R.id.home_btn, 0);
//        idsMap.put(R.id.circle_btn, 1);
//        idsMap.put(R.id.cart_btn, 2);
        idsMap.put(R.id.list_btn, 1);
        idsMap.put(R.id.me_btn, 2);

        viewModel.addFragViewModel(homeFragment.getFragViewModel());

        currentFragment = homeFragment;

//        binding.pager.setCurrentItem(1);
        binding.pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        currentFragment = homeFragment;
                        break;
                    case 1:
                        currentFragment = multiToolFragment;
                        break;
                    case 2:
                        currentFragment = meFragment;
                        break;
                }
                return currentFragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.bottomMenu.check(idsMap.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewModel.cId.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer checkedId) {
                binding.pager.setCurrentItem(idsMap.get(checkedId));
            }
        });

        viewModel.fragDataShare.observe(this, new Observer<Message>() {
            @Override
            public void onChanged(Message message) {
                if (message.what == 100) {
                    viewModel.cId.setValue(R.id.home_btn);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQ_TAB_CONFIG)
            homeFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
