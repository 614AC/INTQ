package com.example.intq.main.vm;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.Course;
import com.example.intq.common.bean.instance.InstList;
import com.example.intq.common.bean.instance.InstSearch;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;

import com.example.intq.common.util.logger.Logger;
import com.example.intq.main.request.IMainRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class SearchViewModel extends WDViewModel<IMainRequest> {
    private final Logger logger = Logger.createLogger(getClass());

    public MutableLiveData<InstList> instList = new MutableLiveData<>();
    public MutableLiveData<Boolean> searching = new MutableLiveData<>();
    public MutableLiveData<List<InstSearch>> lastSearches = new MutableLiveData<>();
    public final int MAX_SUGGESTIONS = 5;
    private boolean initLastSearches = false;
    private static long searchMillis;

    @Override
    protected void create() {
        super.create();
        if (!initLastSearches)
            loadLastSearches();
    }

    public float getSearchSec() {
        return Objects.requireNonNull(searching.getValue()) ? -1 : searchMillis / 1000.0f;
    }

    public List<String> getLastKeywords() {
        List<String> keywords = new ArrayList<>();
        for (InstSearch query : lastSearches.getValue())
            keywords.add(String.format("[%s]%s", Course.eng2Chi(query.course), query.key));
        return keywords;
    }

    public void loadLastSearches() {
        List<InstSearch> sampleHis = new ArrayList<>();
        lastSearches.setValue(sampleHis);
        initLastSearches = true;
    }

    public void addLastSearch(InstSearch query) {
        List<InstSearch> history = lastSearches.getValue();
        int exist = history.indexOf(query);
        if (exist >= 0)
            history.remove(exist);
        history.add(0, query);
        while (history.size() > MAX_SUGGESTIONS)
            history.remove(history.size() - 1);
        lastSearches.setValue(history);
    }

    public void deleteLastSearch(int index) {
        List<InstSearch> history = lastSearches.getValue();
        history.remove(index);
        lastSearches.setValue(history);
    }

    public boolean worthSearch(InstSearch query) {
        return !lastSearches.getValue().get(0).equals(query);
    }

    public void updateInstList(InstSearch query) {
        searching.setValue(true);
        logger.d("Searching state: " + searching.getValue() + "[starting]");
        searchMillis = System.currentTimeMillis();
        request(iRequest.getInstList(LOGIN_USER.getToken(),
                query.offset, query.limit, query.sort, query.key, query.course), new DataCall<InstList>() {
            @Override
            public void success(InstList data) {
                searching.setValue(false);
                searchMillis = System.currentTimeMillis() - searchMillis;
                instList.setValue(data);
                logger.d("Searching state: " + searching.getValue() + "[success]");
                logger.d("Searching result: " + instList.getValue());
            }

            @Override
            public void fail(ApiException data) {
                searching.setValue(false);
                searchMillis = System.currentTimeMillis() - searchMillis;
                instList.setValue(null);
                logger.d("Searching state: " + searching.getValue() + "[failed]");
                logger.d("Error code:" + data.getCode());
                logger.d("Error msg:" + data.getMessage());
            }
        });
    }
}
