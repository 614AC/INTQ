package com.example.intq.main.adapter;

import androidx.databinding.ViewDataBinding;

import com.example.intq.common.bean.instance.InstListNode;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ListInstanceBinding;

public class ListInstanceAdapter extends WDRecyclerAdapter<InstListNode> {
    @Override
    protected int getLayoutId() {
        return R.layout.list_instance;
    }

    @Override
    protected void bindView(ViewDataBinding binding, InstListNode item, int position) {
        ListInstanceBinding viewBinding = (ListInstanceBinding) binding;
        viewBinding.instLabel.setText(item.getLabel());
        if (item.getCategory().length() > 0)
            viewBinding.instCategory.setText(String.format("[%s]", item.getCategory()));
    }
}