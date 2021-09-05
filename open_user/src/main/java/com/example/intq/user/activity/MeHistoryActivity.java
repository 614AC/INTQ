package com.example.intq.user.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.user.R;
import com.example.intq.user.databinding.ActivityMeHistoryBinding;
import com.example.intq.user.fragment.HistoryExerciseFragment;
import com.example.intq.user.fragment.HistoryInstanceFragment;
import com.example.intq.user.vm.MeHistoryViewModel;
import com.google.android.material.tabs.TabLayout;

@Route(path = Constant.ACTIVITY_URL_ME_HISTORY)
public class MeHistoryActivity extends WDActivity<MeHistoryViewModel, ActivityMeHistoryBinding> {
    private final String[] titles = {"实体历史", "习题历史"};
    private HistoryInstanceFragment instanceFragment;
    private HistoryExerciseFragment exerciseFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_history;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        TabLayout tabLayout = findViewById(R.id.star_tab);
        ViewPager viewPager = findViewById(R.id.history_pager);

        instanceFragment = new HistoryInstanceFragment();
//        exerciseFragment = new HistoryExerciseFragment();

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if(position == 0)
                    return instanceFragment;
                else
                    return exerciseFragment;
            }

            @Override
            public int getCount() {
                return 1;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

//        tabLayout.setupWithViewPager(viewPager, false);
    }

}
