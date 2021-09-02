package com.example.intq.common.bean;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int index;

    private static final String[] NAME_ENG = new String[]{"chinese", "english", "math", "physics",
            "chemistry", "biology", "history", "geo", "politics"};
    private static final String[] NAME_CHI = new String[]{"语文", "英语", "数学", "物理",
            "化学", "生物", "历史", "地理", "政治"};

    public static String getNameEng(int index) {
        return NAME_ENG[clamp(index)];
    }

    public static String getNameChi(int index) {
        return NAME_CHI[clamp(index)];
    }

    public static int getIndexEng(String s) {
        for (int i = 0; i < getCourseNumber(); ++i)
            if (s.equals(NAME_ENG[i]))
                return i;
        return -1;
    }

    public static int getIndexChi(String s) {
        for (int i = 0; i < getCourseNumber(); ++i)
            if (s.equals(NAME_CHI[i]))
                return i;
        return -1;
    }

    public static String chi2Eng(String chi) {
        return Course.getNameEng(Course.getIndexChi(chi));
    }

    public static String eng2Chi(String eng) {
        return Course.getNameChi(Course.getIndexEng(eng));
    }

    public static int getCourseNumber() {
        return NAME_ENG.length;
    }

    public static int[] course2Integer(List<Course> courseList) {
        try {
            int[] courseIndices = new int[courseList.size()];
            for (int i = 0; i < courseList.size(); ++i)
                courseIndices[i] = courseList.get(i).getIndex();
            return courseIndices;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static List<Course> integer2Course(int[] courseIndices) {
        try {
            List<Course> courseList = new ArrayList<>(courseIndices.length);
            for (int i = 0; i < courseIndices.length; ++i)
                courseList.add(new Course(courseIndices[i]));
            return courseList;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static int clamp(int index) {
        return (index < 0) ? 0 : (index >= getCourseNumber() ? getCourseNumber() - 1 : index);
    }

    public Course(int index) {
        this.index = clamp(index);
    }

    public int getIndex() {
        return index;
    }

    public String getNameEng() {
        return NAME_ENG[index];
    }

    public String getNameChi() {
        return NAME_CHI[index];
    }
}
