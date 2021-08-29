package com.example.intq.main.fragment;

import android.os.Bundle;

import com.example.intq.common.core.WDFragment;
import com.example.intq.main.R;
import com.example.intq.main.databinding.FragMultiToolBinding;
import com.example.intq.main.vm.MultiToolViewModel;

public class MultiToolFragment extends WDFragment<MultiToolViewModel, FragMultiToolBinding> {
    @Override
    protected MultiToolViewModel initFragViewModel() {
        return new MultiToolViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_multi_tool;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
