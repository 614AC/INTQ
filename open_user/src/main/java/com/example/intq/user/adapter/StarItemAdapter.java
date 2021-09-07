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
import com.example.intq.user.databinding.LayoutItemFooterBinding;
import com.example.intq.user.databinding.LayoutStarItemBinding;

import java.util.List;

public class StarItemAdapter extends WDRecyclerAdapter<StarItem> {
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
            return new StarItemHolder(binding.getRoot(), mOnItemClickListener);
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
        return R.layout.layout_star_item;
    }

    @Override
    protected void bindView(ViewDataBinding binding, StarItem item, int position) {
        if(position != mList.size() - 1) {
            LayoutStarItemBinding binding1 = (LayoutStarItemBinding) binding;
            binding1.starTitle.setText(item.getLabel());
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

    public void updateList(List<StarItem> nList){
        mList = nList;
    }
}
