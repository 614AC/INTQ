package com.example.intq.user.vm;

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

    @Override
    protected void create() {
        super.create();
        updateHistory();

    }

    @Override
    protected void resume() {
        super.resume();
        updateHistory();
    }

    public void updateHistory(){
        request(iRequest.getHistoryInstList(LOGIN_USER.getToken(), 0, 10), new DataCall<HistoryInstResult>() {
            @Override
            public void success(HistoryInstResult data) {
                List<HistoryItem> historyItems = new ArrayList<>();
                for (HistoryInst inst : data.getInstList()) {
                    historyItems.add(new HistoryItem(0, inst.getCourse(), inst.getLabel(), inst.getUri(), inst.getTsp()));
                }
                instanceHistoryList.setValue(historyItems);
                LOGIN_USER.setHistoryInst(JSON.toJSONString(historyItems));
                userInfoBox.put(LOGIN_USER);
            }

            @Override
            public void fail(ApiException data) {
                System.out.println(LOGIN_USER.getHistoryInst());
                try{
                    List<HistoryItem> historyItems = JSON.parseArray(LOGIN_USER.getHistoryInst(), HistoryItem.class);
                    instanceHistoryList.setValue(historyItems);
                }catch (Exception e){
                    UIUtils.showToastSafe("网络错误，请检查网络设置");
                }
            }
        });
    }
}
