package com.vc.wd.main.adapter;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;

import com.vc.wd.common.bean.Course;
import com.vc.wd.main.fragment.HomeTabFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeTabAdapter extends FragmentPagerAdapter {
    private List<Course> mCourseList = new ArrayList<>();

    public HomeTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setList(List<Course> data) {
        this.mCourseList.clear();
        this.mCourseList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        HomeTabFragment fragment = new HomeTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("courseIndex", mCourseList.get(position).getIndex());
        fragment.setArguments(bundle);
        return fragment;
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