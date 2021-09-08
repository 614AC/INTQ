package com.example.intq.common.bean.instance;

import java.util.List;

public class SearchInstList {
    private List<SearchInstListNode> instList;

    public SearchInstList() {
    }

    public SearchInstList(List<SearchInstListNode> instList) {
        this.instList = instList;
    }

    public List<SearchInstListNode> getInstList() {
        return instList;
    }

    public void setInstList(List<SearchInstListNode> instList) {
        this.instList = instList;
    }
}