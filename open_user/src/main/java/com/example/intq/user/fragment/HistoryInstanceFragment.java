package com.example.intq.user.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.intq.common.bean.HistoryItem;
import com.example.intq.common.bean.StarItem;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.recycleview.SpacingItemDecoration;
import com.example.intq.user.R;
import com.example.intq.user.adapter.HistoryItemAdapter;
import com.example.intq.user.databinding.FragHistoryItemBinding;
import com.example.intq.user.vm.HistoryItemViewModel;

import java.util.List;

public class HistoryInstanceFragment extends WDFragment<HistoryItemViewModel, FragHistoryItemBinding> {
    private HistoryItemAdapter adapter;
    private int lastVis;

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
        adapter.setOnItemClickListener((view, position) -> {
            HistoryItem item = adapter.getItem(position);
            ARouter.getInstance().build(Constant.ACTIVITY_URL_INSTANCE)
                    .withString("inst_name", item.getLabel())
                    .withString("course", item.getCourse())
                    .withString("uri", item.getUri())
                    .navigation();
        });



        binding.historyList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.historyList.addItemDecoration(new SpacingItemDecoration(30));
        binding.historyList.setAdapter(adapter);

        binding.historyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(linearLayoutManager != null){
                    lastVis = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    if(lastVis == linearLayoutManager.getItemCount() - 1)
                        viewModel.loadMore();
                }
            }
        });

        viewModel.instanceHistoryList.observe(this, new Observer<List<HistoryItem>>() {
            @Override
            public void onChanged(List<HistoryItem> historyItems) {
                adapter.clear();
                adapter.addAll(historyItems);
                adapter.add(new HistoryItem());
                adapter.notifyDataSetChanged();

                if(viewModel.fromResume.get()){
                    if(lastVis >= adapter.getItemCount() - 1)
                        lastVis = adapter.getItemCount() - 1;
                    binding.historyList.scrollToPosition(lastVis);
                    viewModel.fromResume.set(false);
                }
            }
        });

        viewModel.fail.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    adapter.setFooterFail();
                else
                    adapter.setFooterLoading();
                adapter.notifyItemChanged(adapter.getItemCount() - 1);
            }
        });

        viewModel.firstLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean)
                    binding.historyLoading.hide();
            }
        });
    }
}
