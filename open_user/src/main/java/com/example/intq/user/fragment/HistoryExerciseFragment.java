package com.example.intq.user.fragment;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intq.common.bean.HistoryItem;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.recycleview.SpacingItemDecoration;
import com.example.intq.user.R;
import com.example.intq.user.adapter.HistoryItemAdapter;
import com.example.intq.user.databinding.FragHistoryItemBinding;
import com.example.intq.user.vm.HistoryItemViewModel;

import java.util.List;

public class HistoryExerciseFragment extends WDFragment<HistoryItemViewModel, FragHistoryItemBinding> {
    private HistoryItemAdapter adapter;


    @Override
    protected HistoryItemViewModel initFragViewModel() {
        return new HistoryItemViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_history_item;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        adapter = new HistoryItemAdapter();

        binding.starList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.starList.addItemDecoration(new SpacingItemDecoration(30));
        binding.starList.setAdapter(adapter);

        viewModel.exerciseHistoryList.observe(this, new Observer<List<HistoryItem>>() {
            @Override
            public void onChanged(List<HistoryItem> historyItemsItems) {
                adapter.addAll(historyItemsItems);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
