package com.example.intq.main.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.bean.QAChat;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.main.R;
import com.example.intq.main.adapter.QAAdapter;
import com.example.intq.main.databinding.ActivityQABinding;
import com.example.intq.main.vm.QAViewModel;

@Route(path = Constant.ACTIVITY_URL_QA)
public class QAActivity extends WDActivity<QAViewModel, ActivityQABinding> {

    private static final String[] m={"语文","数学","英语","物理","化学", "生物", "政治", "历史", "地理"};
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private QAAdapter qaAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_q_a;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        spinner = (Spinner) findViewById(R.id.course_spinner);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        qaAdapter = new QAAdapter();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        binding.lvChatDialog.setLayoutManager(manager);
        binding.lvChatDialog.setAdapter(qaAdapter);

        viewModel.qaChat.observe(this, new Observer<QAChat>() {
            @Override
            public void onChanged(QAChat qaChat) {
                qaAdapter.add(qaChat);
                qaAdapter.notifyDataSetChanged();
                binding.lvChatDialog.smoothScrollToPosition(qaAdapter.getItemCount() - 1);
            }
        });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
