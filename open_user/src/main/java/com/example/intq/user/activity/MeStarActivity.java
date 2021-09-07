package com.example.intq.user.activity;

import android.content.res.Configuration;
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
import com.example.intq.user.fragment.StarInstanceFragment;
import com.example.intq.user.vm.MeStarViewModel;

@Route(path = Constant.ACTIVITY_URL_ME_STAR)
public class MeStarActivity extends WDActivity<MeStarViewModel, ActivityMeStarBinding> {

    private final String[] titles = {"实体收藏", "习题收藏"};
    private StarInstanceFragment instanceFragment;
//    private StarExerciseFragment exerciseFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_star;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        TabLayout tabLayout = findViewById(R.id.star_tab);
        ViewPager viewPager = findViewById(R.id.star_pager);

        instanceFragment = new StarInstanceFragment();
//        exerciseFragment = new StarExerciseFragment();

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
//                if(position == 0)
                    return instanceFragment;
//                else
//                    return exerciseFragment;
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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
