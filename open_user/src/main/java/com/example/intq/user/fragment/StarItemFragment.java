package com.example.intq.user.fragment;

import android.os.Bundle;

import com.example.intq.common.core.WDFragment;
import com.example.intq.user.R;
import com.example.intq.user.databinding.FragStarItemBinding;
import com.example.intq.user.vm.StarItemViewModel;

public class StarItemFragment extends WDFragment<StarItemViewModel, FragStarItemBinding> {
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

    }
}
