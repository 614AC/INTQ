package com.example.intq.common.bean.instance;

import java.util.List;

public class InstList {
    private List<InstListNode> instList;

    public InstList(List<InstListNode> instList) {
        this.instList = instList;
    }

    public List<InstListNode> getInstList() {
        return instList;
    }
}