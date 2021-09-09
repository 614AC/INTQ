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
import com.example.intq.user.databinding.LayoutItemFooterBinding;

import java.text.SimpleDateFormat;

public class HistoryItemAdapter extends WDRecyclerAdapter<HistoryItem> {
    private OnItemClickListener mOnItemClickListener;

    private final int TYPE_ITEM = 0;
    private final int TYPE_FOOTER = 1;
    private boolean end = false;

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == mList.size() - 1)
            return 1;
        return 0;
    }

    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(),
                    parent, false);
            initBindingField(parent, binding);
            return new HistoryItemHolder(binding.getRoot(), mOnItemClickListener);
        }
        else {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_footer,
                    parent, false);
            initBindingField(parent, binding);
            return new FooterHolder(binding.getRoot());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_history_item;
    }

    @Override
    protected void bindView(ViewDataBinding binding, HistoryItem item, int position) {
        if(position != mList.size() - 1) {
            LayoutHistoryItemBinding binding1 = (LayoutHistoryItemBinding) binding;
            binding1.historyTitle.setText(item.getLabel());
            binding1.historyTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getVis()));
        }
        else {
            LayoutItemFooterBinding binding1 = (LayoutItemFooterBinding) binding;
            if(end){
                binding1.footerLoading.hide();
                binding1.footerText.setVisibility(View.VISIBLE);
            }
            else {
                binding1.footerLoading.show();
                binding1.footerText.setVisibility(View.INVISIBLE);
            }
        }
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

    public static class FooterHolder extends WDRecyclerAdapter.MyHodler {
        public FooterHolder(@NonNull View view){
            super(view);
        }
    }

    public interface OnItemClickListener{
        void onItemClicked(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    public void setFooterFail(){
        end = true;
    }

    public void setFooterLoading(){
        end = false;
    }

}
