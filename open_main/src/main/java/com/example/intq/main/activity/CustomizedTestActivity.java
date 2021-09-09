package com.example.intq.main.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.bean.ExtraExercise;
import com.example.intq.common.bean.ExtraOption;
import com.example.intq.common.bean.exercise.SingleExerciseResult;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.recycleview.SpacingItemDecoration;
import com.example.intq.main.R;
import com.example.intq.main.adapter.ChoiceAdapter;
import com.example.intq.main.databinding.ActivityCustomizedTestBinding;
import com.example.intq.main.vm.CustomizedTestViewModel;

import java.util.List;

@Route(path = Constant.ACTIVITY_CUSTOMIZED)
public class CustomizedTestActivity extends WDActivity<CustomizedTestViewModel, ActivityCustomizedTestBinding> {
    @Autowired
    public int limit;
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
        binding.retryBtn.setClickable(false);
        choiceAdapter = new ChoiceAdapter();
        choiceAdapter.setOnItemClickListener((view, position) -> {
            viewModel.choose(position);
        });
        binding.optionsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.optionsRecycler.addItemDecoration(new SpacingItemDecoration(30));

        binding.progressDown.setText(String.valueOf(limit));

        binding.questionBody.setMovementMethod(new ScrollingMovementMethod());
        binding.submitBtn.setVisibility(View.INVISIBLE);
        binding.progressView.setVisibility(View.INVISIBLE);
        binding.failIcon.setVisibility(View.INVISIBLE);
        binding.questionBody.setVisibility(View.INVISIBLE);
        binding.optionsRecycler.setVisibility(View.INVISIBLE);
        binding.previousOne.setVisibility(View.INVISIBLE);
        binding.nextOne.setVisibility(View.INVISIBLE);
        binding.optionsRecycler.setAdapter(choiceAdapter);
        viewModel.limit.setValue(limit);
        viewModel.course.setValue(course);
        viewModel.getCustomizedExercise();

        viewModel.presentQuestion.observe(this, new Observer<ExtraExercise>() {
            @Override
            public void onChanged(ExtraExercise extraExercise) {
                binding.questionBody.setText(extraExercise.getBody());
//                choiceAdapter.clear();
//                choiceAdapter.addAll(extraExercise.getOptions());
                choiceAdapter.update(extraExercise.getOptions());
                choiceAdapter.notifyDataSetChanged();

                if (loading) {
//                    binding.generateLoading.setVisibility(View.INVISIBLE);
                    binding.generateIcon.smoothToHide();
                    binding.generateLoading.setVisibility(View.INVISIBLE);
                    binding.questionBody.setVisibility(View.VISIBLE);
                    binding.optionsRecycler.setVisibility(View.VISIBLE);
                    binding.progressView.setVisibility(View.VISIBLE);
                    binding.submitBtn.setVisibility(View.VISIBLE);
                    loading = false;
                }
            }
        });
        viewModel.qIndex.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer <= 0) {
                    binding.previousOne.setVisibility(View.INVISIBLE);
                }
                else
                    binding.previousOne.setVisibility(View.VISIBLE);
                if (integer >= limit - 1) {
                    binding.nextOne.setVisibility(View.INVISIBLE);
                }
                else
                    binding.nextOne.setVisibility(View.VISIBLE);
            }
        });
        viewModel.qMode.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    choiceAdapter.setOnItemClickListener((view, position) -> {
                        viewModel.choose(position);
                    });
                }
                else {
                    choiceAdapter.removeOnItemClickListener();
                    binding.submitBtn.setText("退出");
                }
            }
        });

        viewModel.fail.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.generateIcon.smoothToHide();
                    binding.generateLoading.setText("没有找到匹配套题\n单击此处重试");
                    binding.failIcon.setVisibility(View.VISIBLE);
                    binding.retryBtn.setClickable(true);
                }
                else {
                    binding.generateIcon.smoothToShow();
                    binding.generateLoading.setText("正在为您精选试题");
                    binding.failIcon.setVisibility(View.INVISIBLE);
                }
            }
        });

        viewModel.corrects.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressName.setText("正确率");
                binding.progressCircle.setProgress(0, 300);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressCircle.setProgress((int)((integer) * 1.0 / limit * 100), 200);
                    }
                }, 300);
                binding.progressUp.setText(String.valueOf(integer));
            }
        });

        viewModel.progress.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == limit - 1)
                    binding.progressCircle.setProgress(100, 300);
                else
                    binding.progressCircle.setProgress((int)((integer + 1) * 1.0 / limit * 100), 300);
                binding.progressUp.setText(String.valueOf(integer + 1));
            }
        });

        viewModel.quit.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    finish();
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
