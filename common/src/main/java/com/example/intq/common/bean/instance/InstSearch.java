package com.example.intq.common.bean.instance;

public class InstSearch {
    public int offset;
    public int limit;
    public String sort;
    public String key;
    public String course;

    public InstSearch(int offset, int limit, String sort, String key, String course) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
        this.key = key;
        this.course = course;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof InstSearch) {
            InstSearch otherQuery = (InstSearch) other;
            return this.key.equals(otherQuery.key) && this.course.equals(otherQuery.course);
        }
        return false;
    }
}
