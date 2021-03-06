package com.example.intq.user.vm;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.example.intq.common.bean.HistoryItem;
import com.example.intq.common.bean.instance.HistoryInst;
import com.example.intq.common.bean.instance.HistoryInstResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.util.UIUtils;
import com.example.intq.user.request.IUserRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HistoryItemViewModel extends WDFragViewModel<IUserRequest> {
    public MutableLiveData<List<HistoryItem>> instanceHistoryList = new MutableLiveData<>();
    public MutableLiveData<List<HistoryItem>> exerciseHistoryList = new MutableLiveData<>();
    private int offset = 0;
    private final int limit = 20;
    public ObservableField<Boolean> fromResume = new ObservableField<>(false);
    public MutableLiveData<Integer> lastVisit = new MutableLiveData<>();
    public MutableLiveData<Boolean> fail = new MutableLiveData<>();
    public MutableLiveData<Boolean> firstLoading = new MutableLiveData<>(true);
    private boolean loading = false;
    @Override
    protected void create() {
        super.create();
        loadMore();

    }

    @Override
    protected void resume() {
        super.resume();
        fromResume.set(true);
        updateHistory(0, offset);
    }

    public void loadMore(){
        updateHistory(offset, limit);
    }

    public void updateHistory(int _offset, int _limit){
        if(!loading) {
            loading = true;
            fail.setValue(false);
            request(iRequest.getHistoryInstList(LOGIN_USER.getToken(), _offset, _limit), new DataCall<HistoryInstResult>() {
                @Override
                public void success(HistoryInstResult data) {
                    List<HistoryItem> historyItems = instanceHistoryList.getValue();
                    if(historyItems == null || fromResume.get())
                        historyItems = new ArrayList<>();
                    if(data.getInstList() == null || data.getInstList().size() < limit)
                        fail.setValue(true);
                    else
                        fail.setValue(false);
                    for (HistoryInst inst : data.getInstList()) {
                        historyItems.add(new HistoryItem(0, inst.getCourse(), inst.getLabel(), inst.getUri(), inst.getTsp()));
                    }
                    instanceHistoryList.setValue(historyItems);
                    LOGIN_USER.setHistoryInst(JSON.toJSONString(historyItems));
                    userInfoBox.put(LOGIN_USER);
                    offset = historyItems.size();
                    loading = false;
                    if(firstLoading.getValue())
                        firstLoading.setValue(false);
                }

                @Override
                public void fail(ApiException data) {
                    System.out.println(LOGIN_USER.getHistoryInst());
                    try {
                        List<HistoryItem> historyItems1 = JSON.parseArray(LOGIN_USER.getHistoryInst(), HistoryItem.class);
                        List<HistoryItem> historyItems = instanceHistoryList.getValue();
                        if(historyItems == null)
                            historyItems = new ArrayList<>();
                        int end = _offset + _limit;
                        if(end > historyItems1.size()){
                            end = historyItems1.size();
                            fail.setValue(true);
                        }
                        else
                            fail.setValue(false);
                        historyItems.addAll(historyItems1.subList(_offset, end));
                        instanceHistoryList.setValue(historyItems);
                        offset = historyItems.size();
                    } catch (Exception e) {
                        UIUtils.showToastSafe("????????????????????????????????????");
                        fail.setValue(true);
                    }
                    loading = false;
                    if(firstLoading.getValue())
                        firstLoading.setValue(false);
                }
            });
        }
    }
}
