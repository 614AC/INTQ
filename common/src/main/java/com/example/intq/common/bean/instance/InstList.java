package com.example.intq.common.bean.instance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InstList {
    private List<InstListNode> instList;

    public InstList() {
    }

    public InstList(List<InstListNode> instList) {
        this.instList = instList;
    }

    public List<InstListNode> getInstList() {
        return instList;
    }

    public void setInstList(List<InstListNode> instList) {
        this.instList = instList;
    }
}