package com.example.intq.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intq.common.bean.instance.InstListNode;
import com.example.intq.common.bean.instance.SearchInstList;
import com.example.intq.common.bean.instance.SearchInstListNode;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.main.R;
import com.example.intq.main.databinding.SearchListInstanceBinding;

public class SearchListInstanceAdapter extends WDRecyclerAdapter<SearchInstListNode> {
    private OnItemClickListener mOnItemClickListener;

    @Override
    protected int getLayoutId() {
        return R.layout.search_list_instance;
    }

    @Override
    protected void bindView(ViewDataBinding binding, SearchInstListNode item, int position) {
        SearchListInstanceBinding viewBinding = (SearchListInstanceBinding) binding;
        viewBinding.instLabel.setText(item.getLabel());
        if (!item.getCategory().equals("")) {
            viewBinding.instCategory.setText(String.format("[%s]", item.getCategory()));
            viewBinding.instCategory.setVisibility(View.VISIBLE);
        } else
            viewBinding.instCategory.setVisibility(View.INVISIBLE);
        if (!item.getUri().equals("")) {
            viewBinding.instStarTimes.setText(String.format("%d", item.getStarTimes()));
            viewBinding.instViewTimes.setText(String.format("%d", item.getViewTimes()));
            viewBinding.instViewTimes.setVisibility(View.VISIBLE);
            viewBinding.instStarTimes.setVisibility(View.VISIBLE);
            viewBinding.starIcon.setVisibility(View.VISIBLE);
            viewBinding.viewIcon.setVisibility(View.VISIBLE);
        } else {
            viewBinding.instViewTimes.setVisibility(View.INVISIBLE);
            viewBinding.instStarTimes.setVisibility(View.INVISIBLE);
            viewBinding.starIcon.setVisibility(View.INVISIBLE);
            viewBinding.viewIcon.setVisibility(View.INVISIBLE);
        }
    }

    @NonNull
    @Override
    public ListInstanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(),
                parent, false);
        initBindingField(parent, binding);
        return new ListInstanceHolder(binding.getRoot(), mOnItemClickListener);
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