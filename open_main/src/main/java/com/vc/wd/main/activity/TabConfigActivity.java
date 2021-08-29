package com.vc.wd.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.vc.wd.common.bean.Course;
import com.vc.wd.common.core.WDActivity;
import com.vc.wd.common.util.Constant;
import com.vc.wd.main.R;
import com.vc.wd.main.fragment.TabConfigFragment;
import com.vc.wd.main.databinding.ActivityTabConfigBinding;
import com.vc.wd.main.utils.TabConfigDataProvider;
import com.vc.wd.main.vm.EmptyViewModel;

import java.util.HashSet;
import java.util.List;

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
        mDataProvider = new TabConfigDataProvider(intent.getIntArrayExtra("courseIndices"));

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

    public TabConfigDataProvider getDataProvider() {
        return mDataProvider;
    }
}