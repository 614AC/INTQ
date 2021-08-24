package com.vc.wd.main.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.vc.wd.common.core.WDFragment;

import com.vc.wd.main.activity.MainActivity;
import com.vc.wd.main.adapter.HomeTabAdapter;
import com.vc.wd.main.R;
import com.vc.wd.main.vm.HomeViewModel;
import com.vc.wd.main.databinding.FragmentHomeBinding;

import com.vc.wd.common.util.UIUtils;

import java.util.List;

public class HomeFragment extends WDFragment<HomeViewModel, FragmentHomeBinding> {
    private HomeTabAdapter mTabAdapter;

    @Override
    protected HomeViewModel initFragViewModel() {
        return new HomeViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle bundle) {
        mTabAdapter = new HomeTabAdapter(getChildFragmentManager());

        binding.innerViewPager.setAdapter(mTabAdapter);
        binding.tabItems.setupWithViewPager(binding.innerViewPager);
        reduce();
        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (enabled)
                    expand();
                else
                    reduce();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });
    }

    private void expand() {
        //设置伸展状态时的布局
        binding.searchBar.setHint("搜索已光翼展开");

        //设置动画
        MarginLayoutParams searchBarLayoutParams = (MarginLayoutParams) binding.searchBar.getLayoutParams();

        searchBarLayoutParams.topMargin = binding.searchTitleBar.getHeight();
        searchBarLayoutParams.width = LayoutParams.MATCH_PARENT;
        binding.searchBar.setLayoutParams(searchBarLayoutParams);
        beginDelayedTransition(binding.searchBar, 0, 500);
        beginDelayedTransition(binding.tabLayout, 0, 500);
        beginDelayedAlphaTransition(binding.searchTitleBar, 0, 1, 500);
    }

    private void reduce() {
        // 设置收缩状态时的布局
        binding.searchBar.setHint("点我搜索");

        //设置动画
        MarginLayoutParams searchBarLayoutParams = (MarginLayoutParams) binding.searchBar.getLayoutParams();

        searchBarLayoutParams.width = UIUtils.getScreenWidth(getActivity()) * 3 / 4;
        searchBarLayoutParams.topMargin = 0;
        binding.searchBar.setLayoutParams(searchBarLayoutParams);
        beginDelayedTransition(binding.searchBar, 0, 500);
        beginDelayedTransition(binding.tabLayout, 0, 500);
        beginDelayedAlphaTransition(binding.searchTitleBar, 1, 0, 500);
    }

    void beginDelayedTransition(ViewGroup view, long startDelay, long duration) {
        AutoTransition tran = new AutoTransition();
        tran.setStartDelay(startDelay);
        tran.setDuration(duration);
        TransitionManager.beginDelayedTransition(view, tran);
    }

    void beginDelayedAlphaTransition(ViewGroup view, float fromAlpha, float endAlpha,
                                     long duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, endAlpha);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }

//        viewModel.bannerData.observe(this, new Observer<List<Banner>>() {
//            @Override
//            public void onChanged(List<Banner> banners) {
//                mBannerAdapter.updateData(banners);
//            }
//        });
//
//        viewModel.homeListData.observe(this, new Observer<HomeList>() {
//            @Override
//            public void onChanged(HomeList data) {
//                mHotAdapter.addAll(data.getRxxp().getCommodityList());
//                mFashionAdapter.addAll(data.getMlss().getCommodityList());
//                mLifeAdapter.addAll(data.getPzsh().getCommodityList());
//                mHotAdapter.notifyDataSetChanged();
//                mFashionAdapter.notifyDataSetChanged();
//                mLifeAdapter.notifyDataSetChanged();
//            }
//        });
//
//        viewModel.fragDataShare.observe(this, new Observer<Message>() {
//            @Override
//            public void onChanged(Message message) {
//                if (message.what == 100) {//提示我的页面发送过来的消息，实现数据共享
//                    UIUtils.showToastSafe((String) message.obj);
//                }
//            }
//        });
}
