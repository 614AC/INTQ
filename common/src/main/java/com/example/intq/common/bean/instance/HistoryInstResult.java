package com.example.intq.common.bean.instance;

import java.util.List;

public class HistoryInstResult {
    private List<HistoryInst> instList;

    public HistoryInstResult(List<HistoryInst> instList) {
        this.instList = instList;
    }

    public List<HistoryInst> getInstList() {
        return instList;
    }

    public void setInstList(List<HistoryInst> instList) {
        this.instList = instList;
    }
}
