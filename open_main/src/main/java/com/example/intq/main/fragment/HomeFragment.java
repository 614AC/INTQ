package com.example.intq.main.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.intq.common.bean.Course;
import com.example.intq.common.util.Constant;
import com.example.intq.main.databinding.FragHomeBinding;
import com.example.intq.main.vm.HomeTabViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.example.intq.main.adapter.HomeTabAdapter;
import com.example.intq.main.R;
import com.example.intq.common.util.UIUtils;
import com.example.intq.common.core.WDFragment;

import java.util.List;
import java.util.Objects;

public class HomeFragment extends WDFragment<HomeTabViewModel, FragHomeBinding> {
    private HomeTabAdapter mTabAdapter;
    private int mCurrentIndex;

    @Override
    protected HomeTabViewModel initFragViewModel() {
        return new HomeTabViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView(Bundle bundle) {
        //Adapter
        mTabAdapter = new HomeTabAdapter(getChildFragmentManager());
        viewModel.courseList.observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                mTabAdapter.setList(courses);
                binding.tabItems.selectTab(binding.tabItems.getTabAt(0));
            }
        });
        binding.tabItems.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                mCurrentIndex = tab.getPosition();

                TextView textView = getTabTextView(mCurrentIndex);
                textView.setTextAppearance(R.style.tab_selected_style);
            }

            @Override
            public void onTabUnselected(Tab tab) {
                TextView textView = getTabTextView(mCurrentIndex);
                textView.setTextAppearance(R.style.tab_unselected_style);
            }

            @Override
            public void onTabReselected(Tab tab) {
            }
        });
        //Tab相关设置
        binding.innerViewPager.setAdapter(mTabAdapter);
        binding.tabItems.setupWithViewPager(binding.innerViewPager);
        //Tab编辑导航
        binding.tabConfig.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            bundle1.putIntArray("courseIndices", Course.course2Integer(getFragViewModel().courseList.getValue()));
            intentForResultByRouter(Constant.ACTIVITY_URL_TAB_CONFIG, bundle1, Constant.REQ_TAB_CONFIG);
        });
        //搜索栏设置
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
                if (text != null && text.length() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("keyword", text);
                    int courseIndex = mTabAdapter.mFragments.get(mCurrentIndex).getCourseIndex();
                    bundle.putCharSequence("course", Course.getNameEng(courseIndex));
                    intentByRouter(Constant.ACTIVITY_URL_SEARCH, bundle);
                }
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });
    }

    private TextView getTabTextView(int index) {
        return (TextView) (((LinearLayout) ((LinearLayout)
                binding.tabItems.getChildAt(0)).getChildAt(index)).getChildAt(1));
    }

    private void expand() {
        //设置伸展状态时的布局
        binding.searchBar.setHint("搜索已光翼展开");

        //设置动画
        MarginLayoutParams searchBarLayoutParams = (MarginLayoutParams) binding.searchBarCard.getLayoutParams();
        searchBarLayoutParams.topMargin = binding.searchTitleBar.getHeight();
        searchBarLayoutParams.width = LayoutParams.MATCH_PARENT;
        binding.searchBarCard.setLayoutParams(searchBarLayoutParams);
        beginDelayedTransition(binding.fragHome, 0, 500);
        beginDelayedAlphaTransition(binding.searchTitleBar, 0, 1, 500);
    }

    private void reduce() {
        // 设置收缩状态时的布局
        binding.searchBar.setPlaceHolder("开始搜索");

        //设置动画
        MarginLayoutParams searchBarLayoutParams = (MarginLayoutParams) binding.searchBarCard.getLayoutParams();
        searchBarLayoutParams.width = UIUtils.getScreenWidth(getActivity()) * 3 / 4;
        searchBarLayoutParams.topMargin = 0;
        binding.searchBarCard.setLayoutParams(searchBarLayoutParams);
        beginDelayedTransition(binding.fragHome, 0, 500);
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
