package com.example.intq.common.bean.instance;

public class InstListNode {
    private String label;
    private String category;
    private String uri;

    public InstListNode(String label, String category, String uri) {
        this.label = label;
        this.category = category;
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public String getCategory() {
        return category;
    }

    public String getUri() {
        return uri;
    }
}