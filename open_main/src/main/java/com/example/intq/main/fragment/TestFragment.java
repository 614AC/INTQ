package com.example.intq.main.fragment;

import android.os.Bundle;

import com.example.intq.common.core.WDFragment;
import com.example.intq.main.R;
import com.example.intq.main.databinding.FragTestBinding;
import com.example.intq.main.vm.TestViewModel;

public class TestFragment extends WDFragment<TestViewModel, FragTestBinding> {
    @Override
    protected TestViewModel initFragViewModel() {
        return new TestViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
