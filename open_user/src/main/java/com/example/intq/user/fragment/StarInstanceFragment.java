package com.example.intq.user.fragment;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intq.common.bean.StarItem;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.recycleview.SpacingItemDecoration;
import com.example.intq.user.R;
import com.example.intq.user.adapter.StarItemAdapter;
import com.example.intq.user.databinding.FragStarItemBinding;
import com.example.intq.user.vm.StarItemViewModel;

import java.util.List;

public class StarInstanceFragment extends WDFragment<StarItemViewModel, FragStarItemBinding> {

    private StarItemAdapter adapter;


    @Override
    protected StarItemViewModel initFragViewModel() {
        return new StarItemViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_star_item;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        adapter = new StarItemAdapter();

        binding.starList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.starList.addItemDecoration(new SpacingItemDecoration(30));
        binding.starList.setAdapter(adapter);

        viewModel.instanceStarList.observe(this, new Observer<List<StarItem>>() {
            @Override
            public void onChanged(List<StarItem> starItems) {
                adapter.clear();
                adapter.addAll(starItems);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
