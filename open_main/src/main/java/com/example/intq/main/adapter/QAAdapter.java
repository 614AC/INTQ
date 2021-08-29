package com.example.intq.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.intq.common.bean.QAChat;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.main.R;
import com.example.intq.main.databinding.LayoutChatLeftBinding;
import com.example.intq.main.databinding.LayoutChatRightBinding;

public class QAAdapter extends WDRecyclerAdapter<QAChat> {

    private Boolean question;

    @Override
    protected int getLayoutId() {
        if (question)
            return R.layout.layout_chat_right;
        else
            return R.layout.layout_chat_left;
    }
    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        if(viewType == 0){
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_chat_right,
                    parent, false);
        }
        else
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_chat_left,
                    parent, false);

        initBindingField(parent,binding);
        return new MyHodler(binding.getRoot());
    }


    /**
     *
     * @param position position
     * @return int: o for question and 1 for answer
     */
    @Override
    public int getItemViewType(int position) {
        if(mList.get(position).getQuestion()){
            return 0;
        }
        else
            return 1;
    }

    @Override
    protected void bindView(ViewDataBinding binding, QAChat item, int position) {
        this.question = item.getQuestion();
        if (item.getQuestion()) {
            LayoutChatRightBinding rightBinding = (LayoutChatRightBinding) binding;
            rightBinding.question.setText(item.getContent());

        } else {
            LayoutChatLeftBinding leftBinding = (LayoutChatLeftBinding) binding;
            leftBinding.answer.setText(item.getContent());
        }

    }
}
