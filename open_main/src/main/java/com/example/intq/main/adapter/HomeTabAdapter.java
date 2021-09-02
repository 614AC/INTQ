package com.example.intq.main.adapter;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.intq.common.bean.Course;
import com.example.intq.main.fragment.HomeTabFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeTabAdapter extends FragmentStatePagerAdapter {
    public List<HomeTabFragment> mFragments = new ArrayList<>();
    public List<Course> mCourseList = new ArrayList<>();

    private void add(int cId) {
        cId = Course.clamp(cId);
        mCourseList.add((new Course(cId)));
        HomeTabFragment fragment = new HomeTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("courseIndex", cId);
        fragment.setArguments(bundle);
        mFragments.add(fragment);
    }

    public HomeTabAdapter(FragmentManager fm) {
        super(fm);
        for (int i = 0; i < Course.getCourseNumber(); ++i)
            add(i);
    }

    public void setList(List<Course> data) {
        for (int i = 0; i < data.size(); ++i)
            mFragments.get(i).setCourseIndex(data.get(i).getIndex());
        mCourseList = data;
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