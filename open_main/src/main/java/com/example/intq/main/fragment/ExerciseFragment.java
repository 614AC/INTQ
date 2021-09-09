package com.example.intq.main.fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.Locale;
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

    private boolean[] savedCollapse;

    private int[] savedIcon;

    public MutableLiveData<String> shareExercise = new MutableLiveData<>();

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

        mExpandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                long packedPosition = mExpandableListView.getExpandableListPosition(i);
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                StringBuffer str = new StringBuffer();
                str.append(mExpandableModeList.get(groupPosition).getGroupName());
                List<ExerciseOption> options = mExpandableModeList.get(groupPosition).getChildData();
                for(int j = 0; j < options.size(); j++){
                    str.append('\n');
                    str.append(alphabet[j].toUpperCase(Locale.ROOT));
                    str.append('.');
                    str.append(options.get(j).getChildName());
                }
                str.append("\n正确答案：");
                str.append(alphabet[mExpandableModeList.get(groupPosition).getAnswer()].toUpperCase(Locale.ROOT));
                shareExercise.setValue(str.toString());
                return true;
            }
        });

        // 打开所有
//        for (int i = 0; i < mExpandableModeList.size(); i++) mExpandableListView.expandGroup(i);
        // 设置默认展开蔬菜组
        // mExpandableListView.expandGroup(1);
    }

    public void expandAll(){
        savedCollapse = new boolean[mExpandableModeList.size()];
        savedIcon = new int[mExpandableModeList.size()];

        for(int i = 0; i < mExpandableModeList.size(); i++){
            savedCollapse[i] = mExpandableListView.isGroupExpanded(i);
            mExpandableListView.expandGroup(i);

            int icon = -2; // nothing chosen - turn the right answer to green
            List<ExerciseOption> childDataBeans = mExpandableModeList.get(i).getChildData();
            ExerciseNode en = mExpandableModeList.get(i);
            for (int j = 0; j < childDataBeans.size(); j++) {
                ExerciseOption option = childDataBeans.get(j);
                if(option.getChildIcon().contains("_red")){
                    icon = j; // choose the wrong answer
                    option.setChildIcon("icon_" + alphabet[j]);
                    break;
                }
                if(option.getChildIcon().contains("_green")){
                    icon = -1; // choose the right answer
                }
            }
            childDataBeans.get(en.getAnswer()).setChildIcon("icon_" + alphabet[en.getAnswer()] + "_green");
            savedIcon[i] = icon;
        }
    }

    public void reverseExpandAll(){
        for(int i = 0; i < mExpandableModeList.size(); i++){

            int icon = savedIcon[i]; // nothing chosen or only right answer - turn the right answer to green
            List<ExerciseOption> childDataBeans = mExpandableModeList.get(i).getChildData();
            ExerciseNode en = mExpandableModeList.get(i);
            switch (icon){
                case -2:
                    childDataBeans.get(en.getAnswer()).setChildIcon("icon_" + alphabet[en.getAnswer()]);
                    break;
                case -1:
                    break;
                default:
                    childDataBeans.get(icon).setChildIcon("icon_" + alphabet[icon] + "_red");
            }

            if(!savedCollapse[i])
                mExpandableListView.collapseGroup(i);
        }
    }
}
