package com.example.intq.main.vm;

import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.alibaba.fastjson.JSON;
import com.example.intq.common.bean.Course;
import com.example.intq.common.bean.instance.InstList;
import com.example.intq.common.bean.instance.InstSearch;
import com.example.intq.common.bean.instance.SearchInstList;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;

import com.example.intq.common.util.UIUtils;
import com.example.intq.common.util.logger.Logger;
import com.example.intq.main.activity.SearchActivity;
import com.example.intq.main.request.IMainRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class SearchViewModel extends WDViewModel<IMainRequest> {
    private final Logger logger = Logger.createLogger(getClass());

    public MutableLiveData<SearchInstList> instList = new MutableLiveData<>();
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
        return searchMillis / 1000.0f;
    }

    public List<String> getLastKeywords() {
        List<String> keywords = new ArrayList<>();
        for (InstSearch query : lastSearches.getValue())
            keywords.add(String.format("[%s]%s", Course.eng2Chi(query.course), query.key));
        return keywords;
    }

    public void loadLastSearches() {
        List<InstSearch> searchesOnDisk = JSON.parseArray(LOGIN_USER.getLastSearches(), InstSearch.class);
        if (searchesOnDisk == null)
            searchesOnDisk = new ArrayList<>();
        lastSearches.setValue(searchesOnDisk);
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
        save();
    }

    public void deleteLastSearch(int index) {
        List<InstSearch> history = lastSearches.getValue();
        history.remove(index);
        lastSearches.setValue(history);
        save();
    }

    public boolean worthSearch(InstSearch query) {
        return !lastSearches.getValue().get(0).equals(query);
    }

    public void updateInstList(InstSearch query) {
        searching.setValue(true);

        searchMillis = System.currentTimeMillis();
        request(iRequest.getInstList(LOGIN_USER.getToken(),
                query.offset, query.limit, query.sort, query.key, query.course), new DataCall<SearchInstList>() {
            @Override
            public void success(SearchInstList data) {
                searching.setValue(false);
                searchMillis = System.currentTimeMillis() - searchMillis;
                instList.setValue(data);
            }

            @Override
            public void fail(ApiException data) {
                searching.setValue(false);
                searchMillis = System.currentTimeMillis() - searchMillis;
                instList.setValue(null);
            }
        });
    }

    private void save() {
        LOGIN_USER.setLastSearches(JSON.toJSONString(lastSearches.getValue()));
        userInfoBox.put(LOGIN_USER);
    }
}
