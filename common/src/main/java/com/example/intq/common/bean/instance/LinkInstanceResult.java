package com.example.intq.common.bean.instance;

import java.util.List;

public class LinkInstanceResult {
    private List<Link> results;

    public LinkInstanceResult(List<Link> results) {
        this.results = results;
    }

    public List<Link> getResults() {
        return results;
    }

    public void setResults(List<Link> results) {
        this.results = results;
    }
}
