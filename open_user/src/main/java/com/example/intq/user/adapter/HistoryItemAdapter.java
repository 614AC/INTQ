package com.example.intq.user.adapter;

import androidx.databinding.ViewDataBinding;

import com.example.intq.common.bean.HistoryItem;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.user.R;
import com.example.intq.user.databinding.LayoutHistoryItemBinding;

public class HistoryItemAdapter extends WDRecyclerAdapter<HistoryItem> {
    @Override
    protected int getLayoutId() {
        return R.layout.layout_history_item;
    }

    @Override
    protected void bindView(ViewDataBinding binding, HistoryItem item, int position) {
        LayoutHistoryItemBinding binding1 = (LayoutHistoryItemBinding) binding;
        binding1.historyTitle.setText(item.getTitle());
        binding1.historyTime.setText(item.getVis().toString());
    }

}
