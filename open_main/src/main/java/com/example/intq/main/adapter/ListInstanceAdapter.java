package com.example.intq.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intq.common.bean.instance.InstListNode;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ListInstanceBinding;

public class ListInstanceAdapter extends WDRecyclerAdapter<InstListNode> {
    private OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public ListInstanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(),
                parent, false);
        initBindingField(parent, binding);
        return new ListInstanceHolder(binding.getRoot(), mOnItemClickListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_instance;
    }

    @Override
    protected void bindView(ViewDataBinding binding, InstListNode item, int position) {
        ListInstanceBinding viewBinding = (ListInstanceBinding) binding;
        viewBinding.instLabel.setText(item.getLabel());
        if (item.getCategory().length() > 0)
            viewBinding.instCategory.setText(String.format("[%s]", item.getCategory()));
    }

    public static class ListInstanceHolder extends WDRecyclerAdapter.MyHodler {
        public ListInstanceHolder(@NonNull View itemView, OnItemClickListener onClickListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        //确保position值有效
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.onItemClicked(view, position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }
}