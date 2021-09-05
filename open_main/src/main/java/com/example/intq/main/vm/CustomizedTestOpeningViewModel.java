package com.example.intq.main.vm;

import android.os.Bundle;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.request.IMainRequest;

public class CustomizedTestOpeningViewModel extends WDViewModel<IMainRequest> {
    public ObservableField<Integer> limit = new ObservableField<>(10);
    public ObservableField<Integer> course = new ObservableField<>(0);
    public String[] courses = new String[]{"语文","数学","英语","物理","化学","生物","政治","历史","地理"};
    public String[] coursesName = new String[]{"chinese", "math", "english", "physics", "chemistry", "biology", "politics", "history", "geo"};

    @Override
    protected void create() {
        super.create();
        limit.set(10);
        course.set(0);
    }

    public void leaveOpening(){
        if(LOGIN_USER == null){
            UIUtils.showToastSafe("请先登录");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("limit", limit.get());
        bundle.putString("course", coursesName[course.get()]);
        intentByRouter(Constant.ACTIVITY_CUSTOMIZED, bundle);
    }
}
