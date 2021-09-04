package com.example.intq.main.fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.intq.common.bean.exercise.ExerciseOption;
import com.example.intq.common.bean.instance.ContentNode;
import com.example.intq.common.core.WDFragment;
import com.example.intq.main.R;
import com.example.intq.main.databinding.FragExerciseBinding;
import com.example.intq.main.databinding.FragGraphBinding;
import com.example.intq.main.vm.ExerciseViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import com.example.intq.common.bean.exercise.ExerciseNode;
import com.example.intq.main.adapter.MyExpandableAdapter;
public class ExerciseFragment extends WDFragment<ExerciseViewModel, FragExerciseBinding> {

    public static final String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h"};
    /** 数据集合 */
    private List<ExerciseNode> mExpandableModeList;
    /** 适配器 */
    private MyExpandableAdapter mExpandableAdapter;
    /** 可展开的List */
    private ExpandableListView mExpandableListView;

    @Override
    protected ExerciseViewModel initFragViewModel() {
        return new ExerciseViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_exercise;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View exercise = inflater.inflate(R.layout.frag_exercise, container, false);
        mExpandableListView = exercise.findViewById(R.id.act_main_expandable_list_view);
        viewModel.exerciseNodes.observe(this, new Observer<List<ExerciseNode>>() {
            @Override
            public void onChanged(List<ExerciseNode> content) {
                loadData(content);
            }
        });
        return mExpandableListView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("frag -> " + getArguments().getString("inst_name"));
        viewModel.instName.setValue(getArguments().getString("inst_name"));
        viewModel.queryCourse.setValue(getArguments().getString("course"));
        viewModel.queryUri.setValue(getArguments().getString("uri"));

        viewModel.getData();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    public void loadData(List<ExerciseNode> ens) {
        mExpandableModeList = ens;
        // 实例化适配器
        mExpandableAdapter = new MyExpandableAdapter(mExpandableModeList, Objects.requireNonNull(this.getActivity()));
        // 设置适配器
        mExpandableListView.setAdapter(mExpandableAdapter);

        // 子item的监听
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Toast.makeText(MainActivity.this, childPosition + "", Toast.LENGTH_SHORT).show();
                List<ExerciseOption> childDataBeans = mExpandableModeList.get(groupPosition).getChildData();
                ExerciseNode en = mExpandableModeList.get(groupPosition);
                for (int i = 0; i < childDataBeans.size(); i++) {
                    ExerciseOption cleanOption = childDataBeans.get(i);
                    cleanOption.setChildIcon("icon_" + alphabet[i]);
                    childDataBeans.set(i, cleanOption);
                }
                if (en.getAnswer() == childPosition) {
                    ExerciseOption correctOption = childDataBeans.get(childPosition);
                    correctOption.setChildIcon("icon_" + alphabet[childPosition] + "_green");
                    childDataBeans.set(childPosition, correctOption);
                }
                else {
                    ExerciseOption falseOption = childDataBeans.get(childPosition);
                    ExerciseOption correctOption = childDataBeans.get(en.getAnswer());
                    falseOption.setChildIcon("icon_" + alphabet[childPosition] + "_red");
                    correctOption.setChildIcon("icon_" + alphabet[en.getAnswer()] + "_green");
                    childDataBeans.set(childPosition, falseOption);
                    childDataBeans.set(en.getAnswer(), correctOption);
                }
                // 设置数据
                mExpandableAdapter.setExpandableModeList(mExpandableModeList);
                // 通过关闭组item在展开刷新数据
                mExpandableListView.collapseGroup(groupPosition);
                mExpandableListView.expandGroup(groupPosition);
                return false;
            }
        });

        // 打开所有
//        for (int i = 0; i < mExpandableModeList.size(); i++) mExpandableListView.expandGroup(i);
        // 设置默认展开蔬菜组
        // mExpandableListView.expandGroup(1);
    }
}
