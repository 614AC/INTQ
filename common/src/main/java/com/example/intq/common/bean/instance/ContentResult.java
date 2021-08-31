package com.example.intq.common.bean.instance;

import java.util.List;

public class ContentResult {
    List<ContentNode> nodes;
    public ContentResult() {}

    public void setNodes(List<ContentNode> nodes) {
        this.nodes = nodes;
    }

    public List<ContentNode> getNodes() {
        return nodes;
    }
}