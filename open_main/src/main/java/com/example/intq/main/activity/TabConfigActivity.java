package com.example.intq.main.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ActivityTabConfigBinding;
import com.example.intq.main.fragment.TabConfigFragment;
import com.example.intq.main.utils.TabConfigDataProvider;
import com.example.intq.main.vm.EmptyViewModel;

@Route(path = Constant.ACTIVITY_URL_TAB_CONFIG)
public class TabConfigActivity extends WDActivity<EmptyViewModel, ActivityTabConfigBinding> {
    private TabConfigDataProvider mDataProvider;
    private TabConfigFragment mTabConfigFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab_config;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTabConfigFragment = new TabConfigFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.tab_config_container, mTabConfigFragment)
                .commit();

        Intent intent = getIntent();
        mDataProvider = new com.example.intq.main.utils.TabConfigDataProvider(intent.getIntArrayExtra("courseIndices"));

        binding.tabConfigBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnCourseList();
            }
        });
    }

    private void returnCourseList() {
        Intent intent = new Intent();
        intent.putExtra("courseIndices", mDataProvider.dump());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        returnCourseList();
        super.onBackPressed();
    }

    public com.example.intq.main.utils.TabConfigDataProvider getDataProvider() {
        return mDataProvider;
    }
}