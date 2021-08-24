package com.vc.wd.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vc.wd.common.bean.Course;
import com.vc.wd.common.core.WDFragment;
import com.vc.wd.main.R;
import com.vc.wd.main.adapter.HomeTabAdapter;
import com.vc.wd.main.databinding.HomeTabBinding;
import com.vc.wd.main.vm.HomeViewModel;

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
