package com.example.intq.common.bean;

public class Course {
    int value;

    public static final String[] TYPE = new String[]{"chinese", "english", "math", "physics",
            "chemistry", "biology", "history", "geo", "politics"};
    public static final String[] TYPE_CHINESE = new String[]{"中文", "英语", "数学", "物理",
            "化学", "生物", "历史", "地理", "政治"};

    public Course(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
