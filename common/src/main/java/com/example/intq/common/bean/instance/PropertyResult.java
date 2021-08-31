package com.example.intq.common.bean.instance;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PropertyResult {
    private List<PropertyNode> nodes;

    public PropertyResult() {}

    public List<PropertyNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<PropertyNode> nodes) {
        this.nodes = nodes;
    }
}
