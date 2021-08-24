package com.vc.wd.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.vc.wd.common.bean.Course;
import com.vc.wd.common.core.WDActivity;
import com.vc.wd.common.util.Constant;
import com.vc.wd.main.R;
import com.vc.wd.main.databinding.ActivityTabConfigBinding;
import com.vc.wd.main.vm.EmptyViewModel;
import com.vc.wd.main.vm.TestViewModel;

import java.util.List;

@Route(path = Constant.ACTIVITY_URL_TAB_CONFIG)
public class TabConfigActivity extends WDActivity<EmptyViewModel, ActivityTabConfigBinding> {
    private List<Course> mCouseList;

    private void returnCourseList() {
        Intent intent = new Intent();
        intent.putExtra("courseIndices", Course.course2Integer(mCouseList));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab_config;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mCouseList = Course.integer2Course(intent.getIntArrayExtra("courseIndices"));
        mCouseList.clear();
        mCouseList.add(new Course(0));
        binding.tabConfigBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnCourseList();
            }
        });
    }

    @Override
    public void onBackPressed() {
        returnCourseList();
        super.onBackPressed();
    }
}