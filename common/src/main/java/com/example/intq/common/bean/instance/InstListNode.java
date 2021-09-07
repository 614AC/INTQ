package com.example.intq.common.bean.instance;

import java.io.Serializable;

public class InstListNode {
    private String label;
    private String category;
    private String uri;

    public InstListNode(String label, String category, String uri) {
        this.label = label;
        this.category = category;
        this.uri = uri;
    }

    public InstListNode() {

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
}