package com.example.intq.main.adapter;

import androidx.databinding.ViewDataBinding;

import com.example.intq.common.bean.instance.PropertyNode;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.main.R;
import com.example.intq.main.databinding.LayoutInstanceItemBinding;

import java.util.List;

public class InstanceAdapter extends WDRecyclerAdapter<PropertyNode> {
    @Override
    protected int getLayoutId() {
        return R.layout.layout_instance_item;
    }

    @Override
    protected void bindView(ViewDataBinding binding, PropertyNode item, int position) {
        LayoutInstanceItemBinding binding1 = (LayoutInstanceItemBinding) binding;
        binding1.instTitle.setText(item.getPredicateLabel());
        binding1.instContent.setText(item.getObject());
    }
}
