package com.example.intq.main.vm;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.instance.Link;
import com.example.intq.common.bean.instance.LinkInstanceResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.core.http.NetworkManager;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.request.IMainRequest;

import java.util.List;

import okhttp3.RequestBody;

public class LinkViewModel extends WDViewModel<IMainRequest> {
    private final String[] courseMapping = {"chinese", "math", "english", "physics", "chemistry", "biology", "politics", "history", "geo"};
    public ObservableField<Integer> courseId = new ObservableField<>();
    public ObservableField<String> context = new ObservableField<>();
    public MutableLiveData<List<Link>> links = new MutableLiveData<>();
    public MutableLiveData<Boolean> enabled = new MutableLiveData<>(true);

    public void link(){
        if(enabled.getValue()){
            enabled.setValue(false);
            RequestBody body = NetworkManager.convertJsonBody(new String[]{"course", "context"}, new String[]{courseMapping[courseId.get()], context.get()});
            request(iRequest.getLinkInstance(body), new DataCall<LinkInstanceResult>() {
                @Override
                public void success(LinkInstanceResult data) {
                    links.setValue(data.getLinkInstance());
                }

                @Override
                public void fail(ApiException data) {
                    UIUtils.showToastSafe("网络连接失败，请检查网络连接");
                    enabled.setValue(true);
                }
            });
        }

    }
}
