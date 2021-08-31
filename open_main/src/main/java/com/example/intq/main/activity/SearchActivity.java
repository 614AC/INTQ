package com.example.intq.main.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ActivitySearchBinding;
import com.example.intq.main.vm.EmptyViewModel;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

@Route(path = Constant.ACTIVITY_URL_SEARCH)
public class SearchActivity extends WDActivity<EmptyViewModel, ActivitySearchBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        search(getIntent().getCharSequenceExtra("keyword"));
        //搜索栏设置
        reduce();
        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (enabled)
                    expand();
                else
                    reduce();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                if (text != null && text.length() > 0)
                    binding.searchTextDisplay.setText(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });
    }

    public boolean search(CharSequence keyword) {
        binding.searchTextDisplay.setText(keyword);
        return true;
    }

    private void expand() {
        //设置伸展状态时的布局
        binding.searchBar.setHint("搜索已光翼展开");

        //设置动画
        MarginLayoutParams searchBarLayoutParams = (MarginLayoutParams) binding.searchBar.getLayoutParams();
        searchBarLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        binding.searchBar.setLayoutParams(searchBarLayoutParams);
        beginDelayedTransition(binding.searchBar, 0, 500);
    }

    private void reduce() {
        // 设置收缩状态时的布局
        binding.searchBar.setHint("点我搜索");

        //设置动画
        MarginLayoutParams searchBarLayoutParams = (MarginLayoutParams) binding.searchBar.getLayoutParams();

        searchBarLayoutParams.width = UIUtils.getScreenWidth(this) * 3 / 4;
        searchBarLayoutParams.topMargin = 0;
        binding.searchBar.setLayoutParams(searchBarLayoutParams);
        beginDelayedTransition(binding.searchBar, 0, 500);
    }

    void beginDelayedTransition(ViewGroup view, long startDelay, long duration) {
        AutoTransition tran = new AutoTransition();
        tran.setStartDelay(startDelay);
        tran.setDuration(duration);
        TransitionManager.beginDelayedTransition(view, tran);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            binding.searchBar.closeSearch();
            finish();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
//        searchBar.setHint("Custom hint");
//        searchBar.setSpeechMode(true);
//        //enable searchbar callbacks
//        searchBar.setOnSearchActionListener(this);
//        //restore last queries from disk
//        lastSearches = loadSearchSuggestionFromDisk();
//        searchBar.setLastSuggestions(list);
//        //Inflate menu and setup OnMenuItemClickListener
//        searchBar.inflateMenu(R.menu.main);
//        searchBar.getMenu().setOnMenuItemClickListener(this);
//    }
}