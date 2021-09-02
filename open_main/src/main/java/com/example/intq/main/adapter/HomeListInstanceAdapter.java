package com.example.intq.main.adapter;

import androidx.databinding.ViewDataBinding;

import com.example.intq.common.bean.instance.InstListNode;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.main.R;
import com.example.intq.main.databinding.HomeListInstanceBinding;
import com.example.intq.main.databinding.ListInstanceBinding;

public class HomeListInstanceAdapter extends ListInstanceAdapter {
    @Override
    protected int getLayoutId() {
        return R.layout.home_list_instance;
    }

    @Override
    protected void bindView(ViewDataBinding binding, InstListNode item, int position) {
        HomeListInstanceBinding viewBinding = (HomeListInstanceBinding) binding;
        viewBinding.instLabel.setText(item.getLabel());
    }
}