package com.example.intq.main.fragment;

import android.os.Bundle;

import com.example.intq.common.bean.Course;
import com.example.intq.common.core.WDFragment;
import com.example.intq.main.R;
import com.example.intq.main.adapter.HomeTabAdapter;
import com.example.intq.main.databinding.HomeTabBinding;
import com.example.intq.main.vm.HomeViewModel;

public class HomeTabFragment extends WDFragment<HomeViewModel, HomeTabBinding> {
    private int mCourseIndex;

    @Override
    protected HomeViewModel initFragViewModel() {
        return new HomeViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_tab;
    }

    @Override
    protected void initView(Bundle bundle) {
        bundle = getArguments();
        mCourseIndex = bundle.getInt("courseIndex");
        String text = Course.getNameChi(mCourseIndex) + "\n";
        for (int i = 0; i < 6; ++i)
            text += text;
        binding.txtContent.setText(text);
    }
}
