package com.example.intq.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.bean.Course;
import com.example.intq.common.bean.instance.InstListNode;
import com.example.intq.common.bean.instance.InstSearch;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.common.util.recycleview.SpacingItemDecoration;
import com.example.intq.main.R;
import com.example.intq.main.adapter.ListInstanceAdapter;
import com.example.intq.main.databinding.ActivitySearchBinding;
import com.example.intq.main.vm.SearchViewModel;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.MaterialSearchBar.OnSearchActionListener;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.Objects;

import javax.annotation.Nullable;

@Route(path = Constant.ACTIVITY_URL_SEARCH)
public class SearchActivity extends WDActivity<SearchViewModel, ActivitySearchBinding> {
    private ListInstanceAdapter mAdapter;
    private MaterialSearchBar mSearchBar;
    private PopupMenu mCourseMenu;
    private EditText mSearchEdit;
    private MutableLiveData<CharSequence> mCurrentCourse = new MutableLiveData<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mAdapter = new ListInstanceAdapter();
        mAdapter.setOnItemClickListener((view, position) -> {

        });
        binding.searchRecyclerView.setLayoutManager(new

                LinearLayoutManager(this));
        binding.searchRecyclerView.addItemDecoration(new

                SpacingItemDecoration(30));
        binding.searchRecyclerView.setAdapter(mAdapter);

