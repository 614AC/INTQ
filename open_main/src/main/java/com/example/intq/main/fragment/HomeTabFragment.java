package com.example.intq.main.fragment;

import android.os.Bundle;
import android.os.Handler;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.intq.common.bean.Course;
import com.example.intq.common.core.WDFragment;
import com.example.intq.main.R;
import com.example.intq.main.databinding.FragHomeTabBinding;
import com.example.intq.main.vm.HomeViewModel;

public class HomeTabFragment extends WDFragment<HomeViewModel, FragHomeTabBinding> {
    private int mCourseIndex;
    private int mCnt;

    @Override
    protected HomeViewModel initFragViewModel() {
        return new HomeViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home_tab;
    }

    @Override
    protected void initView(Bundle bundle) {
        bundle = getArguments();
        mCourseIndex = bundle.getInt("courseIndex");
        String text = Course.getNameChi(mCourseIndex) + "\n";
        for (int i = 0; i < 6; ++i)
            text += text;
        binding.txtContent.setText(text);

        binding.homeTabRefresh.setOnRefreshListener(() -> {
            String oldText = (String) binding.txtContent.getText();
            binding.txtContent.setText(++mCnt + oldText);

            // Do async call or whatever to get the new data
            // Update your data e.g:
            //    - RecylerView: update the data in your adapter and call 'notifyDataSetChanged'
            //    - fixed views: update the field
            // Call `pullToRefresh.setRefreshing(false);`
            new Handler().postDelayed(() -> binding.homeTabRefresh.setRefreshing(false), 5000);

        });
    }
}
