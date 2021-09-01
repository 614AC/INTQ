package com.example.intq.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.bean.Course;
import com.example.intq.common.bean.instance.InstList;
import com.example.intq.common.bean.instance.InstListNode;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ActivitySearchBinding;
import com.example.intq.main.vm.SearchViewModel;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.Objects;

@Route(path = Constant.ACTIVITY_URL_SEARCH)
public class SearchActivity extends WDActivity<SearchViewModel, ActivitySearchBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewModel.searching.observe(this, searching -> {
            if (searching) {
                binding.searchDisplay.setVisibility(View.INVISIBLE);
                binding.searchLoading.show();
            } else {
                binding.searchDisplay.setVisibility(View.VISIBLE);
                binding.searchLoading.smoothToHide();
            }
        });
        viewModel.instList.observe(this, new Observer<InstList>() {
            @Override
            public void onChanged(InstList instList) {
                String toastInfo = "";
                String show = "";
                if (instList == null) {
                    toastInfo = "没有找到相关实体~";
                    show = "空空如也";
                } else {
                    toastInfo = String.format("搜索到%d个相关实体\n耗时%fs",
                            instList.getInstList().size(), viewModel.getSearchSec());
                    for (InstListNode node : instList.getInstList())
                        show += String.format("label:%s\ncategory:%s\nuri:%s\n",
                                node.getLabel(), node.getCategory(), node.getUri());
                }
                UIUtils.showToastSafe(toastInfo);
                binding.searchDisplay.setText(show);
            }
        });
        //加载设置
        binding.searchLoading.getIndicator().setColor(R.color.colorPrimary);
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
                    search(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });

        CharSequence keyword = Objects.requireNonNull(getIntent().getCharSequenceExtra("keyword"));
        binding.searchBar.setText(keyword.toString());
        binding.searchBar.getSearchEditText().setSelection(keyword.length());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.searchBar.openSearch();
                //等效于点击搜索按钮
                binding.searchBar.onEditorAction(null, 0, null);
            }
        }, 80);

    }

    public boolean search(CharSequence keyword) {
        viewModel.updateInstList(0, 100, "name",
                keyword.toString(), Course.getNameEng(0));
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