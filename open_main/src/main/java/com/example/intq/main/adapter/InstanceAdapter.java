package com.example.intq.main.adapter;

import androidx.databinding.ViewDataBinding;

import com.example.intq.common.bean.Instance;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.main.R;
import com.example.intq.main.databinding.LayoutInstanceItemBinding;

public class InstanceAdapter extends WDRecyclerAdapter<Instance> {
    @Override
    protected int getLayoutId() {
        return R.layout.layout_instance_item;
    }

    @Override
    protected void bindView(ViewDataBinding binding, Instance item, int position) {
        LayoutInstanceItemBinding binding1 = (LayoutInstanceItemBinding) binding;
        binding1.instTitle.setText(item.getName());
    }
}
