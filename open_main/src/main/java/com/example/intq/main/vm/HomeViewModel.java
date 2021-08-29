package com.example.intq.main.vm;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.Banner;
import com.example.intq.common.bean.Course;
import com.example.intq.common.bean.shop.HomeList;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.main.request.IMainRequest;

import java.util.ArrayList;
import java.util.List;


public class HomeViewModel extends WDFragViewModel<IMainRequest> {

    public List<Course> courseList = new ArrayList<Course>();
    public MutableLiveData<List<Banner>> bannerData = new MutableLiveData<>();
    public MutableLiveData<HomeList> homeListData = new MutableLiveData<>();

    @Override
    protected void create() {
        super.create();
        for (int i = 0; i < Course.TYPE.length; ++i)
            courseList.add(new Course(i));
        request(iRequest.bannerShow(), new DataCall<List<Banner>>() {
            @Override
            public void success(List<Banner> data) {
                bannerData.setValue(data);
            }

            @Override
            public void fail(ApiException e) {

            }
        });

        request(iRequest.commodityList(), new DataCall<HomeList>() {

            @Override
            public void success(HomeList data) {
                homeListData.setValue(data);
            }

            @Override
            public void fail(ApiException data) {

            }
        });
    }
}
