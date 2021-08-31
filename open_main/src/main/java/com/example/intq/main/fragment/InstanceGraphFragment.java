package com.example.intq.main.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intq.common.bean.Instance;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.recycleview.SpacingItemDecoration;
import com.example.intq.main.R;
import com.example.intq.main.adapter.InstanceAdapter;
import com.example.intq.main.databinding.FragGraphBinding;
import com.example.intq.main.view.CirclePeopleView;
import com.example.intq.main.vm.InstanceItemViewModel;
import com.example.intq.main.utils.Contant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InstanceGraphFragment extends WDFragment<InstanceItemViewModel, FragGraphBinding> {

    private InstanceAdapter adapter;
    public static Context mContext;
    public static String childSub; // center text
    private CirclePeopleView peopleView;
    private List<Instance> lists = new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 222) {
                peopleView.SetFieldInfo(lists);
                peopleView.invalidate();
            }
        }
    };

//    public InstanceGraphFragment() {}
//    public InstanceGraphFragment(CirclePeopleView p) {
//        this.peopleView = p;
//    }

    @Override
    protected InstanceItemViewModel initFragViewModel() {
        return new InstanceItemViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_graph;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        adapter = new InstanceAdapter();

        viewModel.InstanceList.observe(this, new Observer<List<com.example.intq.common.bean.Instance>>() {
            @Override
            public void onChanged(List<Instance> Entities) {
                adapter.addAll(Entities);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();
//        this.init();
        this.loadData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View graph = inflater.inflate(R.layout.frag_graph, container, false);
        peopleView = graph.findViewById(R.id.layout_cricle_people);
//        sq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainActivity ma = (MainActivity) getActivity();
//                ma.setTabSelection(2);
//            }
//        });
        return graph;

    }

    private void loadData() {
        try {
            childSub = "张三";
            JSONObject object = new JSONObject(Contant.json);
            JSONArray array = object.getJSONArray("nodes");
            System.out.println(array.length());
            Instance personBean = null;
            for (int i = 0; i < array.length(); i++) {
                personBean = new Instance();
                JSONObject data = array.getJSONObject(i);
                personBean.setName(data.getString("name"));
                personBean.setSymbolSize(data.getInt("symbolSize"));
                lists.add(personBean);
            }
            System.out.println("......");
            System.out.println(lists.toString());
//            System.out.println("......");
            Message msg = Message.obtain();
            msg.what = 222;
            handler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setPeopleView(CirclePeopleView p) {
        this.peopleView = p;
    }
//    private void init() {
//        peopleView = getView().findViewById(R.id.layout_cricle_people);
//        peopleView.setmViewClick(new CirclePeopleView.OnViewClick() {
//            @Override
//            public void onClick(CoPersonBean personBean) {
//                Toast.makeText(MainActivity.this,"您点击了："+personBean.getName(),Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
