package com.example.intq.user.vm;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.StarItem;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.user.request.IUserRequest;

import java.util.ArrayList;
import java.util.List;

public class StarItemViewModel extends WDFragViewModel<IUserRequest> {

    public MutableLiveData<List<StarItem>> instanceStarList = new MutableLiveData<>();
    public MutableLiveData<List<StarItem>> exerciseStarList = new MutableLiveData<>();

    @Override
    protected void create() {
        super.create();
        ArrayList<StarItem> a = new ArrayList<>();
        a.add(new StarItem(0, "a","a"));
        a.add(new StarItem(0,"b","b"));
        ArrayList<StarItem> b = new ArrayList<>();
        b.add(new StarItem(1,"c","c"));
        b.add(new StarItem(1,"d","d"));


        instanceStarList.setValue(a);
        exerciseStarList.setValue(b);
    }
}
