package com.example.intq.main.adapter;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.intq.common.bean.Course;
import com.example.intq.common.bean.instance.HomeTabInfo;
import com.example.intq.common.bean.instance.InstList;
import com.example.intq.main.fragment.HomeTabFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeTabAdapter extends FragmentStatePagerAdapter {
    public List<HomeTabFragment> mFragments = new ArrayList<>();
    public HomeTabInfo mHomeTabInfo;

    public HomeTabAdapter(FragmentManager fm) {
        super(fm);
        for (int i = 0; i < Course.getCourseNumber(); ++i)
            mFragments.add(new HomeTabFragment());
    }

    public void setHomeTabInfo(HomeTabInfo homeTabInfo) {
        mHomeTabInfo = homeTabInfo;
        List<Integer> courseList = mHomeTabInfo.getCourseList();
        List<InstList> instLists = mHomeTabInfo.getInstLists();
        for (int i = 0; i < getCount(); ++i) {
            int courseIndex = courseList.get(i);
            mFragments.get(i).setIndexAndInstList(courseIndex, instLists.get(courseIndex));
        }
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mHomeTabInfo == null ? 0 : mHomeTabInfo.getCourseList().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String plateName = Course.getNameChi(mHomeTabInfo.getCourseList().get(position));
        if (plateName == null) {
            plateName = "";
        } else if (plateName.length() > 15) {
            plateName = plateName.substring(0, 15) + "...";
        }
        return plateName;
    }
}