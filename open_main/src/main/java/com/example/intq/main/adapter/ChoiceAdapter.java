package com.example.intq.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intq.common.bean.ExtraOption;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.main.R;
import com.example.intq.main.databinding.LayoutItemChildBinding;

import java.util.List;

public class ChoiceAdapter extends WDRecyclerAdapter<ExtraOption> {
    private OnItemClickListener mOnItemClickListener;

    private final int[] choices_chosen = {R.mipmap.icon_a, R.mipmap.icon_b, R.mipmap.icon_c, R.mipmap.icon_d, R.mipmap.icon_e, R.mipmap.icon_f, R.mipmap.icon_g, R.mipmap.icon_h};
    private final int[] choices_common = {R.mipmap.icon_a_common, R.mipmap.icon_b_common, R.mipmap.icon_c_common, R.mipmap.icon_d_common, R.mipmap.icon_e_common, R.mipmap.icon_f_common, R.mipmap.icon_g_common, R.mipmap.icon_h_common};
    private final int[] choices_correct = {R.mipmap.icon_a_green, R.mipmap.icon_b_green, R.mipmap.icon_c_green, R.mipmap.icon_d_green, R.mipmap.icon_e_green, R.mipmap.icon_f_green, R.mipmap.icon_g_green, R.mipmap.icon_h_green};
    private final int[] choices_wrong = {R.mipmap.icon_a_red, R.mipmap.icon_b_red, R.mipmap.icon_c_red, R.mipmap.icon_d_red, R.mipmap.icon_e_red, R.mipmap.icon_f_red, R.mipmap.icon_g_red, R.mipmap.icon_h_red};

    @NonNull
    @Override
    public ChoiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(),
                parent, false);
        initBindingField(parent, binding);
        return new ChoiceHolder(binding.getRoot(), mOnItemClickListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_item_child;
    }

    @Override
    protected void bindView(ViewDataBinding binding, ExtraOption item, int position) {
        LayoutItemChildBinding binding1 = (LayoutItemChildBinding) binding;
        binding1.itemChildNameTv.setText(item.getChoice().substring(2));
        switch (item.getType()) {
            case 0:
                binding1.itemChildIconIv.setImageResource(choices_common[item.getIndex()]);
                break;
            case 1:
                binding1.itemChildIconIv.setImageResource(choices_chosen[item.getIndex()]);
                break;
            case 2:
                binding1.itemChildIconIv.setImageResource(choices_correct[item.getIndex()]);
                break;
            case 3:
                binding1.itemChildIconIv.setImageResource(choices_wrong[item.getIndex()]);
                break;
            default:
                break;
        }

    }

    private int stringToInt(String s) {
        return s.charAt(0) - 'A';
    }

    public static class ChoiceHolder extends WDRecyclerAdapter.MyHodler {
        public ChoiceHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClicked(view, position);
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

    public void removeOnItemClickListener(){
        this.mOnItemClickListener = null;
    }

    public void update(List<ExtraOption> newOptions){
        mList = newOptions;
    }
}
