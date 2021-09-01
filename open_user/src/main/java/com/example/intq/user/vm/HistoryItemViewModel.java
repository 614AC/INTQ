package com.example.intq.user.vm;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.HistoryItem;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.user.request.IUserRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryItemViewModel extends WDFragViewModel<IUserRequest> {
    public MutableLiveData<List<HistoryItem>> instanceHistoryList = new MutableLiveData<>();
    public MutableLiveData<List<HistoryItem>> exerciseHistoryList = new MutableLiveData<>();

    @Override
    protected void create() {
        super.create();
        ArrayList<HistoryItem> a = new ArrayList<>();
        a.add(new HistoryItem(0, "a","a", new Date()));
        a.add(new HistoryItem(0,"b","b", new Date()));
        ArrayList<HistoryItem> b = new ArrayList<>();
        b.add(new HistoryItem(1,"c","c", new Date()));
        b.add(new HistoryItem(1,"d","d", new Date()));


        instanceHistoryList.setValue(a);
        exerciseHistoryList.setValue(b);
    }

}
