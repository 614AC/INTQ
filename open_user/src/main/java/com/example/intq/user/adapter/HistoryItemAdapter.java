package com.example.intq.user.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intq.common.bean.HistoryItem;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.user.R;
import com.example.intq.user.databinding.LayoutHistoryItemBinding;

import java.text.SimpleDateFormat;

public class HistoryItemAdapter extends WDRecyclerAdapter<HistoryItem> {
    private OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public HistoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(),
                parent, false);
        initBindingField(parent, binding);
        return new HistoryItemHolder(binding.getRoot(), mOnItemClickListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_history_item;
    }

    @Override
    protected void bindView(ViewDataBinding binding, HistoryItem item, int position) {
        LayoutHistoryItemBinding binding1 = (LayoutHistoryItemBinding) binding;
        binding1.historyTitle.setText(item.getLabel());
        binding1.historyTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getVis()));
    }

    public static class HistoryItemHolder extends WDRecyclerAdapter.MyHodler {
        public HistoryItemHolder(@NonNull View itemView, OnItemClickListener onClickListener) {
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

    public interface OnItemClickListener{
        void onItemClicked(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

}
