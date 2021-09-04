package com.example.intq.common.bean.instance;

import java.sql.Timestamp;

public class HistoryInst {
    private String label;
    private String course;
    private String uri;
    private long tsp;


    public HistoryInst(String label, String course, String uri, long tsp) {
        this.label = label;
        this.course = course;
        this.uri = uri;
        this.tsp = tsp;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getTsp() {
        return tsp;
    }

    public void setTsp(long tsp) {
        this.tsp = tsp;
    }
}
