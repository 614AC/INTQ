package com.example.intq.user.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.intq.common.bean.StarItem;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.Constant;
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
        adapter.setOnItemClickListener((view, position) -> {
            StarItem item = adapter.getItem(position);
            ARouter.getInstance().build(Constant.ACTIVITY_URL_INSTANCE)
                    .withString("inst_name", item.getLabel())
                    .withString("course", item.getCourse())
                    .withString("uri", item.getUri())
                    .navigation();
        });
        binding.starList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.starList.addItemDecoration(new SpacingItemDecoration(30));
        binding.starList.setAdapter(adapter);

        binding.starList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.getItemCount() - 1){
                    viewModel.updateStar();
                }
            }
        });

        viewModel.instanceStarList.observe(this, new Observer<List<StarItem>>() {
            @Override
            public void onChanged(List<StarItem> starItems) {
                adapter.clear();
                adapter.addAll(starItems);
                adapter.add(new StarItem());
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.fail.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    adapter.setFooterFail();
                }
                else {
                    adapter.setFooterLoading();
                }
                adapter.notifyItemChanged(adapter.getItemCount() - 1);
            }
        });

        viewModel.firstLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean)
                    binding.starLoading.smoothToHide();
            }
        });
    }

}
