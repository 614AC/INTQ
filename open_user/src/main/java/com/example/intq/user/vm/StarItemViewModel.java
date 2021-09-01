package com.example.intq.user.vm;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.StarItem;
import com.example.intq.common.bean.instance.StarInst;
import com.example.intq.common.bean.instance.StarInstResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.user.request.IUserRequest;

import java.util.ArrayList;
import java.util.List;

public class StarItemViewModel extends WDFragViewModel<IUserRequest> {

    public MutableLiveData<List<StarItem>> instanceStarList = new MutableLiveData<>();
    public MutableLiveData<List<StarItem>> exerciseStarList = new MutableLiveData<>();

    @Override
    protected void create() {
        super.create();
        request(iRequest.getStarredInstList(LOGIN_USER.getToken(), 0, 10), new DataCall<StarInstResult>() {
            @Override
            public void success(StarInstResult data) {
                List<StarItem> starItems = new ArrayList<>();
                for(StarInst inst: data.getInstList()){
                    starItems.add(new StarItem(0, inst.getLabel(), inst.getUri(), inst.getCourse(), null));
                }
                instanceStarList.setValue(starItems);
            }

            @Override
            public void fail(ApiException data) {

            }
        });
    }
}
