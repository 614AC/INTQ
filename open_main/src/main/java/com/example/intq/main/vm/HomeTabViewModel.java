package com.example.intq.main.vm;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;

import com.alibaba.fastjson.JSON;
import com.example.intq.common.bean.Course;
import com.example.intq.common.bean.instance.HomeTabInfo;
import com.example.intq.common.bean.instance.InstList;
import com.example.intq.common.bean.instance.InstListNode;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.request.IMainRequest;

import java.util.ArrayList;
import java.util.List;


public class HomeTabViewModel extends WDFragViewModel<IMainRequest> {
    public static MutableLiveData<HomeTabInfo> homeTabInfo = new MutableLiveData<>();
    public MutableLiveData<Boolean> searching = new MutableLiveData<>();

    @Override
    protected void create() {
        super.create();
        if (homeTabInfo.getValue() == null) {
            HomeTabInfo homeTabInfoDisk = JSON.parseObject(LOGIN_USER.getHomeTabInfo(), HomeTabInfo.class);
            if (homeTabInfoDisk == null) {
                List<Integer> courseList = Course.getAllIndices();
                List<InstList> emptyList = new ArrayList<>();
                for (Integer i : courseList) {
                    InstList instList = new InstList();
                    List<InstListNode> instListNodeList = new ArrayList<>();
                    instList.setInstList(instListNodeList);
                    emptyList.add(instList);
                }
                homeTabInfoDisk = new HomeTabInfo(courseList, emptyList);
            }
            homeTabInfo.setValue(homeTabInfoDisk);
            save();
        }
        searching.setValue(false);
    }

    public List<Integer> getCourseList() {
        return homeTabInfo.getValue().getCourseList();
    }

    public void updateCourseList(List<Integer> newCourseList) {
        HomeTabInfo newHomeTabInfo = homeTabInfo.getValue();
        newHomeTabInfo.setCourseList(newCourseList);
        homeTabInfo.setValue(newHomeTabInfo);
        save();
    }

    public void updateRandomInstList(int limit, int courseIndex) {
        searching.setValue(true);

        request(iRequest.getRandomInstList(LOGIN_USER.getToken(), limit, Course.getNameEng(courseIndex)),
                new DataCall<InstList>() {
                    @Override
                    public void success(InstList data) {
                        searching.setValue(false);
                        HomeTabInfo newHomeTabInfo = homeTabInfo.getValue();
                        for (InstListNode node : data.getInstList())
                            node.setCategory("");
                        newHomeTabInfo.setInstList(courseIndex, data);
                        homeTabInfo.setValue(newHomeTabInfo);
                        save();
                    }

                    @Override
                    public void fail(ApiException data) {
                        searching.setValue(false);
                        UIUtils.showToastSafe("网络错误~请重试");
                    }
                });
    }

    private void save() {
        LOGIN_USER.setHomeTabInfo(JSON.toJSONString(homeTabInfo.getValue()));
        userInfoBox.put(LOGIN_USER);
    }
}
