package com.example.intq.main.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.bean.ExtraExercise;
import com.example.intq.common.bean.ExtraOption;
import com.example.intq.common.bean.exercise.SingleExerciseResult;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.main.R;
import com.example.intq.main.adapter.ChoiceAdapter;
import com.example.intq.main.databinding.ActivityCustomizedTestBinding;
import com.example.intq.main.vm.CustomizedTestViewModel;

import java.util.List;

@Route(path = Constant.ACTIVITY_CUSTOMIZED)
public class CustomizedTestActivity extends WDActivity<CustomizedTestViewModel, ActivityCustomizedTestBinding> {
    @Autowired
    public Integer limit;
    @Autowired
    public String course;

    private ChoiceAdapter choiceAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customized_test;
    }

    private boolean loading = true;

    @Override
    protected void initView(Bundle savedInstanceState) {
        choiceAdapter = new ChoiceAdapter();
        choiceAdapter.setOnItemClickListener((view, position) -> {
            for (ExtraOption option : choiceAdapter.getList()) {
                option.setType(0);
            }
            choiceAdapter.getItem(position).setType(1);
            viewModel.presentQuestion.getValue().setOptions(choiceAdapter.getList());
            choiceAdapter.notifyDataSetChanged();
        });

        binding.questionBody.setVisibility(View.INVISIBLE);
        binding.optionsRecycler.setVisibility(View.INVISIBLE);
        binding.optionsRecycler.setAdapter(choiceAdapter);
        viewModel.limit.setValue(limit);
        viewModel.course.setValue(course);
        viewModel.getCustomizedExercise();

        viewModel.presentQuestion.observe(this, new Observer<ExtraExercise>() {
            @Override
            public void onChanged(ExtraExercise extraExercise) {
                binding.questionBody.setText(extraExercise.getBody());
                choiceAdapter.clear();
                choiceAdapter.addAll(extraExercise.getOptions());

                if (loading) {
                    binding.searchLoading.smoothToHide();
                    binding.questionBody.setVisibility(View.VISIBLE);
                    binding.optionsRecycler.setVisibility(View.VISIBLE);
                    loading = false;
                }
            }
        });
        viewModel.qIndex.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0) {
                    binding.previousOne.setVisibility(View.INVISIBLE);
                }
                if (integer == limit - 1) {
                    binding.nextOne.setVisibility(View.INVISIBLE);
                }
            }
        });
        viewModel.qMode.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    choiceAdapter.setOnItemClickListener((view, position) -> {
                        for (ExtraOption option : choiceAdapter.getList()) {
                            option.setType(0);
                        }
                        choiceAdapter.getItem(position).setType(1);
                        viewModel.presentQuestion.getValue().setOptions(choiceAdapter.getList());
                        choiceAdapter.notifyDataSetChanged();
                    });
                }
                else {
                    choiceAdapter.removeOnItemClickListener();
                }
            }
        });

    }
}
