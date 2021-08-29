package com.example.intq.user.adapter;

import androidx.databinding.ViewDataBinding;

import com.example.intq.common.bean.StarItem;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.user.R;
import com.example.intq.user.databinding.LayoutStarItemBinding;

public class StarItemAdapter extends WDRecyclerAdapter<StarItem> {
    @Override
    protected int getLayoutId() {
        return R.layout.layout_star_item;
    }

    @Override
    protected void bindView(ViewDataBinding binding, StarItem item, int position) {
        LayoutStarItemBinding binding1 = (LayoutStarItemBinding) binding;
        binding1.starTitle.setText(item.getTitle());
    }
}
