package com.example.intq.main.fragment;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intq.common.bean.instance.Instance;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.recycleview.SpacingItemDecoration;
import com.example.intq.main.R;
import com.example.intq.main.adapter.InstanceAdapter;
import com.example.intq.main.databinding.FragInstanceItemBinding;
import com.example.intq.main.vm.InstanceItemViewModel;

import java.util.List;

public class InstanceListFragment extends WDFragment<InstanceItemViewModel, FragInstanceItemBinding> {

    private InstanceAdapter adapter;


    @Override
    protected InstanceItemViewModel initFragViewModel() {
        return new InstanceItemViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_instance_item;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        adapter = new InstanceAdapter();

        binding.instanceList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.instanceList.addItemDecoration(new SpacingItemDecoration(30));
        binding.instanceList.setAdapter(adapter);

        viewModel.InstanceList.observe(this, new Observer<List<Instance>>() {
            @Override
            public void onChanged(List<Instance> Entities) {
                adapter.addAll(Entities);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
