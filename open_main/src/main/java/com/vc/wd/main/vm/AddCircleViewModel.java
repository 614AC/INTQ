package com.vc.wd.main.vm;

import androidx.databinding.ObservableField;

import com.vc.wd.common.core.DataCall;
import com.vc.wd.common.core.WDViewModel;
import com.vc.wd.common.core.exception.ApiException;
import com.vc.wd.common.util.UIUtils;
import com.vc.wd.main.request.IMainRequest;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddCircleViewModel extends WDViewModel<IMainRequest> {

    public ObservableField<String> content = new ObservableField<>();

    public void publish(List<Object> list){
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        builder.addFormDataPart("content", content.get());
        builder.addFormDataPart("commodityId", "1");
        if (list.size()>1) {
            for (int i = 1; i < list.size(); i++) {
                File file = new File((String) list.get(i));
                builder.addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/octet-stream"),
                                file));
            }
        }
        request(iRequest.releaseCircle(LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId(),builder.build()),
                new DataCall() {
            @Override
            public void success(Object data) {
                forResult.setValue(null);
                finish();
            }

            @Override
            public void fail(ApiException e) {
                UIUtils.showToastSafe(e.getCode()+"  "+e.getDisplayMessage());
            }
        });
    }

}
