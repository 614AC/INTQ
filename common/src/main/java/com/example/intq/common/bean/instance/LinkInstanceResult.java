package com.example.intq.common.bean.instance;

import java.util.List;

public class LinkInstanceResult {
    private List<Link> linkInstance;

    public LinkInstanceResult(List<Link> linkInstance) {
        this.linkInstance = linkInstance;
    }

    public List<Link> getLinkInstance() {
        return linkInstance;
    }

    public void setLinkInstance(List<Link> linkInstance) {
        this.linkInstance = linkInstance;
    }
}
