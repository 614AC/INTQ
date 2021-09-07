package com.example.intq.common.bean.instance;

import com.example.intq.common.bean.Course;

import java.io.Serializable;
import java.util.List;

public class HomeTabInfo  {
    private List<Integer> courseList;
    private List<InstList> instLists;

    public HomeTabInfo() {
        this(null, null);
    }

    public HomeTabInfo(List<Integer> courseList, List<InstList> instLists) {
        this.courseList = courseList;
        this.instLists = instLists;
    }

    public List<Integer> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Integer> courseList) {
        this.courseList = courseList;
    }

    public List<InstList> getInstLists() {
        return instLists;
    }

    public void setInstLists(List<InstList> instLists) {
        this.instLists = instLists;
    }

    public InstList getInstList(int courseIndex) {
        return instLists.get(courseIndex);
    }

    public void setInstList(int courseIndex, InstList element) {
        instLists.set(courseIndex, element);
    }
}