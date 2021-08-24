package com.vc.wd.common.bean;

public class Course {
    private int index;

    private static final String[] NAME_ENG = new String[]{"chinese", "english", "math", "physics",
            "chemistry", "biology", "history", "geo", "politics"};
    private static final String[] NAME_CHI = new String[]{"中文", "英语", "数学", "物理",
            "化学", "生物", "历史", "地理", "政治"};

    public static String getNameEng(int index) {
        return NAME_ENG[clamp(index)];
    }

    public static String getNameChi(int index) {
        return NAME_CHI[clamp(index)];
    }

    public static int getCourseNumber() {
        return NAME_ENG.length;
    }

    private static int clamp(int index) {
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
