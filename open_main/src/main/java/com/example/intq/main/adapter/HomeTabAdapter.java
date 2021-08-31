package com.example.intq.main.adapter;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;

import com.example.intq.common.bean.Course;
import com.example.intq.main.fragment.HomeTabFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeTabAdapter extends FragmentPagerAdapter {
    private List<HomeTabFragment> mFragments = new ArrayList<>();
    private List<Course> mCourseList = new ArrayList<>();

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
        mCourseList.clear();
        mFragments.clear();
        for (Course c : data)
            add(c.getIndex());
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

    /**
     * 返回值有三种，
     * POSITION_UNCHANGED  默认值，位置没有改变
     * POSITION_NONE       item已经不存在
     * position            item新的位置
     * 当position发生改变时这个方法应该返回改变后的位置，以便页面刷新。
     */
    @Override
    public int getItemPosition(Object object) {
        if (object instanceof Fragment) {
            if (mFragments.contains(object)) {
                return mFragments.indexOf(object);
            } else {
                return POSITION_NONE;
            }

        }
        return super.getItemPosition(object);
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