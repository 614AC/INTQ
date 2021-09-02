package com.example.intq.main.fragment;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.intq.common.bean.Course;
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
    private HomeListInstanceAdapter mAdapter;
    private MutableLiveData<Integer> mCourseIndex = new MutableLiveData<>();

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
        mAdapter = new HomeListInstanceAdapter();
        mAdapter.setOnItemClickListener((view, position) -> {
            InstListNode node = mAdapter.getItem(position);
            ARouter.getInstance().build(Constant.ACTIVITY_URL_INSTANCE)
                    .withString("inst_name", node.getLabel())
                    .withString("course", Course.getNameEng(mCourseIndex.getValue()))
                    .withString("uri", node.getUri())
                    .navigation();
        });
        binding.tabRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.tabRecyclerView.addItemDecoration(new SpacingItemDecoration(30));
        binding.tabRecyclerView.setAdapter(mAdapter);

        bundle = getArguments();
        mCourseIndex.setValue(bundle.getInt("courseIndex"));
        mCourseIndex.observe(this, (courseIndex) -> {
            refresh();
        });
        viewModel.searching.observe(this, searching -> {
            binding.homeTabRefresh.setRefreshing(searching);
        });
        viewModel.randomInstList.observe(this, new Observer<InstList>() {
            @Override
            public void onChanged(InstList instList) {
                mAdapter.clear();
                if (instList == null || instList.getInstList().size() == 0) {
                    UIUtils.showToastSafe("网络错误~");
                    mAdapter.add(new InstListNode("空空如也", "搜索", ""));
                } else {
                    mAdapter.addAll(instList.getInstList());
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        binding.homeTabRefresh.setOnRefreshListener(() -> {
            refresh();
        });
        refresh();
    }

    public int getCourseIndex() {
        try {
            return mCourseIndex.getValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public void setCourseIndex(int index) {
        int oldIndex = -1;
        try {
            oldIndex = mCourseIndex.getValue();
        } catch (Exception e) {
        }
        if (oldIndex != index) {
            List<InstListNode> node = new ArrayList<>();
            node.add(new InstListNode("空空如也", "搜索", ""));
            InstList instList = new InstList(node);
            viewModel.randomInstList.setValue(instList);

            mCourseIndex.setValue(index);
        }
    }

    private void refresh() {
        viewModel.updateRandomInstList(20, Course.getNameEng(mCourseIndex.getValue()));
    }
}
