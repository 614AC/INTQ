package com.example.intq.common.bean.instance;

import java.util.List;

public class InstInfoAlter {
    private List<PropertyNode> property;
    private List<ContentNode> content;
    private String label;

    public InstInfoAlter(List<PropertyNode> property, List<ContentNode> content, String label) {
        this.property = property;
        this.content = content;
        this.label = label;
    }

    public List<PropertyNode> getProperty() {
        return property;
    }

    public void setProperty(List<PropertyNode> property) {
        this.property = property;
    }

    public List<ContentNode> getContent() {
        return content;
    }

    public void setContent(List<ContentNode> content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
