package com.example.intq.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AlphaAnimation;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.intq.common.bean.Course;
import com.example.intq.common.util.Constant;
import com.example.intq.main.databinding.FragHomeBinding;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.example.intq.main.adapter.HomeTabAdapter;
import com.example.intq.main.R;
import com.example.intq.main.vm.HomeViewModel;
import com.example.intq.common.util.UIUtils;
import com.example.intq.common.core.WDFragment;

import java.util.List;

public class HomeFragment extends WDFragment<HomeViewModel, FragHomeBinding> {
    private HomeTabAdapter mTabAdapter;

    @Override
    protected HomeViewModel initFragViewModel() {
        return new HomeViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView(Bundle bundle) {
        mTabAdapter = new HomeTabAdapter(getChildFragmentManager());
        viewModel.courseList.observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                mTabAdapter.setList(courses);
            }
        });

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

        binding.tabConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putIntArray("courseIndices", Course.course2Integer(getFragViewModel().courseList.getValue()));
                intentForResultByRouter(Constant.ACTIVITY_URL_TAB_CONFIG, bundle, Constant.REQ_TAB_CONFIG);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constant.REQ_TAB_CONFIG) {
            getFragViewModel().courseList.setValue(Course.integer2Course(data.getIntArrayExtra("courseIndices")));
        }
    }
}
