package com.example.intq.main.fragment;

import android.os.Bundle;

import androidx.lifecycle.Observer;

import com.example.intq.common.bean.Course;
import com.example.intq.common.bean.instance.InstList;
import com.example.intq.common.bean.instance.InstListNode;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.R;
import com.example.intq.main.databinding.FragHomeTabBinding;
import com.example.intq.main.vm.HomeTabViewModel;

public class HomeTabFragment extends WDFragment<HomeTabViewModel, FragHomeTabBinding> {
    private int mCourseIndex;
    private int mCnt;

    @Override
    protected HomeTabViewModel initFragViewModel() {
        return new HomeTabViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home_tab;
    }

    @Override
    protected void initView(Bundle bundle) {
        bundle = getArguments();
        mCourseIndex = bundle.getInt("courseIndex");
        viewModel.searching.observe(this, searching -> {
            if (!searching)
                binding.homeTabRefresh.setRefreshing(false);
        });
        viewModel.randomInstList.observe(this, new Observer<InstList>() {
            @Override
            public void onChanged(InstList instList) {
                String show = Course.getNameChi(mCourseIndex) + "\n";
                if (instList == null) {
                    UIUtils.showToastSafe("网络错误~");
                    show += "空空如也";
                } else {
                    for (InstListNode node : instList.getInstList())
                        show += String.format("label:%s\nuri:%s\n",
                                node.getLabel(), node.getUri());
                }
                binding.txtContent.setText(show);
            }
        });

        binding.homeTabRefresh.setOnRefreshListener(() -> {
            refresh();
        });
        refresh();
    }

    private void refresh() {
        binding.homeTabRefresh.setRefreshing(true);
        viewModel.updateRandomInstList(20, Course.getNameEng(mCourseIndex));
    }
}
