package com.example.intq.common.bean.instance;

public class SearchInstListNode {
    private String label;
    private String category;
    private String uri;
    private int starTimes;
    private int viewTimes;

    public SearchInstListNode(String label, String category, String uri, int starTimes, int viewTimes) {
        this.label = label;
        this.category = category;
        this.uri = uri;
        this.starTimes = starTimes;
        this.viewTimes = viewTimes;
    }

    public SearchInstListNode() {

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getStarTimes() {
        return starTimes;
    }

    public void setStarTimes(int starTimes) {
        this.starTimes = starTimes;
    }

    public int getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(int viewTimes) {
        this.viewTimes = viewTimes;
    }
}