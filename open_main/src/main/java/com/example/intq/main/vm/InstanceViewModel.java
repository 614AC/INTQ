package com.example.intq.main.vm;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.intq.common.bean.instance.CheckInstanceResult;
import com.example.intq.common.bean.instance.Link;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.core.http.NetworkManager;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.request.IMainRequest;
import com.google.gson.internal.LinkedTreeMap;

import okhttp3.RequestBody;

public class InstanceViewModel extends WDViewModel<IMainRequest> {
    public MutableLiveData<String> instName = new MutableLiveData<>();
    public MutableLiveData<String> course = new MutableLiveData<>();
    public MutableLiveData<String> uri = new MutableLiveData<>();
    public MutableLiveData<Boolean> if_star = new MutableLiveData<>(false);

    public void addHistory(){
        if(LOGIN_USER != null){
            RequestBody body = NetworkManager.convertJsonBody(new String[]{"course", "uri"}, new String[]{course.getValue(), uri.getValue()});
            request(iRequest.addHistoryInstance(LOGIN_USER.getToken(), body), new DataCall<LinkedTreeMap>() {
                @Override
                public void success(LinkedTreeMap data) {
                }

                @Override
                public void fail(ApiException data) {
                }
            });
        }
    }
    public void checkStar(){
        if(LOGIN_USER != null){
            JSONObject object = new JSONObject();
            object.put("course",course.getValue());
            object.put("uri",uri.getValue());
            JSONArray array = new JSONArray();
            array.add(object);
            request(iRequest.checkInstanceIfStarred(LOGIN_USER.getToken(), array.toJSONString()), new DataCall<CheckInstanceResult>() {
                @Override
                public void success(CheckInstanceResult data) {
                    if_star.setValue(data.getIf_star().get(0));
                }

                @Override
                public void fail(ApiException data) {
                    try{
                        if_star.setValue(LOGIN_USER.getStarInst().contains(uri.getValue()));
                    }catch (Exception e){

                    }
                }
            });
        }
    }

    public void starOperation(){
        if(LOGIN_USER == null) {
            UIUtils.showToastSafe("请先登录");
            return;
        }
        if(if_star.getValue() == false){
            RequestBody body = NetworkManager.convertJsonBody(new String[]{"course", "uri"}, new String[]{course.getValue(), uri.getValue()});
            request(iRequest.starInstance(LOGIN_USER.getToken(), body), new DataCall<LinkedTreeMap>() {
                @Override
                public void success(LinkedTreeMap data) {
                    UIUtils.showToastSafe("收藏成功");
                    if_star.setValue(true);
                }

                @Override
                public void fail(ApiException data) {
                    UIUtils.showToastSafe("收藏失败，请重试");
                }
            });
        }
        else {
            RequestBody body = NetworkManager.convertJsonBody(new String[]{"course", "uri"}, new String[]{course.getValue(), uri.getValue()});
            request(iRequest.unstarInstance(LOGIN_USER.getToken(), body), new DataCall<LinkedTreeMap>() {
                @Override
                public void success(LinkedTreeMap data) {
                    UIUtils.showToastSafe("取消收藏成功");
                    if_star.setValue(false);
                }

                @Override
                public void fail(ApiException data) {
                    UIUtils.showToastSafe("取消收藏失败，请重试");
                }
            });
        }
    }
}
