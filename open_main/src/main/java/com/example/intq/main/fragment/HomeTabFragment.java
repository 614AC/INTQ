package com.example.intq.main.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.intq.common.bean.Course;
import com.example.intq.common.bean.instance.HomeTabInfo;
import com.example.intq.common.bean.instance.InstList;
import com.example.intq.common.bean.instance.InstListNode;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.common.util.recycleview.SpacingItemDecoration;
import com.example.intq.main.R;
import com.example.intq.main.adapter.HomeListInstanceAdapter;
import com.example.intq.main.adapter.ListInstanceAdapter;
import com.example.intq.main.databinding.FragHomeTabBinding;
import com.example.intq.main.vm.HomeTabViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeTabFragment extends WDFragment<HomeTabViewModel, FragHomeTabBinding> {
    private int mCourseIndex;
    private HomeListInstanceAdapter mAdapter;

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
        if (mAdapter == null)
            mAdapter = new HomeListInstanceAdapter();
        mAdapter.setOnItemClickListener((view, position) -> {
            InstListNode node = mAdapter.getItem(position);
            if (node.getLabel().equals("空空如也") && node.getUri().equals("") && node.getCategory().equals(""))
                return;
            ARouter.getInstance().build(Constant.ACTIVITY_URL_INSTANCE)
                    .withString("inst_name", node.getLabel())
                    .withString("course", Course.getNameEng(mCourseIndex))
                    .withString("uri", node.getUri())
                    .navigation();
        });
        binding.tabRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.tabRecyclerView.addItemDecoration(new SpacingItemDecoration(30));
        binding.tabRecyclerView.setAdapter(mAdapter);

        viewModel.searching.observe(this, searching -> {
            binding.homeTabRefresh.setRefreshing(searching);
        });
        binding.homeTabRefresh.setOnRefreshListener(this::refresh);
        refresh();
    }

    public int getCourseIndex() {
        return mCourseIndex;
    }

    public void setIndexAndInstList(int courseIndex, InstList instList) {
        mCourseIndex = courseIndex;
        if (mAdapter == null)
            mAdapter = new HomeListInstanceAdapter();
        mAdapter.clear();
        if (instList == null || instList.getInstList().size() == 0) {
            List<InstListNode> nodes = new ArrayList<>();
            nodes.add(new InstListNode("空空如也", "", ""));
            instList = new InstList(nodes);
        }
        mAdapter.addAll(instList.getInstList());
        mAdapter.notifyDataSetChanged();
    }

    private void refresh() {
        viewModel.updateRandomInstList(20, mCourseIndex);
    }
}
