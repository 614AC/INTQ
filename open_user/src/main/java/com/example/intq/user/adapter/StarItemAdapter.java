package com.example.intq.user.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intq.common.bean.StarItem;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.user.R;
import com.example.intq.user.databinding.LayoutStarItemBinding;

public class StarItemAdapter extends WDRecyclerAdapter<StarItem> {
    private OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public StarItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(),
                parent, false);
        initBindingField(parent, binding);
        return new StarItemHolder(binding.getRoot(), mOnItemClickListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_star_item;
    }

    @Override
    protected void bindView(ViewDataBinding binding, StarItem item, int position) {
        LayoutStarItemBinding binding1 = (LayoutStarItemBinding) binding;
        binding1.starTitle.setText(item.getLabel());
    }

    public static class StarItemHolder extends WDRecyclerAdapter.MyHodler {
        public StarItemHolder(@NonNull View itemView, OnItemClickListener onClickListener) {
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
