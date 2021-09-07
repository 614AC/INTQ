package com.example.intq.common.bean;

import java.util.ArrayList;
import java.util.List;

public class Course {

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

    public static List<Integer> getAllIndices() {
        List<Integer> courseList = new ArrayList<>();
        for (int i = 0; i < getCourseNumber(); ++i)
            courseList.add(i);
        return courseList;
    }

    public static int clamp(int index) {
        return (index < 0) ? 0 : (index >= getCourseNumber() ? getCourseNumber() - 1 : index);
    }
}
