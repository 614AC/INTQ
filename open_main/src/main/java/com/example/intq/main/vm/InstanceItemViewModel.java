package com.example.intq.main.vm;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.example.intq.common.bean.InstInfo;
import com.example.intq.common.bean.InstInfo_;
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
    public MutableLiveData<String> queryUri = new MutableLiveData<>();
//    public MutableLiveData<List<Instance>> EntityGraph = new MutableLiveData<>();

    public void getData(){
        request(iRequest.getInstanceInfo(this.queryInstance.getValue(), this.queryCourse.getValue()), new DataCall<InstInfoResult>() {
            @Override
            public void success(InstInfoResult data) {
                propertyResultMutableLiveData.setValue(data.getInstInfo().getProperty());
                contentResultMutableLiveData.setValue(data.getInstInfo().getContent());

                InstInfo instInfo = instInfoBox.query().equal(InstInfo_.course, queryCourse.getValue()).equal(InstInfo_.uri, queryUri.getValue()).build().findFirst();
                System.out.println(JSON.toJSONString(data.getInstInfo().getProperty()));
                if(instInfo == null){
                    instInfoBox.put(new InstInfo(JSON.toJSONString(data.getInstInfo().getProperty()), JSON.toJSONString(data.getInstInfo().getContent()), queryInstance.getValue(), queryCourse.getValue(), queryUri.getValue(), null));
                }
                else{
                    instInfo.setProperty(JSON.toJSONString(data.getInstInfo().getProperty()));
                    instInfo.setContent(JSON.toJSONString(data.getInstInfo().getContent()));
                    instInfoBox.put(instInfo);
                }
            }

            @Override
            public void fail(ApiException data) {
                InstInfo instInfo = instInfoBox.query().equal(InstInfo_.course, queryCourse.getValue()).equal(InstInfo_.uri, queryUri.getValue()).build().findFirst();
                if(instInfo == null)
                    UIUtils.showToastSafe("网络连接失败，请检查网络连接");
                else{
                    try{
                        List<PropertyNode> propertyNodes = JSON.parseArray(instInfo.getProperty(), PropertyNode.class);
                        List<ContentNode> contentNodes = JSON.parseArray(instInfo.getContent(), ContentNode.class);
                        propertyResultMutableLiveData.setValue(propertyNodes);
                        contentResultMutableLiveData.setValue(contentNodes);
                    }catch (JSONException e){
                        UIUtils.showToastSafe("网络连接失败，请检查网络连接");
                    }

                }
            }
        });
    }
}
