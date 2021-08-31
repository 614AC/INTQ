package com.example.intq.main.vm;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.Instance;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.main.request.IMainRequest;

import java.util.ArrayList;
import java.util.List;

// List and Graph both use
public class InstanceItemViewModel extends WDFragViewModel<IMainRequest> {

    public MutableLiveData<List<Instance>> InstanceList = new MutableLiveData<>();
//    public MutableLiveData<List<Instance>> EntityGraph = new MutableLiveData<>();

    @Override
    protected void create() {
        super.create();
        ArrayList<Instance> a = new ArrayList<>();
        a.add(new Instance("aaa", 1));
        a.add(new Instance("bbb", 2));
//        ArrayList<Instance> b = new ArrayList<>();
//        b.add(new Instance(1,"c","c"));
//        b.add(new Instance(1,"d","d"));


        InstanceList.setValue(a);
//        EntityGraph.setValue(b);
    }
}
