package com.example.intq.main.vm;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RadioGroup;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.instance.InstList;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;

import com.example.intq.common.util.logger.Logger;
import com.example.intq.main.request.ISearchRequest;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

public class SearchViewModel extends WDViewModel<ISearchRequest> {
    private final Logger logger = Logger.createLogger(getClass());

    public MutableLiveData<InstList> instLabelList = new MutableLiveData<>();
    public MutableLiveData<Boolean> searching = new MutableLiveData<>();
    private static long searchMillis;

    @Override
    protected void create() {
        super.create();
    }

    public void updateInstLabelList(int offset, int limit, String sort, String key, String course) {
        searching.setValue(true);
        logger.d("Searching state: " + searching.getValue() + "[starting]");
        searchMillis = System.currentTimeMillis();
        new Handler().postDelayed(() -> request(iRequest.getInstLabelList(LOGIN_USER.getToken(),
                offset, limit, sort, key, course), new DataCall<InstList>() {
            @Override
            public void success(InstList data) {
                searching.setValue(false);
                searchMillis = System.currentTimeMillis() - searchMillis;
                instLabelList.setValue(data);
                logger.d("Searching state: " + searching.getValue() + "[success]");
                logger.d("Searching result: " + instLabelList.getValue());
            }

            @Override
            public void fail(ApiException data) {
                searching.setValue(false);
                searchMillis = System.currentTimeMillis() - searchMillis;
                logger.d("Searching state: " + searching.getValue() + "[failed]");
                logger.d("Error code:" + data.getCode());
                logger.d("Error msg:" + data.getMessage());
            }
        }), 2000);

    }

    public float getSearchSec() {
        return Objects.requireNonNull(searching.getValue()) ? -1 : searchMillis / 1000.0f;
    }
}
