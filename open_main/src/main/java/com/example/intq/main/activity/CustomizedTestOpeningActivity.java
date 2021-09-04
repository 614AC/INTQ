package com.example.intq.main.activity;

import android.os.Bundle;
import android.widget.NumberPicker;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ActivityCustomizedTestOpeningBinding;
import com.example.intq.main.vm.CustomizedTestOpeningViewModel;

@Route(path = Constant.ACTIVITY_CUSTOMIZED_OPENING)
public class CustomizedTestOpeningActivity extends WDActivity<CustomizedTestOpeningViewModel, ActivityCustomizedTestOpeningBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customized_test_opening;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        NumberPicker numberPicker = findViewById(R.id.limit_picker);
        numberPicker.setMinValue(5);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(5);
    }
}
