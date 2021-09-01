package com.example.intq.main.vm;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.QAChat;
import com.example.intq.common.bean.instance.ContentNode;
import com.example.intq.common.bean.instance.ContentResult;
import com.example.intq.common.bean.instance.InstInfoResult;
import com.example.intq.common.bean.instance.Instance;
import com.example.intq.common.bean.instance.PropertyNode;
import com.example.intq.common.bean.instance.PropertyResult;
import com.example.intq.common.bean.question.SolveResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.request.IMainRequest;

import java.util.ArrayList;
import java.util.List;

// List and Graph both use
public class InstanceItemViewModel extends WDFragViewModel<IMainRequest> {

    public MutableLiveData<List<Instance>> InstanceList = new MutableLiveData<>();
    public MutableLiveData<List<PropertyNode>> propertyResultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ContentNode>> contentResultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> queryInstance = new MutableLiveData<>();
    public MutableLiveData<String> queryCourse = new MutableLiveData<>();
//    public MutableLiveData<List<Instance>> EntityGraph = new MutableLiveData<>();

    public void getData(){
        request(iRequest.getInstanceInfo(this.queryInstance.getValue(), this.queryCourse.getValue()), new DataCall<InstInfoResult>() {
            @Override
            public void success(InstInfoResult data) {
                propertyResultMutableLiveData.setValue(data.getInstInfo().getProperty());
                contentResultMutableLiveData.setValue(data.getInstInfo().getContent());
            }

            @Override
            public void fail(ApiException data) {
                UIUtils.showToastSafe("网络连接失败，请检查网络连接");
            }
        });
    }
}
