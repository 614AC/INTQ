package com.example.intq.main.adapter;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;

import com.example.intq.common.bean.Course;
import com.example.intq.main.fragment.HomeTabFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeTabAdapter extends FragmentPagerAdapter {
    private List<HomeTabFragment> mFragments = new ArrayList<>();
    private List<Course> mCourseList = new ArrayList<>();

    public HomeTabAdapter(FragmentManager fm) {
        super(fm);
        for (int i = 0; i < Course.getCourseNumber(); ++i) {
            HomeTabFragment fragment = new HomeTabFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("courseIndex", i);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
            mCourseList.add((new Course(i)));
        }
    }

    public void setList(List<Course> data) {
        this.mCourseList.clear();
        this.mCourseList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mCourseList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String plateName = mCourseList.get(position).getNameChi();
        if (plateName == null) {
            plateName = "";
        } else if (plateName.length() > 15) {
            plateName = plateName.substring(0, 15) + "...";
        }
        return plateName;
    }
}