package com.example.intq.user.vm;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.example.intq.common.bean.StarItem;
import com.example.intq.common.bean.instance.StarInst;
import com.example.intq.common.bean.instance.StarInstResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.util.UIUtils;
import com.example.intq.user.request.IUserRequest;

import java.util.ArrayList;
import java.util.List;

public class StarItemViewModel extends WDFragViewModel<IUserRequest> {

    public MutableLiveData<List<StarItem>> instanceStarList = new MutableLiveData<>();
    public MutableLiveData<List<StarItem>> exerciseStarList = new MutableLiveData<>();
    private int offset = 0;
    private final int limit = 20;
    public MutableLiveData<Integer> lastVisit = new MutableLiveData<>();
    public MutableLiveData<Boolean> fail = new MutableLiveData<>();
    public MutableLiveData<Boolean> firstLoading = new MutableLiveData<>(true);
    private boolean loading = false;

    @Override
    protected void create() {
        super.create();
        updateStar();
    }

    @Override
    protected void resume() {
        super.resume();
        updateStar();
    }

    public void updateStar(){
        if(!loading){
            loading = true;
            request(iRequest.getStarredInstList(LOGIN_USER.getToken(), offset, limit), new DataCall<StarInstResult>() {
                @Override
                public void success(StarInstResult data) {
                    List<StarItem> starItems = instanceStarList.getValue();
                    if(starItems == null)
                        starItems = new ArrayList<>();
                    if(data.getInstList() == null || data.getInstList().size() < limit)
                        fail.setValue(true);
                    else
                        fail.setValue(false);
                    for(StarInst inst: data.getInstList()){
                        starItems.add(new StarItem(0, inst.getLabel(), inst.getUri(), inst.getCourse(), null));
                    }
                    instanceStarList.setValue(starItems);
                    LOGIN_USER.setStarInst(JSON.toJSONString(starItems));
                    userInfoBox.put(LOGIN_USER);
                    offset = starItems.size();
                    loading = false;
                    if(firstLoading.getValue())
                        firstLoading.setValue(false);
                }

                @Override
                public void fail(ApiException data) {
                    System.out.println(LOGIN_USER.getStarInst());
                    try{
                        List<StarItem> starItems = JSON.parseArray(LOGIN_USER.getStarInst(), StarItem.class);
                        List<StarItem> starItems1 = instanceStarList.getValue();
                        if(starItems1 == null)
                            starItems1 = new ArrayList<>();
                        int end = offset + limit;
                        if(end > starItems.size()) {
                            end = starItems.size();
                            fail.setValue(true);
                        }
                        else
                            fail.setValue(false);
                        starItems1.addAll(starItems.subList(offset, end));
                        instanceStarList.setValue(starItems1);
                        offset = starItems1.size();
                    }catch (Exception e){
                        UIUtils.showToastSafe("网络错误，请检查网络设置");
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
