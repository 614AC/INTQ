package com.vc.wd.main.vm;

import androidx.lifecycle.MutableLiveData;

import com.vc.wd.common.bean.Banner;
import com.vc.wd.common.bean.Course;
import com.vc.wd.common.bean.shop.HomeList;
import com.vc.wd.common.core.DataCall;
import com.vc.wd.common.core.WDFragViewModel;
import com.vc.wd.common.core.exception.ApiException;
import com.vc.wd.common.util.Constant;
import com.vc.wd.main.request.IMainRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class HomeViewModel extends WDFragViewModel<IMainRequest> {
    public MutableLiveData<List<Banner>> bannerData = new MutableLiveData<>();
    public MutableLiveData<HomeList> homeListData = new MutableLiveData<>();
    public MutableLiveData<List<Course>> courseList = new MutableLiveData<>();

    @Override
    protected void create() {
        super.create();
        List<Course> courseList = new ArrayList<>();
        for (int i = 0; i < Course.getCourseNumber(); ++i)
            courseList.add(new Course(i));
        this.courseList.setValue(courseList);

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