        viewModel.searching.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean searching) {
                if (searching) {
                    binding.searchRecyclerView.setVisibility(View.INVISIBLE);
                    binding.searchLoading.smoothToShow();
                } else {
                    binding.searchRecyclerView.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(() -> {
                        binding.searchLoading.smoothToHide();
                    }, 1000);
                }
            }
        });
        viewModel.instList.observe(this, instList ->

        {
            String toastInfo = "";
            mAdapter.clear();
            if (instList == null || instList.getInstList().size() == 0) {
                mAdapter.add(new InstListNode("空空如也", "搜索", ""));
                toastInfo = "没有找到相关实体~";
            } else {
                mAdapter.addAll(instList.getInstList());
                toastInfo = String.format("搜索到%d个相关实体,耗时%.2fs",
                        instList.getInstList().size(), viewModel.getSearchSec());
            }
            mAdapter.notifyDataSetChanged();
            UIUtils.showToastSafe(toastInfo);
        });
        mCurrentCourse.observe(this, (charSequence) ->

        {
            String show = String.format("搜索学科:%s", Course.eng2Chi(charSequence.toString()));
            binding.searchDisplayHead.setText(show);
        });
        //加载设置
        binding.searchLoading.getIndicator().

                setColor(R.color.colorPrimary);
        //搜索栏设置
        mSearchBar = binding.searchBar;
        mSearchBar.setSpeechMode(false);
        mSearchBar.setNavButtonEnabled(true);
        mSearchBar.setMaxSuggestionCount(viewModel.MAX_SUGGESTIONS);
        //restore last queries from disk
        viewModel.loadLastSearches();
        //setup menu and OnMenuItemClickListener
        mCourseMenu = new

                PopupMenu(this, findViewById(R.id.mt_nav));
        mCourseMenu.setOnMenuItemClickListener(item ->

        {
            CharSequence title = item.getTitle();
            mCurrentCourse.setValue(Course.chi2Eng(title.toString()));
            return true;
        });
        mCourseMenu.inflate(R.menu.main_course);
        mCourseMenu.setGravity(Gravity.LEFT);
        mSearchBar.setCardViewElevation(10);
        mSearchBar.addTextChangeListener(new

                                                 TextWatcher() {
                                                     @Override
                                                     public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                     }

                                                     @Override
                                                     public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                         Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + mSearchBar.getText());
                                                     }

                                                     @Override
                                                     public void afterTextChanged(Editable editable) {

                                                     }

                                                 });

        binding.searchButton.setOnClickListener(v ->

        {
            if (mSearchBar.isSearchOpened())
                search(mSearchBar.getText(), mCurrentCourse.getValue(), false);
            else
                mSearchBar.openSearch();
        });

        reduce();

        OnSearchActionListener searchActionListener = new OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (enabled)
                    expand();
                else
                    reduce();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                search(text, mCurrentCourse.getValue(), false);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                switch (buttonCode) {
                    case MaterialSearchBar.BUTTON_NAVIGATION:
                        mCourseMenu.show();
                        break;
                    case MaterialSearchBar.BUTTON_SPEECH:
                        break;
                    case MaterialSearchBar.BUTTON_BACK:
                        mSearchBar.closeSearch();
                        break;
                }
            }
        };
        mSearchBar.setOnSearchActionListener(searchActionListener);
        Intent intent = getIntent();
        CharSequence keyword = Objects.requireNonNull(intent.getCharSequenceExtra("keyword"));
        mCurrentCourse.setValue(Objects.requireNonNull(intent.getCharSequenceExtra("course")));
        mSearchBar.setText(keyword.toString());
        mSearchEdit = mSearchBar.getSearchEditText();
        mSearchEdit.setSelection(keyword.length());
        mSearchEdit.setOnEditorActionListener((v, actionId, event) ->

        {
            searchActionListener.onSearchConfirmed(mSearchEdit.getText());
            return true;
        });
        mSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                if (v.getTag() instanceof String) {
                    String[] courseWithKeyword = ((String) v.getTag()).split("]");
                    String course = Course.chi2Eng(courseWithKeyword[0].substring(1));
                    String keyword = courseWithKeyword[1].trim();
                    mCurrentCourse.setValue(course);
                    new Handler().postDelayed(() -> {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.showSoftInput(mSearchEdit, InputMethodManager.SHOW_IMPLICIT);
                    }, 10);
                    mSearchEdit.requestFocus();
                    mSearchEdit.setText(keyword);
                    mSearchEdit.setSelection(keyword.length());
                }
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {
                if (v.getTag() instanceof String) {
                    viewModel.deleteLastSearch(position);
                    mSearchBar.updateLastSuggestions(Objects.requireNonNull(viewModel.getLastKeywords()));
                }
            }
        });

        search(keyword, mCurrentCourse.getValue(), true);
    }

    /**
     * null默认采用搜索框的字符串作为关键字
     *
     * @param keyword
     * @return
     */
    public boolean search(@NonNull CharSequence keyword, @NonNull CharSequence course, boolean forceSearch) {
        keyword = keyword.toString().trim();
        if (keyword.length() > 0) {
            InstSearch query = new InstSearch(0, 100, "name", keyword.toString(), course.toString());
            if (forceSearch || viewModel.worthSearch(query)) {
                viewModel.updateInstList(query);
                viewModel.addLastSearch(query);
            }
            mSearchBar.hideSuggestionsList();
            mSearchBar.closeSearch();
            mSearchBar.setPlaceHolder(keyword);
            return true;
        }
        return false;
    }

    private void expand() {
        mSearchBar.updateLastSuggestions(Objects.requireNonNull(viewModel.getLastKeywords()));
        binding.searchFrame.setBackgroundColor(getColor(R.color.transparentBlack));
        //设置动画
        MarginLayoutParams mSearchBarLayoutParams = (MarginLayoutParams) mSearchBar.getLayoutParams();
        mSearchBarLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mSearchBar.setLayoutParams(mSearchBarLayoutParams);
        beginDelayedTransition(mSearchBar, 0, 500);
    }

    private void reduce() {
        binding.searchFrame.setBackgroundColor(getColor(R.color.white));

        //设置动画
        MarginLayoutParams mSearchBarLayoutParams = (MarginLayoutParams) mSearchBar.getLayoutParams();
        mSearchBarLayoutParams.width = UIUtils.getScreenWidth(this) * 3 / 4;
        mSearchBar.setLayoutParams(mSearchBarLayoutParams);
        beginDelayedTransition(mSearchBar, 0, 500);
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
            mSearchBar.closeSearch();
            finish();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}