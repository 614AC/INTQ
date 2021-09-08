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

import com.example.intq.common.bean.instance.ContentNode;
import com.example.intq.common.bean.instance.Instance;
import com.example.intq.common.core.WDFragment;
import com.example.intq.main.R;
import com.example.intq.main.adapter.InstanceAdapter;
import com.example.intq.main.databinding.FragGraphBinding;
import com.example.intq.main.view.CirclePeopleView;
import com.example.intq.main.vm.InstanceItemViewModel;

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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();
//        this.init();
        childSub = getArguments().getString("inst_name");
        System.out.println("frag -> " + childSub);
        viewModel.queryInstance.setValue(childSub);
        viewModel.queryCourse.setValue(getArguments().getString("course"));
        viewModel.queryUri.setValue(getArguments().getString("uri"));
        viewModel.getData();
        this.initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View graph = inflater.inflate(R.layout.frag_graph, container, false);
        peopleView = graph.findViewById(R.id.layout_circle_people);
        viewModel.contentResultMutableLiveData.observe(this, new Observer<List<ContentNode>>() {
            @Override
            public void onChanged(List<ContentNode> contentResult) {
                loadData();
            }
        });
        return graph;
    }

    private void initData() {
        try {
            Message msg = Message.obtain();
            msg.what = 222;
            handler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            List<ContentNode> nodes = viewModel.contentResultMutableLiveData.getValue();
//            JSONObject object = new JSONObject(Contant.json);
//            JSONArray array = object.getJSONArray("nodes");
            Instance personBean = null;
            for (int i = 0; i < nodes.size(); i++) {
                personBean = new Instance();
                ContentNode node = nodes.get(i);
                if (node.getObject() != null) {
                    personBean.setName(node.getObject_label());
                }
                else {
                    personBean.setName(node.getSubject_label());
                }
                personBean.setSymbolSize(1);
                personBean.setPredicate_label(node.getPredicate_label());
                lists.add(personBean);
            }
            System.out.println("......");
            System.out.println(lists.toString());
//            System.out.println("......");
            Message msg = Message.obtain();
            msg.what = 222;
            handler.sendMessage(msg);
        } catch (Exception e) {
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
