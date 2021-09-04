package com.example.intq.main.vm;

import android.os.Bundle;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.request.IMainRequest;

public class CustomizedTestOpeningViewModel extends WDViewModel<IMainRequest> {
    public ObservableField<Integer> limit = new ObservableField<>(5);

    public void leaveOpening(){
        if(LOGIN_USER == null){
            UIUtils.showToastSafe("请先登录");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("limit", limit.get());
        intentByRouter(Constant.ACTIVITY_CUSTOMIZED, bundle);
    }
}
