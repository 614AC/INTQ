package com.example.intq.common.bean;

import java.util.Date;

public class HistoryItem {
    private int type;
    private String title;
    private String label;
    private Date vis;

    public HistoryItem(int type, String title, String label, Date vis) {
        this.type = type;
        this.title = title;
        this.label = label;
        this.vis = vis;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getVis() {
        return vis;
    }

    public void setVis(Date vis) {
        this.vis = vis;
    }
}
