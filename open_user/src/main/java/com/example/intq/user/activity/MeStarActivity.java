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
import com.example.intq.user.databinding.ActivityMeStarBinding;
import com.example.intq.user.fragment.StarItemFragment;
import com.example.intq.user.vm.MeStarViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Route(path = Constant.ACTIVITY_URL_ME_STAR)
public class MeStarActivity extends WDActivity<MeStarViewModel, ActivityMeStarBinding> {

    private final String[] titles = {"实体收藏", "习题收藏"};
    private List<StarItemFragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_star;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TabLayout tabLayout = findViewById(R.id.star_tab);
        ViewPager viewPager = findViewById(R.id.star_pager);

        fragments.add(new StarItemFragment());
        fragments.add(new StarItemFragment());

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        tabLayout.setupWithViewPager(viewPager, false);
    }
}
