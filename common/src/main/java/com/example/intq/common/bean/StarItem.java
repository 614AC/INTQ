package com.example.intq.common.bean;

public class StarItem {
    private int type;
    private String title;
    private String label;


    public StarItem(int type, String title, String label) {
        this.type = type;
        this.title = title;
        this.label = label;
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
}
