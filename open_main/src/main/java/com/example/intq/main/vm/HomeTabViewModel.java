package com.example.intq.main.vm;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.Course;
import com.example.intq.common.bean.instance.InstList;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.main.request.IMainRequest;

import java.util.ArrayList;
import java.util.List;


public class HomeTabViewModel extends WDFragViewModel<IMainRequest> {
    public MutableLiveData<List<Course>> courseList = new MutableLiveData<>();
    public MutableLiveData<InstList> randomInstList = new MutableLiveData<>();
    public MutableLiveData<Boolean> searching = new MutableLiveData<>();

    @Override
    protected void create() {
        super.create();
        List<Course> courseList = new ArrayList<>();
        for (int i = 0; i < Course.getCourseNumber(); ++i)
            courseList.add(new Course(i));
        this.courseList.setValue(courseList);

    }

    public void updateRandomInstList(int limit, String course) {
        searching.setValue(true);
        request(iRequest.getRandomInstList(LOGIN_USER.getToken(), limit, course), new DataCall<InstList>() {
            @Override
            public void success(InstList data) {
                searching.setValue(false);
                randomInstList.setValue(data);
            }

            @Override
            public void fail(ApiException data) {
                searching.setValue(false);
                randomInstList.setValue(null);
            }
        });
    }
}
