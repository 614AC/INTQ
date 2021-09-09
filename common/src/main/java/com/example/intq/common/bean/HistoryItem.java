package com.example.intq.common.bean;

import java.sql.Timestamp;

public class HistoryItem {
    private int type;
    private String course;
    private String label;
    private String uri;
    private long vis;


    public HistoryItem(){}

    public HistoryItem(int type, String course, String label, String uri, long vis) {
        this.type = type;
        this.course = course;
        this.label = label;
        this.uri = uri;
        this.vis = vis;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getVis() {
        return vis;
    }

    public void setVis(long vis) {
        this.vis = vis;
    }
}
