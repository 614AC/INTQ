package com.example.intq.main.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ActivityCustomizedTestOpeningBinding;
import com.example.intq.main.vm.CustomizedTestOpeningViewModel;

@Route(path = Constant.ACTIVITY_CUSTOMIZED_OPENING)
public class CustomizedTestOpeningActivity extends WDActivity<CustomizedTestOpeningViewModel, ActivityCustomizedTestOpeningBinding> {

    public String[] courses = new String[]{"语文","数学","英语","物理","化学","生物","政治","历史","地理"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customized_test_opening;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        NumberPicker numberPicker = findViewById(R.id.limit_picker);
        numberPicker.setMinValue(5);
        numberPicker.setMaxValue(20);
        numberPicker.setValue(10);
        binding.coursePicker.setMinValue(0);
        binding.coursePicker.setMaxValue(8);
//        binding.coursePicker.setFormatter(new NumberPicker.Formatter() {
//            @Override
//            public String format(int i) {
//                return courses[i];
//            }
//        });
        binding.coursePicker.setDisplayedValues(courses);
        binding.coursePicker.setValue(0);
        binding.coursePicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
