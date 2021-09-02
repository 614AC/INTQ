package com.example.intq.main.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ActivityInstanceBinding;
import com.example.intq.main.fragment.ExerciseFragment;
import com.example.intq.main.fragment.InstanceGraphFragment;
import com.example.intq.main.fragment.InstanceListFragment;
import com.example.intq.main.view.CirclePeopleView;
import com.example.intq.main.vm.InstanceViewModel;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

@Route(path = Constant.ACTIVITY_URL_INSTANCE)
public class InstanceActivity extends WDActivity<InstanceViewModel, ActivityInstanceBinding> {

    private final String[] titles = {"属性", "知识图谱", "相关试题"};
    private final String inst_name = "李白";
    private InstanceGraphFragment GraphFragment;
    private InstanceListFragment ListFragment;
    private ExerciseFragment ExerciseFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_instance;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TabLayout tabLayout = findViewById(R.id.inst_tab);
        ViewPager viewPager = findViewById(R.id.inst_pager);
        TextView tv = findViewById(R.id.inst_name);
        tv.setText(this.inst_name);

        GraphFragment = new InstanceGraphFragment();
        ListFragment = new InstanceListFragment();
        ExerciseFragment = new ExerciseFragment();

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if(position == 0)
                    return ListFragment;
                else if (position == 1)
                    return GraphFragment;
                else
                    return ExerciseFragment;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        viewPager.setOffscreenPageLimit(3);

        tabLayout.setupWithViewPager(viewPager, false);
    }
}
