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

    public InstSearch() {
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
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
