//package com.example.intq.main.adapter;
//
//import androidx.databinding.ViewDataBinding;
//
//import com.example.intq.common.bean.instance.Instance;
//import com.example.intq.common.core.WDRecyclerAdapter;
//import com.example.intq.main.R;
//import com.example.intq.main.databinding.FragGraphBinding;
//
//public class GraphAdapter extends WDRecyclerAdapter<Instance> {
//    @Override
//    protected int getLayoutId() {
//        return R.layout.frag_graph;
//    }
//
//    @Override
//    protected void bindView(ViewDataBinding binding, Instance item, int position) {
//        FragGraphBinding binding1 = (FragGraphBinding) binding;
//        binding1.instTitle.setText(item.getTitle());
//    }
//}
